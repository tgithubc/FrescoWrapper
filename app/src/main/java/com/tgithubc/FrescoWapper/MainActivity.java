package com.tgithubc.FrescoWapper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    private String[] mData = new String[]{
            "http://img1.kuwo.cn/star/userpl2015/shoujigd/precision/1005292999_auto_700.jpg",
            "http://img1.kwcdn.kuwo.cn/star/userpl2015/89/77/1474841628674_205780289_700.jpg",
            "http://star.kuwo.cn/star/userpl2015/7/69/1446034521041_146872707_700.jpg",
            "http://img1.kuwo.cn/star/userpl2015/shoujigd/random/624_auto_700.jpg",
            "http://star.kuwo.cn/star/userpl2015/10/42/1483363392268_264650010_700.jpg",
            "http://star.kuwo.cn/star/userpl2015/10/42/1483362772287_264650010_700.jpg",
            "http://star.kuwo.cn/star/userpl2015/50/46/1483082318417_259597450_700.jpg",
            "http://star.kuwo.cn/star/userpl2015/10/42/1483284926391_264650010_700.jpg",
            "http://star.kuwo.cn/star/userpl2015/50/46/1483081588831_259597450_700.jpg",
            "http://star.kuwo.cn/star/userpl2015/50/46/1482979678411_259597450_700.jpg",
            "http://star.kuwo.cn/star/userpl2015/41/91/1483112100057_271700641_700.jpg",
            "http://star.kuwo.cn/star/userpl2015/10/13/1483631237510_132026710_700.jpg",
            "http://star.kuwo.cn/star/userpl2015/69/76/1470658178277_190971769_700.jpg",
            "http://star.kuwo.cn/star/userpl2015/69/76/1467645298167_190971769_700.jpg",
            "http://star.kuwo.cn/star/userpl2015/69/76/1459263416641_190971769_700.jpg",
            "http://star.kuwo.cn/star/userpl2015/69/76/1458489665884_190971769_700.jpg",
            "http://star.kuwo.cn/star/userpl2015/38/18/1446798160242_194047938_700.jpg",
            "http://star.kuwo.cn/star/userpl2015/38/18/1446891837760_194047938_700.jpg",
            "http://star.kuwo.cn/star/userpl2015/75/9/1432548462952_178539975_700.jpg",
            "http://img1.kuwo.cn/star/userpl2015/shoujigd/random/3401_auto_700.jpg",
            "http://img1.kuwo.cn/star/userpl2015/shoujigd/precision/311352587_auto_700.jpg",
            "http://img1.kuwo.cn/star/userpl2015/shoujigd/random/1074_auto_700.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        TestAdapter adapter = new TestAdapter(this, mData);
        recyclerView.setAdapter(adapter);
    }
}
