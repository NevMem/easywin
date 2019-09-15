package com.example.easywin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easywin.R;
import com.example.easywin.adapters.DataAdapter;
import com.example.easywin.model.HistoryRecord;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    final List<HistoryRecord> data = new ArrayList<>();

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_history, container, false);

        ((TextView)rootView.findViewById(R.id.kek)).setText(getText(R.string.history));

        RecyclerView recyclerView = rootView.findViewById(R.id.history_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DataAdapter adapter = new DataAdapter(getContext(), data);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void initData() {
        data.add(new HistoryRecord("Alex", "100", "23"));
        data.add(new HistoryRecord("Alex", "70", "100"));
        data.add(new HistoryRecord("Alex", "100", "85"));
        data.add(new HistoryRecord("Alex", "100", "23"));
        data.add(new HistoryRecord("Alex", "70", "100"));
        data.add(new HistoryRecord("Alex", "100", "85"));
        data.add(new HistoryRecord("Alex", "100", "23"));
        data.add(new HistoryRecord("Alex", "70", "100"));
        data.add(new HistoryRecord("Alex", "100", "85"));

    }

}
