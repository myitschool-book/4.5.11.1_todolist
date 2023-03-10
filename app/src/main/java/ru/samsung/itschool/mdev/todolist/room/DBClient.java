package ru.samsung.itschool.mdev.todolist.room;

import android.content.Context;
import androidx.room.Room;

public class DBClient {

    private Context mContext;
    private static DBClient mInstance;

    private AppDB appDatabase;

    private DBClient(Context context) {
        this.mContext = context;
        //Создание БД - MyToDo
        appDatabase = Room.databaseBuilder(mContext, AppDB.class, "MyToDo").build();
    }

    public static DBClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DBClient(mCtx);
        }
        return mInstance;
    }

    public AppDB getAppDatabase() {
        return appDatabase;
    }
}