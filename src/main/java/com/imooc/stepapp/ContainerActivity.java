package com.imooc.stepapp;

import android.content.Intent;
import android.os.Bundle;
import com.v210.frame.BaseActivity;
import com.v210.frame.BaseFragment;
import com.v210.utils.LogWriter;

public class ContainerActivity extends BaseActivity
{
    public static final String KEY_FRAGMENT_NAME = "fragment_name";
    private Object fragmentObj;
    private Bundle bundle = null;

    private Object getIntentFragment(Intent intent)
    {
        bundle = intent.getExtras();
        String fragmentName = intent.getStringExtra(KEY_FRAGMENT_NAME);
        if (fragmentName != null)
        {
            try
            {
                Class clazz = Class.forName(fragmentName);
                return clazz.newInstance();
            } catch (ClassNotFoundException e)
            {
                LogWriter.e(e.toString());
            } catch (InstantiationException e)
            {
                LogWriter.e(e.toString());
            } catch (IllegalAccessException e)
            {
                LogWriter.e(e.toString());
            }
        }
        return null;
    }

    protected void onNewIntent(Intent intent)
    {
        fragmentObj = getIntentFragment(intent);
        addFragmentToView(fragmentObj);
    }

    private void addFragmentToView(Object frag)
    {
        if (frag != null && (frag instanceof BaseFragment))
        {
            BaseFragment baseFragment = ((BaseFragment) frag);
            if (bundle != null)
            {
                baseFragment.setArguments(bundle);
            }
            if (getCurrentFragment() != null)
            {
                replaceFregment(R.id.container, baseFragment, true);
            } else
            {
                addFregment(R.id.container, baseFragment, true);
            }
        }
    }

    protected void onInitVariable()
    {
        fragmentObj = getIntentFragment(getIntent());
    }

    protected void onInitView(Bundle savedInstanceState)
    {
        setContentView(R.layout.act_container);
        addFragmentToView(fragmentObj);
    }

    protected void onLoadData()
    {

    }

    protected void onRequestData()
    {

    }

    protected void onUnLoadData()
    {

    }
}
