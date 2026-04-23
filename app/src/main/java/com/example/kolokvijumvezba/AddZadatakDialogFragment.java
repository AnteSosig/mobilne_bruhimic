package com.example.kolokvijumvezba;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Locale;

public class AddZadatakDialogFragment extends DialogFragment {
    private static final String REQ_KEY = "add_task";
    private static final String B_NAZIV = "naziv";
    private static final String B_VREME = "vreme";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = requireActivity().getLayoutInflater().inflate(R.layout.dialog_add_zadatak, null);

        EditText etNaziv = v.findViewById(R.id.et_naziv);
        EditText etVreme = v.findViewById(R.id.et_vreme);
        Button btnOdustani = v.findViewById(R.id.btn_odustani);
        Button btnPotvrdi = v.findViewById(R.id.btn_potvrdi);

        etVreme.setOnClickListener(view -> showTimePicker(etVreme));

        btnOdustani.setOnClickListener(view -> dismiss());
        btnPotvrdi.setOnClickListener(view -> {
            Bundle b = new Bundle();
            b.putString(B_NAZIV, etNaziv.getText() == null ? "" : etNaziv.getText().toString());
            b.putString(B_VREME, etVreme.getText() == null ? "" : etVreme.getText().toString());
            getParentFragmentManager().setFragmentResult(REQ_KEY, b);
            dismiss();
        });

        return new AlertDialog.Builder(requireContext())
                .setTitle("Novi zadatak")
                .setView(v)
                .create();
    }

    private void showTimePicker(EditText target) {
        TimePickerDialog dialog = new TimePickerDialog(
                requireContext(),
                (TimePicker view, int hourOfDay, int minute) -> target.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)),
                12,
                0,
                true
        );
        dialog.show();
    }
}

