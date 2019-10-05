package com.ong.practicalexam2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private CheckBox mICS115, mIT205, mITELEC2, mITELEC3, mITELEC4, mICS125, mFELEC2, mFELEC3;
    private Button mSave, mNext;
    private EditText mComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeWidget();

    }

    private void initializeWidget() {
        mICS115 = findViewById(R.id.check_box_ics115);
        mIT205 = findViewById(R.id.check_box_it205);
        mITELEC2 = findViewById(R.id.check_box_itelect2);
        mITELEC3 = findViewById(R.id.check_box_itelect3);
        mITELEC4 = findViewById(R.id.check_box_itelect4);
        mICS125 = findViewById(R.id.check_box_ics125);
        mFELEC2 = findViewById(R.id.check_box_felec2);
        mFELEC3 = findViewById(R.id.check_box_felec3);
        mSave = findViewById(R.id.btn_save);
        mNext = findViewById(R.id.btn_next);
        mComment = findViewById(R.id.edit_text_source_comment);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(SummaryActivity.class);
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                sb.append(mICS115.isChecked() ? String.valueOf(mICS115.getText()) : "").append("~");
                sb.append(mIT205.isChecked() ? String.valueOf(mIT205.getText()) : "").append("~");
                sb.append(mITELEC2.isChecked() ? String.valueOf(mITELEC2.getText()) : "").append("~");
                sb.append(mITELEC3.isChecked() ? String.valueOf(mITELEC3.getText()) : "").append("~");
                sb.append(mITELEC4.isChecked() ? String.valueOf(mITELEC4.getText()) : "").append("~");
                sb.append(mICS125.isChecked() ? String.valueOf(mICS125.getText()) : "").append("~");
                sb.append(mFELEC2.isChecked() ? String.valueOf(mFELEC2.getText()) : "").append("~");
                sb.append(mFELEC3.isChecked() ? String.valueOf(mFELEC3.getText()) : "").append("~");
                sb.append(String.format("comment:%s", String.valueOf(mComment.getText())));
                Toast.makeText(view.getContext(), "Data saving", Toast.LENGTH_SHORT).show();
                insertData(sb.toString());
                Toast.makeText(view.getContext(), "Successfully Saved", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void openActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    private void insertData(String data) {
        FileOutputStream stream = null;
        try {
            String filename = "data.txt";
            stream = openFileOutput(filename, Context.MODE_PRIVATE);
            stream.write(data.getBytes());
        } catch (FileNotFoundException e) {
            Log.d("error", "File not found");
        } catch (IOException e) {
            Log.d("error", "IO error");
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
