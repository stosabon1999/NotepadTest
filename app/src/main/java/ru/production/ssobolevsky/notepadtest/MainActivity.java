package ru.production.ssobolevsky.notepadtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 12345;

    private Button mAddButton;
    private Button mSettingsButton;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mManager;
    private MyAdapter mAdapter;
    private List<MyNote> mData;

    private HandlerThread mHandlerThread = new HandlerThread("HandlerThread");
    private Handler mRequestHandler;
    private Handler mResponseHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            mData = (List<MyNote>) msg.obj;
            mAdapter.setData(mData);
        }
    };

    private Runnable mTask = new Runnable() {
        @Override
        public void run() {
            DbManager manager = new DbManager(MainActivity.this);
            Message msg = new Message();
            msg.obj = manager.getNotes();
            mResponseHandler.sendMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initListeners();
        mHandlerThread.start();
        mRequestHandler = new Handler(mHandlerThread.getLooper());
    }

    private void init() {
        mAddButton = findViewById(R.id.add_btn);
        mSettingsButton = findViewById(R.id.btn_settings);
        mRecyclerView = findViewById(R.id.rv_notes);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        mAdapter = new MyAdapter(MainActivity.this);
        mData = new ArrayList<>();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRequestHandler.post(mTask);
    }


    private void initListeners() {
        mAddButton.setOnClickListener(new AddButtonClickListener());
        mSettingsButton.setOnClickListener(new SettingsButtonClickListener());
    }

    private class AddButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivity(CreateActivity.newIntent(MainActivity.this));
        }
    }

    private class SettingsButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivityForResult(SettingsActivity.newIntent(MainActivity.this), REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CODE == requestCode) {
            SharedPreferences preferences = getSharedPreferences(SettingsActivity.PREF_NAME, Context.MODE_PRIVATE);
            mAdapter.setPrefs(preferences.getInt(SettingsActivity.COLOR, android.R.color.black));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit();
    }
}
