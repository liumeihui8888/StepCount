package com.v210.frame;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.view.KeyEvent;
import com.v210.utils.ACache;
import com.v210.utils.LogWriter;
import com.v210.utils.PrefsManager;
import com.v210.utils.Utils;
import java.util.LinkedList;
import java.util.List;

public class FrameApplication extends Application
{
    private static ErrorHandler crashHandler = null;
    private static FrameApplication mInstance;
    private static LinkedList<Activity> mList = new LinkedList<Activity>();
    long exitTime = 0;
    public boolean closeAppByBackPressed(final int keyCode, final KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if ((System.currentTimeMillis() - this.exitTime) > 2000)
            {
                Utils.makeToast(this, "再按一次退出程序");
                this.exitTime = System.currentTimeMillis();
            } else
            {
                FrameApplication.exitApp();
            }
            return true;
        }
        return false;
    }

    public LinkedList<Activity> getActivityList()
    {
        return mList;
    }

    public static ACache getFileCache()
    {
        return ACache.get(com.v210.frame.FrameApplication.mInstance);
    }

    public static void addToList(final Activity act)
    {
        FrameApplication.mList.add(act);
    }

    public static void removeFromList(final Activity act)
    {
        if (FrameApplication.mList.indexOf(act) != -1)
        {
            FrameApplication.mList.remove(act);
        }
    }

    public static void clearActivityStack()
    {
        for (int i = FrameApplication.mList.size() - 1; i >= 0; i--)
        {
            final Activity activity = FrameApplication.mList.get(i);
            if (activity != null)
            {
                activity.finish();
            }
        }
    }

    /**
     * 退出应用程序
     */
    public static void exitApp()
    {
        try
        {
            for (int i = FrameApplication.mList.size() - 1; i >= 0; i--)
            {
                final Activity activity = FrameApplication.mList.get(i);
                if (activity != null)
                {
                    activity.finish();
                }
            }
        } catch (final Exception e)
        {
        } finally
        {
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    public static FrameApplication getInstance()
    {
        return FrameApplication.mInstance;
    }

    public static void setCrashHandler(final Context c)
    {
        FrameApplication.crashHandler.setToErrorHandler(c);
    }

    /**
     * 全局的SharedPreferences
     */
    private PrefsManager mPrefsManager;

    /**
     * 全局的SharedPreference
     */
    public PrefsManager getPrefsManager()
    {
        return this.mPrefsManager;
    }

    public boolean isAppOnForeground()
    {
        final ActivityManager activityManager = (ActivityManager) this.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        final String packageName = this.getApplicationContext().getPackageName();
        final List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
        {
            return false;
        }
        for (final RunningAppProcessInfo appProcess : appProcesses)
        {
            if (appProcess.processName.equals(packageName) && (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND))
            {
                return true;
            }
        }
        return false;
    }

    public void onCreate()
    {
        super.onCreate();
        FrameApplication.mInstance = this;
        this.mPrefsManager = new PrefsManager(this);
        FrameApplication.crashHandler = ErrorHandler.getInstance();
    }

    public void onLowMemory()
    {
        super.onLowMemory();
        LogWriter.e("Low mem");
    }
}
