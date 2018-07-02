package ru.production.ssobolevsky.notepadtest;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;

public class EditActivity extends AppCompatActivity {

    public static final String DATA = "DATA";
    public static final String NAME = "NAME";
    private static final int WATCH = 10001;
    private static final int UPDATE = 10002;

    private CheckBox mCheckBox;
    private MyNote mMyNote;
    private Handler mUiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WATCH) {
                mMyNote = (MyNote) msg.obj;
                Bundle bundle = new Bundle();
                bundle.putParcelable(DATA, mMyNote);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .add(R.id.fl_content, WatchFragment.newInstance(bundle))
                        .commit();
            }
        }
    };
    private WatchItemHandlerThread mWatchItemHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        initListeners();
    }

    private void init() {
        mCheckBox = findViewById(R.id.checkBox);
        mWatchItemHandlerThread = new WatchItemHandlerThread("WatchThread");
        mWatchItemHandlerThread.start();
        mWatchItemHandlerThread.postTask(new Runnable() {
            @Override
            public void run() {
                DbManager manager = new DbManager(EditActivity.this);
                Message msg = new Message();
                msg.what = WATCH;
                msg.obj = manager.getNoteByName(getIntent().getStringExtra(NAME));
                mUiHandler.sendMessage(msg);
            }
        });
    }

    private void initListeners() {
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.fl_content, EditFragment.newInstance())
                            .commit();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(DATA, mMyNote);
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.fl_content, WatchFragment.newInstance(bundle))
                            .commit();
                }
            }
        });
    }

    public static final Intent newIntent(Context context, String name) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra(NAME, name);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWatchItemHandlerThread.quit();
    }
}
