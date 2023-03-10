package ru.samsung.itschool.mdev.todolist.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import ru.samsung.itschool.mdev.todolist.R;
import ru.samsung.itschool.mdev.todolist.room.DBClient;
import ru.samsung.itschool.mdev.todolist.room.Task;

public class AddDialogFragment extends DialogFragment {
    private Context context;
    private DatePicker datePicker;
    private Date date;
    private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireActivity());
        View viewd = getActivity().getLayoutInflater().inflate(R.layout.add_layout, null);
        final EditText task = viewd.findViewById(R.id.editTitle);
        final EditText desc = viewd.findViewById(R.id.editDescription);

        Date today = new Date();
        try {
            date = formatter.parse(formatter.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        datePicker = viewd.findViewById(R.id.deadLine);
        datePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
            try {
                date = formatter.parse(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        alertDialogBuilder.setView(viewd)
                .setTitle(getString(R.string.add_btn))
                .setPositiveButton(R.string.save_btn, (dialog, which) -> saveTask(task.getText().toString(),desc.getText().toString(),date))
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());

        return alertDialogBuilder.create();
    }

    private void saveTask(String title, String description, Date date) {
         new Thread(() -> {
             Task task = new Task();
             task.setTitle(title);
             task.setDescription(description);
             task.setDeadline(date);
             task.setFinished(false);
             DBClient.getInstance(context).getAppDatabase()
                .taskDao()
                .insert(task);
        }).start();

    }
}