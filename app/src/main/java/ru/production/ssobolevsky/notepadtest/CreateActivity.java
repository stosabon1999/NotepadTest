package ru.production.ssobolevsky.notepadtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateActivity extends AppCompatActivity {

    private Button mButton;
    private EditText mEditTextTitle;
    private EditText mEditTextSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        init();
        initListeners();
    }

    private void init() {
        mButton = findViewById(R.id.btn_create);
        mEditTextTitle = findViewById(R.id.et_note_title);
        mEditTextSubtitle = findViewById(R.id.et_note_subtitle);
    }

    private void initListeners() {
        mButton.setOnClickListener(new CreateButtonclickListener());
    }

    public static final Intent newIntent(Context context) {
        Intent intent = new Intent(context, CreateActivity.class);
        return intent;
    }

    private class CreateButtonclickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DbManager manager = new DbManager(CreateActivity.this);
                    manager.addNote(new MyNote(mEditTextTitle.getText().toString(), mEditTextSubtitle.getText().toString()));
                }
            }).start();
        }
    }
}
