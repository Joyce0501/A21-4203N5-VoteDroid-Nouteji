package com.example.votedroid.bd;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.votedroid.modele.VDQuestion;

@Database(entities = {VDQuestion.class}, version = 2)
public abstract class BD extends RoomDatabase {
    public abstract MonDao monDao();
}
