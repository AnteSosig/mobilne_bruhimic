package com.example.kolokvijumvezba;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ZadaciFragment extends Fragment {
    private static final String REQ_KEY = "add_task";
    private static final String B_NAZIV = "naziv";
    private static final String B_VREME = "vreme";

    private FloatingActionButton fab;
    private ZadatakAdapter adapter;
    private TaskCountReceiver receiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_zadaci, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView rv = view.findViewById(R.id.recycler_zadaci);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ZadatakAdapter();
        rv.setAdapter(adapter);
        adapter.submit(ZadatakRepository.getTasks());

        fab = view.findViewById(R.id.fab_add);
        updateFabColor(ZadatakRepository.getCount());

        fab.setOnClickListener(v -> {
            AddZadatakDialogFragment dialog = new AddZadatakDialogFragment();
            dialog.show(getParentFragmentManager(), "add_zadatak");
        });

        getParentFragmentManager().setFragmentResultListener(REQ_KEY, getViewLifecycleOwner(), (requestKey, result) -> {
            String naziv = result.getString(B_NAZIV, "").trim();
            String vreme = result.getString(B_VREME, "").trim();
            if (naziv.isEmpty() || vreme.isEmpty()) return;

            int count = ZadatakRepository.addTask(new Zadatak(naziv, vreme));
            adapter.submit(ZadatakRepository.getTasks());

            Intent i = new Intent(MainActivity.ACTION_TASKS_CHANGED);
            i.putExtra(MainActivity.EXTRA_TASK_COUNT, count);
            requireContext().sendBroadcast(i);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        receiver = new TaskCountReceiver(this::updateFabColor);
        ContextCompat.registerReceiver(
                requireContext(),
                receiver,
                new IntentFilter(MainActivity.ACTION_TASKS_CHANGED),
                ContextCompat.RECEIVER_NOT_EXPORTED
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        if (receiver != null) {
            requireContext().unregisterReceiver(receiver);
            receiver = null;
        }
    }

    private void updateFabColor(int taskCount) {
        if (fab == null) return;
        boolean odd = (taskCount % 2) == 1;
        int color = odd ? R.color.fab_red : R.color.fab_blue;
        fab.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), color));
        ZadatakRepository.setFabRed(odd);
    }
}

