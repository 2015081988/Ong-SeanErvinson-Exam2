package com.ong.practicalexam2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SummaryActivity extends AppCompatActivity {

    private Button mPrevious, mSend;

    private TextView mResultComment, mRequestedSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        initializeWidget();

        String data = fetchData();
        final String[] subjects = getSubjects(data);
        populateSubjects(subjects);
        populateComment(getComment(data));
    }

    private void populateComment(String comment) {
        mResultComment.setText(comment);
    }


    private void populateSubjects(String[] subjects){
        StringBuilder sb = new StringBuilder();
        sb.append("List of Requested Subject").append("\n");
        for(int i = 0; i < subjects.length; i++){
            if(subjects[i].length() == 0 || subjects[i].isEmpty())
                continue;
            sb.append(subjects[i]).append("\n");
        }
        mRequestedSubject.setText(sb.toString());
    }

    private void initializeWidget() {
        mPrevious = findViewById(R.id.btn_previous);
        mSend = findViewById(R.id.btn_send);
        mResultComment = findViewById(R.id.text_view_result_comment);
        mRequestedSubject = findViewById(R.id.text_view_request_subjects);
        
        mPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(MainActivity.class);
            }
        });
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Request Send", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openActivity(Class activityClass){
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    private String getComment(String data){
        return data.trim().substring(data.lastIndexOf("comment:") + 8);
    }


    private String[] getSubjects(String data) {
        return data.trim().substring(0, data.lastIndexOf("comment:")).split("~");
    }

    private String fetchData() {
        FileInputStream stream = null;
        StringBuilder sb = new StringBuilder();
        String filename = "data.txt";
        try {
            stream = openFileInput(filename);
            int i;
            while ((i = stream.read()) != -1) {
                sb.append((char) i);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            Log.d("error", "File not found");
        } catch (IOException e) {
            Log.d("error", "IO error");
        }finally {
            if(stream != null){
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
