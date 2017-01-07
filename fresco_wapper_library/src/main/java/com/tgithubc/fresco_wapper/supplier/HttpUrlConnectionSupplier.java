package com.tgithubc.fresco_wapper.supplier;

import android.net.Uri;

import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.FetchState;
import com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Created by tiancheng :)
 */

public class HttpUrlConnectionSupplier extends HttpUrlConnectionNetworkFetcher {

    private static final int MAX_REDIRECTS = 5;
    private static final int NUM_NETWORK_THREADS = 3;
    private static final int HTTP_TEMPORARY_REDIRECT = 307;
    private static final int HTTP_PERMANENT_REDIRECT = 308;
    private final ExecutorService mExecutorService;

    public HttpUrlConnectionSupplier() {
        this(Executors.newFixedThreadPool(NUM_NETWORK_THREADS));
    }

    private HttpUrlConnectionSupplier(ExecutorService executorService) {
        mExecutorService = executorService;
    }

    @Override
    public void fetch(final FetchState fetchState, final Callback callback) {
        //if (!NetworkStateUtil.isOnlyWifiConnect() && NetworkStateUtil.isAvaliable()) {
            final Future<?> future = mExecutorService.submit(
                    new Runnable() {
                        @Override
                        public void run() {
                            fetchSync(fetchState, callback);
                        }
                    });
            fetchState.getContext().addCallbacks(
                    new BaseProducerContextCallbacks() {
                        @Override
                        public void onCancellationRequested() {
                            if (future.cancel(false)) {
                                callback.onCancellation();
                            }
                        }
                    });
        //}
    }

    private void fetchSync(FetchState fetchState, Callback callback) {
        HttpURLConnection connection = null;
        try {
            connection = downloadFrom(fetchState.getUri(), MAX_REDIRECTS);

            if (connection != null) {
                callback.onResponse(connection.getInputStream(), -1);
            }
        } catch (IOException e) {
            callback.onFailure(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private HttpURLConnection downloadFrom(Uri uri, int maxRedirects) throws IOException {
        URL url = new URL(uri.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        /*
        //代理
        Proxy proxy = HttpSession.getGlobalProxy();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);

        //电信流量包请求头
        Map<String, String> headers = HttpSession.getGlobalProxyHeaders();
        if (null != headers) {
            String spId = headers.get("spId");
            String spKey = headers.get("spKey");
            String mobile = headers.get("mobile");
            String imsi = headers.get("imsi");
            String host = url.getHost();
            if (url.getPort() != 80 && url.getPort() > 0) {
                host = host + ":" + url.getPort();
            }

            String timestamp = Long.toString(System.currentTimeMillis());
            String token = KwFlowUtils.getCTCCToken(spId, spKey, host, timestamp, mobile);

            connection.setRequestProperty("spid", spId);
            connection.setRequestProperty("Host", host);
            connection.setRequestProperty("x-up-calling-line-id", mobile);
            connection.setRequestProperty("timestamp", timestamp);
            connection.setRequestProperty("token", token);
            connection.setRequestProperty("imsi", imsi);
        }*/

        int responseCode = connection.getResponseCode();

        if (isHttpSuccess(responseCode)) {
            return connection;
        } else if (isHttpRedirect(responseCode)) {
            String nextUriString = connection.getHeaderField("Location");
            connection.disconnect();

            Uri nextUri = (nextUriString == null) ? null : Uri.parse(nextUriString);
            String originalScheme = uri.getScheme();

            if (maxRedirects > 0 && nextUri != null && !nextUri.getScheme().equals(originalScheme)) {
                return downloadFrom(nextUri, maxRedirects - 1);
            } else {
                String message = maxRedirects == 0
                        ? error("URL %s follows too many redirects", uri.toString())
                        : error("URL %s returned %d without a valid redirect", uri.toString(), responseCode);
                throw new IOException(message);
            }
        } else {
            connection.disconnect();
            throw new IOException(String
                    .format("Image URL %s returned HTTP code %d", uri.toString(), responseCode));
        }
    }

    private static boolean isHttpSuccess(int responseCode) {
        return (responseCode >= HttpURLConnection.HTTP_OK &&
                responseCode < HttpURLConnection.HTTP_MULT_CHOICE);
    }

    private static boolean isHttpRedirect(int responseCode) {
        switch (responseCode) {
            case HttpURLConnection.HTTP_MULT_CHOICE:
            case HttpURLConnection.HTTP_MOVED_PERM:
            case HttpURLConnection.HTTP_MOVED_TEMP:
            case HttpURLConnection.HTTP_SEE_OTHER:
            case HTTP_TEMPORARY_REDIRECT:
            case HTTP_PERMANENT_REDIRECT:
                return true;
            default:
                return false;
        }
    }

    private static String error(String format, Object... args) {
        return String.format(Locale.getDefault(), format, args);
    }
}
