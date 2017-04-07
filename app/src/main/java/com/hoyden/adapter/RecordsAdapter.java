package com.hoyden.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hoyden.bean.RecordsBean;
import com.hoyden.tallybook.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nhd on 2017/4/7.
 */

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.RecordsViewHolder> {
    private List<RecordsBean> mDatas;
    private Context context;
    private OnItemLongClickListener listener;

    public RecordsAdapter(List<RecordsBean> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
    public RecordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_records, parent, false);
        return new RecordsViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecordsViewHolder holder, int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mDatas.get(position).getDate());
        String date = getWeek(calendar.get(Calendar.DAY_OF_WEEK)) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "日";
        holder.date.setText(date);
        holder.content.setText(mDatas.get(position).getContent());
        holder.type.setText(mDatas.get(position).getType());
        holder.price.setText("￥" + mDatas.get(position).getPrice());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != listener) {
                    listener.longClick(holder.getAdapterPosition());
                }
                return false;
            }
        });
    }

    public interface OnItemLongClickListener{
        void longClick(int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void refresh(List<RecordsBean> datas) {
        if (null == datas) {
            mDatas = new ArrayList<>();
        } else {
            mDatas = new ArrayList<>(datas);
        }
    }

    static class RecordsViewHolder extends RecyclerView.ViewHolder {
        TextView date, type, content, price;

        RecordsViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            type = (TextView) itemView.findViewById(R.id.type);
            content = (TextView) itemView.findViewById(R.id.content);
            price = (TextView) itemView.findViewById(R.id.price);
        }
    }

    private String getWeek(int i) {
        switch (i) {
            case Calendar.SUNDAY:
                return "周日";
            case Calendar.MONDAY:
                return "周一";
            case Calendar.TUESDAY:
                return "周二";
            case Calendar.WEDNESDAY:
                return "周三";
            case Calendar.THURSDAY:
                return "周四";
            case Calendar.FRIDAY:
                return "周五";
            case Calendar.SATURDAY:
                return "周六";
        }
        return "";
    }

}
