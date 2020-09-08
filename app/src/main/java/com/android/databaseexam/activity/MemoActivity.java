package com.android.databaseexam.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.android.databaseexam.MemoContract;
import com.android.databaseexam.MemoDbHelper;
import com.android.databaseexam.R;

public class MemoActivity extends AppCompatActivity {

    private EditText et_title_edit;
    private EditText et_content_edit;
    private long mMemoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        et_content_edit = (EditText)findViewById(R.id.et_content_edit);
        et_title_edit = (EditText)findViewById(R.id.et_title_edit);
        Intent intent = getIntent();
        if(intent!= null){
            mMemoId = intent.getLongExtra("id", -1);
            String title = intent.getStringExtra("title");
            String content = intent.getStringExtra("content");
            et_title_edit.setText(title);
            et_content_edit.setText(content);
        }
    }

    @Override
    public void onBackPressed(){
        String title = et_title_edit.getText().toString();
        String content = et_content_edit.getText().toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, title);
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_CONTENTS, content);
        SQLiteDatabase db = MemoDbHelper.getInstance(this).getWritableDatabase();

        if(mMemoId == -1){
            long newRowId = db.insert(MemoContract.MemoEntry.TABLE_NAME, null, contentValues);
            if(newRowId == -1){
                Toast.makeText(this,"저장에 문제가 발생", Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(this,"저장 성공", Toast.LENGTH_LONG).show();
                setResult(RESULT_OK);
            }
        }else{
            int count = db.update(MemoContract.MemoEntry.TABLE_NAME, contentValues, MemoContract.MemoEntry._ID+" = "+mMemoId,null);
            if(count == 0){
                Toast.makeText(this, "수정에 문제 발생",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "수정 완료",Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        }


        super.onBackPressed();
    }
}