package huangyang.com.bawey.hippopman;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 创建人:hy
 * 日期:  2017/8/25
 */

public class OkUtils {
    public void Senthelp(String url, Callback callback)
    {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
