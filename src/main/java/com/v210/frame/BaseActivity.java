package com.v210.frame;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseActivity extends FragmentActivity
{
    public boolean onKeyDown(final int keyCode, final KeyEvent event)
    {
        if (currentFragment != null)
        {
            return currentFragment.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    private BaseFragment currentFragment;

    public BaseFragment getCurrentFragment()
    {
        return currentFragment;
    }

    /**
     * 是否显示应用程序标题栏
     */
    protected boolean isHideAppTitle    = true;
    /**
     * 是否显示系统标题栏
     */
    protected boolean isHideSystemTitle = false;

    protected final void onCreate(final Bundle savedInstanceState)
    {
        this.onInitVariable();
        if (this.isHideAppTitle)
        {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        super.onCreate(savedInstanceState);
        if (this.isHideSystemTitle)
        {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        this.startView(savedInstanceState);
        // 将当前Activity加入列表
        FrameApplication.addToList(this);
    }

    // -----------------------------------------------------------------------------------------
    public void addFregment(final int viewID, final BaseFragment f)
    {
        addFregment(viewID, f, false);
    }

    public void addFregment(final int viewID, final BaseFragment f, final boolean addToBackstack)
    {
        final FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        f.setContext(this);
        transaction.add(viewID, f);
        if (addToBackstack)
        {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
        currentFragment = f;
    }

    // -----------------------------------------------------------------------------------------

    public void replaceFregment(final int viewID, final BaseFragment f, final boolean addToBackstack)
    {
        final FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(viewID, f);
        f.setContext(this);
        if (addToBackstack)
        {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
        currentFragment = f;
    }

    public void replaceFregment(final int viewID, final BaseFragment f)
    {
        this.replaceFregment(viewID, f, true);
    }

    // -----------------------------------------------------------------------------------------
    public void deleteFregment(final int viewID, final BaseFragment f, final boolean addToBackstack)
    {
        final FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.remove(f);
        f.removeContext();
        if (addToBackstack)
        {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
        currentFragment = null;
    }

    // -----------------------------------------------------------------------------------------
    public void popStackFregment()
    {
        this.getSupportFragmentManager().popBackStack();
    }

    // -----------------------------------------------------------------------------------------
    protected void onDestroy()
    {
        // 清理一些无用的数据
        FrameApplication.removeFromList(this);
        super.onDestroy();
    }

    /**
     * 1) 初始化变量 最先被调用 用于初始化一些变量，创建一些对象
     */
    protected abstract void onInitVariable();

    /**
     * 2) 初始化UI 布局载入操作
     *
     * @param savedInstanceState
     */
    protected abstract void onInitView(final Bundle savedInstanceState);
    /**
     * 3） 请求数据
     *
     */
    protected abstract void onRequestData();
    /**
     * 4) 数据加载 onResume时候调用
     */
    protected abstract void onLoadData();
    /**
     * 5) 数据卸载 onPause时候调用
     */
    protected abstract void onUnLoadData();
    protected void onPause()
    {
        super.onPause();
        this.onUnLoadData();
    }
    // ---------------------------------------------------------------------------------------------

    protected void onResume()
    {
        super.onResume();
        this.onLoadData();
    }

    protected void onStop()
    {
        super.onStop();
    }

    private void startView(final Bundle savedInstanceState)
    {
//        if (this.isShowNetOff)
//        {
//            final NetworkInfo netInfo = Utils.getNetworkInfo(this);
//            if (netInfo == null)
//            {
//                Toast.makeText(this, "网络错误", Toast.LENGTH_LONG).show();
//                this.onInitView(savedInstanceState);
//            }
//            else
//            {
//                this.onInitView(savedInstanceState);
//                this.onRequestData();
//            }
//        }
//        else
//        {
            this.onInitView(savedInstanceState);
            this.onRequestData();
//        }
    }

}
