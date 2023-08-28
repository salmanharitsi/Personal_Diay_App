package id.co.salmanharitsi.personaldiary.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import id.co.salmanharitsi.personaldiary.models.Diary;

@Database(entities = {Diary.class}, version = 2)
public abstract class DiaryDatabase extends RoomDatabase {

    private static final String TAG = "DiaryDatabase";
    public static final String DATABASE_NAME = "diary_db";
    private static DiaryDatabase instance;
    public abstract DiaryDao getDiaryDao();

    static DiaryDatabase getInstance(final Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(), DiaryDatabase.class, DATABASE_NAME).build();
        }
        return instance;
    }
}
