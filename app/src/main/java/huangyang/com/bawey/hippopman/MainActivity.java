package huangyang.com.bawey.hippopman;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private FormatStrategy formatStrategy;
    private int b=1;
    public  String url="http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=20&gender=2&ts=1871746850&page="+b;

    private SwipeRefreshLayout swipe;
    private RecyclerView rv;
    private List<Bean.DataBean> list;
    private MyAdapter adapter;

    Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                getData(++b);
                list.clear();
                list.addAll(0, list);
                swipe.setRefreshing(false);
                if (b>=8)
                {
                    Toast.makeText(MainActivity.this, "接口id最大为8其他无值，已经刷完了，亲！", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();

            }
        };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        getData(b);

    }

    private void initView() {
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        rv = (RecyclerView) findViewById(R.id.rv);

        list = new ArrayList<>();
        LinearLayoutManager lm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(lm);
       /* StaggeredGridLayoutManager sm=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(sm);*/

        adapter = new MyAdapter(list,MainActivity.this);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(2000,0);
            }
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                boolean a = isSlideToBottom(rv);
                if(a) {
                    list.addAll(0,list);
                    Toast.makeText(MainActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }else {

                }
            }
        });

        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.d("debug");
        Logger.e("error");
        Logger.w("warning");
        Logger.v("verbose");
        Logger.i("information");
        Logger.wtf("wtf!!!!");

        formatStrategy = CsvFormatStrategy.newBuilder()
                .tag("custom")
                .build();

        //保存到sd卡
        try {
            File file = new File(Environment.getExternalStorageDirectory(),"a.txt");
            FileOutputStream out = new FileOutputStream(file);
            out.write(formatStrategy.toString().getBytes());
            out.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));

        //分隔线
        SpaceItemAnimotou decoration=new SpaceItemAnimotou(getApplicationContext());
        rv.addItemDecoration(decoration);

    }
    private void getData(int b) {

        OkUtils okUtils=new OkUtils();
        okUtils.Senthelp(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Bean bean= new Gson().fromJson(response.body().string(),Bean.class);
                list.addAll(bean.getData());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });

    }

    public static boolean isSlideToBottom(RecyclerView listviews) {
        if ( listviews== null) {
            return false;
        }
        if (listviews.computeVerticalScrollExtent() + listviews.computeVerticalScrollOffset() >= listviews.computeVerticalScrollRange()) {
            return true;
        }
        return false;
    }


}
