package com.example.kolokvijumvezba;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TaskCountReceiver extends BroadcastReceiver {
    public interface Listener {
        void onTaskCountChanged(int count);
    }

    private final Listener listener;

    public TaskCountReceiver(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        if (!MainActivity.ACTION_TASKS_CHANGED.equals(intent.getAction())) return;
        int count = intent.getIntExtra(MainActivity.EXTRA_TASK_COUNT, 0);
        listener.onTaskCountChanged(count);
    }
}

