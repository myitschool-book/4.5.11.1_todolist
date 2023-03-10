package ru.samsung.itschool.mdev.todolist.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 4)
public abstract class AppDB extends RoomDatabase {
    public abstract TaskDAO taskDao();
}