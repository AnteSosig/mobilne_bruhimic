package com.example.kolokvijumvezba;

import android.content.BroadcastReceiver;
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

public class KorisniciFragment extends Fragment {
    private View root;
    private BroadcastReceiver yellowReceiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_korisnici, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        root = view.findViewById(R.id.korisnici_root);
    }

    @Override
    public void onStart() {
        super.onStart();
        yellowReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (root == null) return;
                root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.korisnici_yellow));
            }
        };
        ContextCompat.registerReceiver(
                requireContext(),
                yellowReceiver,
                new IntentFilter(MainActivity.ACTION_MAKE_KORISNICI_YELLOW),
                ContextCompat.RECEIVER_NOT_EXPORTED
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        if (yellowReceiver != null) {
            requireContext().unregisterReceiver(yellowReceiver);
            yellowReceiver = null;
        }
    }
}

