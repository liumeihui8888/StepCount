package com.imooc.stepapp;


import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.v210.frame.*;
import com.v210.utils.*;

public class SettingActivity extends BaseActivity
{
    class ViewHolder
    {
        TextView title;
        TextView desc;
    }

    public class ListViewAdapter extends BaseAdapter
    {
        private Settings setting = null;
        private String[] listTitle = {"设置步长", "设置体重", "传感器敏感度", "传感器采样时间"};

        public ListViewAdapter()
        {
            setting = new Settings(SettingActivity.this);
        }

        public int getCount()
        {
            return listTitle.length;
        }

        public Object getItem(int position)
        {
            return listTitle[position];
        }

        public long getItemId(int position)
        {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder mViewHolder = null;
            if (convertView == null)
            {
                mViewHolder = new ViewHolder();
                convertView = View.inflate(SettingActivity.this, R.layout.item_setting, null);
                mViewHolder.title = (TextView) convertView.findViewById(R.id.title);
                mViewHolder.desc = (TextView) convertView.findViewById(R.id.desc);
                convertView.setTag(mViewHolder);
            }
            else
            {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            mViewHolder.title.setText(listTitle[position]);
            switch (position)
            {
                case 0:
                {
                    final float stepLen = setting.getSetpLength();
                    mViewHolder.desc.setText(String.format(getResources().getString(R.string.stepLen), stepLen));
                    convertView.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {
                            stepClick(stepLen);
                        }
                    });
                }
                break;
                case 1:
                {
                   final float bodyWeight = setting.getBodyWeight();
                    mViewHolder.desc.setText(String.format(getResources().getString(R.string.body_weight), bodyWeight));
                    convertView.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {
                            weightClick(bodyWeight);
                        }
                    });
                }
                break;
                case 2:
                {
                    double sensitivity = setting.getSensitivity();
                    mViewHolder.desc.setText(String.format(getResources().getString(R.string.sensitivity), Utils.getFormatVal(sensitivity, "#.00")));
                    convertView.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {
                            sensitiveClick();
                        }
                    });
                }
                break;
                case 3:
                {
                    int interval = setting.getInterval();
                    mViewHolder.desc.setText(String.format(getResources().getString(R.string.interval), Utils.getFormatVal(interval, "#.00")));
                    convertView.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View v)
                        {
                            setInterval();
                        }
                    });
                }
                break;
            }
            return convertView;
        }

        private void setInterval()
        {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingActivity.this, AlertDialog.THEME_HOLO_LIGHT);
            mBuilder.setItems(R.array.interval_array, new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    setting.setInterval(Settings.INTERVAL_ARRAY[which]);
                    if (adapter != null)
                    {
                        adapter.notifyDataSetChanged();
                    }
                }
            });
            mBuilder.setTitle("设置传感器采样间隔");
            mBuilder.create().show();
        }

        private void sensitiveClick()
        {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingActivity.this, AlertDialog.THEME_HOLO_LIGHT);
            mBuilder.setItems(R.array.sensitive_array, new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    setting.setSensitivity(Settings.SENSITIVE_ARRAY[which]);
                    if (adapter != null)
                    {
                        adapter.notifyDataSetChanged();
                    }
                }
            });
            mBuilder.setTitle("设置传感器灵敏度");
            mBuilder.create().show();
        }

        private void weightClick(float pBodyWeight)
        {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingActivity.this, AlertDialog.THEME_HOLO_LIGHT);
            mBuilder.setTitle("设置体重");
            View mView = View.inflate(SettingActivity.this, R.layout.view_dialog_input, null);
            final EditText mInput = (EditText) mView.findViewById(R.id.input);
            mInput.setText(String.valueOf(pBodyWeight));
            mBuilder.setView(mView);
            mBuilder.setNegativeButton("取消", null);
            mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    String val = mInput.getText().toString();
                    if (val != null && val.length() > 0)
                    {
                        float bodyWeight = Float.parseFloat(val);
                        setting.setBodyWeight(bodyWeight);
                        if (adapter != null)
                        {
                            adapter.notifyDataSetChanged();
                        }
                    }
                    else
                    {
                        Utils.makeToast(SettingActivity.this, "请输入正确的参数!");
                    }
                }
            });
            mBuilder.create().show();
        }

        private void stepClick(float pStepLen)
        {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingActivity.this, AlertDialog.THEME_HOLO_LIGHT);
            mBuilder.setTitle("设置步长");
            View mView = View.inflate(SettingActivity.this, R.layout.view_dialog_input, null);
            final EditText mInput = (EditText) mView.findViewById(R.id.input);
            mInput.setText(String.valueOf(pStepLen));
            mBuilder.setView(mView);
            mBuilder.setNegativeButton("取消", null);
            mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    String val = mInput.getText().toString();
                    if (val != null && val.length() > 0)
                    {
                        float len = Float.parseFloat(val);
                        setting.setStepLength(len);
                        if (adapter != null)
                        {
                            adapter.notifyDataSetChanged();
                        }
                    }
                    else
                    {
                        Utils.makeToast(SettingActivity.this, "请输入正确的参数!");
                    }
                }
            });
            mBuilder.create().show();
        }
    }

    private ListView listView;

    protected void onInitVariable()
    {

    }

    private ListViewAdapter adapter;

    protected void onInitView(Bundle savedInstanceState)
    {
        setContentView(R.layout.act_setting);
        TextView mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText("设置");
        ImageView leftImage = (ImageView) findViewById(R.id.leftImg);
        leftImage.setImageResource(R.drawable.left_arrow);
        leftImage.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                SettingActivity.this.finish();
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ListViewAdapter();
        listView.setAdapter(adapter);
    }

    protected void onRequestData()
    {

    }

    protected void onLoadData()
    {

    }

    protected void onUnLoadData()
    {

    }
}
