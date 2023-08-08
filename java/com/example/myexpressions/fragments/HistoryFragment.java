package com.example.myexpressions.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myexpressions.R;
import com.example.myexpressions.network.DatabaseBoss;

import java.util.Iterator;
import java.util.Map;

public class HistoryFragment extends Fragment {

    TextView history;
    Button fetchBtn;
    DatabaseBoss databaseBoss;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseBoss = new DatabaseBoss(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        history = view.findViewById(R.id.historyText);
        fetchBtn = view.findViewById(R.id.fetchHistory);

        fetchBtn.setOnClickListener(view1 -> {
            setHistory();
        });

        return view;
    }

    public void setHistory() {
        new Thread(() -> {
            Map<String, String> historyList =  databaseBoss.fetchData();
            Iterator iterator = historyList.keySet().iterator();
            String result = "";
            while(iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = (String) historyList.get(key);
                result += " "+key + " = " + value.trim() + "\n";
            }
            String finalResult = result;
            getActivity().runOnUiThread(()-> {
                if (finalResult.isEmpty()) {
                    history.setText(" Empty History");
                } else {
                    history.setText(finalResult);
                }
            });
        }).start();
        history.setText(" Empty History");
    }
}