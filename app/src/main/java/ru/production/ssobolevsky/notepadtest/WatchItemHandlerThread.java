package ru.production.ssobolevsky.notepadtest;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by pro on 02.07.2018.
 */

public class WatchItemHandlerThread extends HandlerThread {

    private Handler mHandler = new Handler();

    public WatchItemHandlerThread(String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler(getLooper());
    }

    public void postTask(Runnable task) {
        mHandler.post(task);
    }
}
