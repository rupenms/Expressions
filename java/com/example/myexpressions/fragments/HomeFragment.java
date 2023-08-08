package com.example.myexpressions.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myexpressions.MainActivity;
import com.example.myexpressions.R;
import com.example.myexpressions.network.DatabaseBoss;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import kotlin.text.Charsets;

public class HomeFragment extends Fragment {

    Button submitBtn;
    EditText inputText;
    SendApiCall sendApiCall;
    MainActivity mainActivity;
    DatabaseBoss databaseBoss;
    Date date;
    String inputExp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = new MainActivity();
        databaseBoss = new DatabaseBoss(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        inputText = view.findViewById(R.id.inputExpression);
        submitBtn = view.findViewById(R.id.submit);

        submitBtn.setOnClickListener(view1 -> {
            inputExp = inputText.getText().toString();
            try {
                String encodedInputExp = URLEncoder.encode(inputExp, Charsets.UTF_8.name());
                sendApiCall = new SendApiCall();
                sendApiCall.execute("https://api.mathjs.org/v4/?expr="+encodedInputExp);
                Toast.makeText(getContext(),"Expression Submited ",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return view;
    }

    public class SendApiCall extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {
                URL url = new URL(strings[0]);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            storeData(s);
        }
    }

    private void storeData(String result) {
        List<String> output = new ArrayList<>();
        if (result.contains(",")) {
            result = result.substring(1, result.length()-1);
            String[] out = result.split(",");
            for (String o:out) {
                output.add(o);
            }
        } else {
            output.add(result);
        }
        String[] input = inputExp.split("\n");
        new Thread(() -> {
            for (int i=0;i<input.length;i++) {
                date = new Date();
                databaseBoss.insertData(input[i], output.get(i), date.getTime());
            }
        }).start();
    }
}