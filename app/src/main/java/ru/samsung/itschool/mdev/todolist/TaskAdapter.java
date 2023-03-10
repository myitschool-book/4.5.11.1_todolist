package ru.samsung.itschool.mdev.todolist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import ru.samsung.itschool.mdev.todolist.room.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TasksViewHolder> {

    private final Context context;
    private List<Task> taskList;

    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        Task t = taskList.get(position);
        holder.tvTitle.setText(t.getTitle());
        holder.tvDescription.setText(t.getDescription());
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        if(t.getDeadline()!= null) {
            holder.tvDeadline.setText(String.format("%s: %s", context.getResources().getText(R.string.deadline), formatter.format(t.getDeadline())));
        } else {
            holder.tvDeadline.setText(R.string.empty);
        }
        if (t.isFinished()) {
            holder.tvStatus.setText(R.string.completed);
            holder.tvStatus.setBackgroundResource(R.color.colorAccent);
        }else{
            holder.tvStatus.setText(R.string.waiting);
            holder.tvStatus.setBackgroundResource(R.color.colorYellow);
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvStatus;
        private final TextView tvTitle;
        private final TextView tvDescription;
        private final TextView tvDeadline;

        public TasksViewHolder(View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.textViewStatus);
            tvTitle = itemView.findViewById(R.id.textViewTask);
            tvDescription = itemView.findViewById(R.id.textViewDesc);
            tvDeadline = itemView.findViewById(R.id.textViewDeadline);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // todo
        }
    }
}