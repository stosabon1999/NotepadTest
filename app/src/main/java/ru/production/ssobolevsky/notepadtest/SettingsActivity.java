package ru.production.ssobolevsky.notepadtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    public static final String PREF_NAME = "PREF_TEXT";
    public static final String COLOR = "COLOR";
    public static final String TEXT_SIZE = "TEXT_SIZE";

    private Button mButtonGreen;
    private Button mButtonRed;
    private Button mButtonBlack;
    private TextView mTextView;

    private int mColor;
    private int mSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mColor = pref.getInt(COLOR, android.R.color.black);
        mSize = pref.getInt(TEXT_SIZE, 18);
        init();
        initListeners();
    }

    private void init() {
        mButtonBlack = findViewById(R.id.btn_black);
        mButtonGreen = findViewById(R.id.btn_green);
        mButtonRed = findViewById(R.id.btn_red);
        mTextView = findViewById(R.id.tv_color);
    }

    private void initListeners() {
        mButtonBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mColor = Color.BLACK;
                mTextView.setTextColor(mColor);
            }
        });
        mButtonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mColor = Color.GREEN;
                mTextView.setTextColor(mColor);
            }
        });
        mButtonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mColor = Color.RED;
                mTextView.setTextColor(mColor);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        savePreferences();
        setResult(RESULT_OK);
    }

    private void savePreferences() {
        SharedPreferences pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        pref.edit().putInt(COLOR, mColor).putInt(TEXT_SIZE, mSize).apply();
    }

    public static final Intent newIntent(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        return intent;
    }
}
