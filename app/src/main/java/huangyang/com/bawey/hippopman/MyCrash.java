package huangyang.com.bawey.hippopman;

import android.app.Application;

/**
 * 创建人:hy
 * 日期:  2017/8/25
 */

public class MyCrash extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
