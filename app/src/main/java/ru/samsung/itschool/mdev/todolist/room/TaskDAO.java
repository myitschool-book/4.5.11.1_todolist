package ru.samsung.itschool.mdev.todolist.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TaskDAO {

    @Query("SELECT * FROM task ORDER BY id DESC")
    Flowable<List<Task>> getAll();

    @Insert
    void insert(Task t);

    // todo

}