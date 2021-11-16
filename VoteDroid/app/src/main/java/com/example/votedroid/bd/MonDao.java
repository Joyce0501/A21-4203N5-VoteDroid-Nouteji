package com.example.votedroid.bd;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.votedroid.modele.VDQuestion;

@Dao
public interface MonDao {
    @Insert
    Long insertQuestion(VDQuestion v);

    //TODO Compléter les autres actions

}
