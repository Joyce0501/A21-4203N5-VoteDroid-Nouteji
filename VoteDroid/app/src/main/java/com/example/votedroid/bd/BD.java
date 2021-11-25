package com.example.votedroid.bd;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.votedroid.modele.VDQuestion;
import com.example.votedroid.modele.VDVote;


@Database(entities = {VDQuestion.class, VDVote.class}, version = 3)
public abstract class BD extends RoomDatabase {
    public abstract MonDao monDao();
}
