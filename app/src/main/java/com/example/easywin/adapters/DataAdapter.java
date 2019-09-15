package com.example.easywin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.recordNameView.setText(record.getPartyName());
        holder.amountOfMoneyView.setText(record.getAmountOfMoney());
        holder.paymentPercentView.setText(record.getPercentOfPayment());

//        setColorsForPercents(holder.paymentPercentView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView recordNameView;
        final TextView amountOfMoneyView;
        final TextView paymentPercentView;

        ViewHolder(View view) {
            super(view);
            recordNameView = view.findViewById(R.id.record_name_view);
            amountOfMoneyView = view.findViewById(R.id.amount_of_money_view);
            paymentPercentView = view.findViewById(R.id.payment_percent_view);
        }
    }

    private void setColorsForPercents(TextView view) {
//        int value = Integer.parseInt(percent);
////        percent += "%";
//        holder.paymentPercentView.setText(percent);
//
////        if (value == 100) {
////            holder.paymentPercentView.setTextColor((int)this.getItemId(R.color.colorGreen));
////        } else {
////            holder.paymentPercentView.setTextColor((int)this.getItemId(R.color.errorColor));
////        }
    }
}
