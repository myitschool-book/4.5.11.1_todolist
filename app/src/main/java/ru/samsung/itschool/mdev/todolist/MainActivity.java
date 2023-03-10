package ru.samsung.itschool.mdev.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.disposables.Disposable;
import ru.samsung.itschool.mdev.todolist.fragment.AddDialogFragment;
import ru.samsung.itschool.mdev.todolist.room.DBClient;
import ru.samsung.itschool.mdev.todolist.room.Task;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Task> tasksList;
    private TaskAdapter adapter;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(MainActivity.this, tasksList);
        recyclerView.setAdapter(adapter);

        ExtendedFloatingActionButton buttonAddTask = findViewById(R.id.floating_button_add);
        buttonAddTask.setOnClickListener(view -> {
            DialogFragment dialogFragment = new AddDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "");
        });
        final TaskAdapter adapter = new TaskAdapter(MainActivity.this, tasksList);

        DBClient
            .getInstance(getApplicationContext())
            .getAppDatabase()
            .taskDao()
            .getAll()
            // observeOn(AndroidSchedulers.mainThread()): заставляет подписчика
            // выполнять свой результат в основном потоке пользовательского интерфейса Android.
            // Это необходимо для изменения UI в Android.
            .observeOn(AndroidSchedulers.mainThread())
            // подписка
            .subscribe(tasks -> {
                adapter.setTaskList(tasks);
                recyclerView.setAdapter(adapter);
            });
    }
}