package com.example.easywin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.easywin.R;
import com.example.easywin.model.HistoryRecord;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<HistoryRecord> data;

    public DataAdapter(Context context, List<HistoryRecord> lines) {
        this.data = lines;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.history_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        HistoryRecord record = data.get(position);
        holder.recordNameView.setText(record.partyName);
        holder.amountOfMoneyView.setText(record.amountOfMoney + "\u20BD");

        String relativeCount = record.currentPeople + "/" + record.allPeople;
        holder.relativeView.setText(relativeCount);

        if (Integer.parseInt(record.currentPeople) == Integer.parseInt(record.allPeople)) {
            holder.tickView.setVisibility(View.VISIBLE);
        }

//        setColorsForPercents(holder.relativeView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView recordNameView;
        final TextView amountOfMoneyView;
        final TextView relativeView;
        final ImageView tickView;

        ViewHolder(View view) {
            super(view);
            recordNameView = view.findViewById(R.id.record_name_view);
            amountOfMoneyView = view.findViewById(R.id.amount_of_money_view);
            relativeView = view.findViewById(R.id.relative_people_view);
            tickView = view.findViewById(R.id.tick_view);
        }
    }

    private void setColorsForPercents(TextView view) {
//        int value = Integer.parseInt(percent);
////        percent += "%";
//        holder.relativeView.setText(percent);
//
////        if (value == 100) {
////            holder.relativeView.setTextColor((int)this.getItemId(R.color.colorGreen));
////        } else {
////            holder.relativeView.setTextColor((int)this.getItemId(R.color.errorColor));
////        }
    }
}
