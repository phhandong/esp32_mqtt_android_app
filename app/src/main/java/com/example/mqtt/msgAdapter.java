package com.example.mqtt;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class msgAdapter extends BaseAdapter{

    private List<msgBean> mDatas;
    private LayoutInflater mInflater;

    public msgAdapter(Context context, List<msgBean> datas) {
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    public msgAdapter(){

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        msgBean msgBean = mDatas.get(position);
        return msgBean.getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            if(getItemViewType(position) == 0){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.activity_receive,null);
                holder.icon = (ImageView) convertView.findViewById(R.id.receive_head_portrait);
                holder.text = (TextView) convertView.findViewById(R.id.receive_msg);
            }else{
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.activity_send,null);
                holder.icon = (ImageView) convertView.findViewById(R.id.send_head_portrait);
                holder.text = (TextView) convertView.findViewById(R.id.text_to_send);
            }
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setImageBitmap(mDatas.get(position).getIcon());
//        holder.text.setText(mDatas.get(position).getText());

        return convertView;
    }

    public final class ViewHolder{
        public ImageView icon;
        public TextView text;
    }


}
