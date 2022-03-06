package cn.dream.study.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.dream.study.R;

public class DataAdapter extends BaseAdapter {

    private String[] mData;
    private Context mContext;

    public DataAdapter(Context mContext, String[] mData) {
        this.mContext = mContext;
        this.mData = mData;

    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_data, null);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mTextView.setText(mData[position]);
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView;

        public ViewHolder(View view) {
            mTextView = view.findViewById(R.id.tv_text);
        }
    }
}
