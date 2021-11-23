package com.example.votedroid.bd;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.votedroid.modele.VDQuestion;
import com.example.votedroid.modele.VDVote;
//import com.example.votedroid.modele.VDVote;

import java.util.List;

@Dao
public abstract class MonDao {
    @Insert
    public abstract Long insertQuestion(VDQuestion v);

    //TODO Compléter les autres actions"

    @Query("SELECT * FROM VDQuestion")
    public abstract List<VDQuestion> toutesLesQuestions();

    @Insert
    public abstract Long insertVote(VDVote vote);

    @Transaction
    public Long creerQuestionVote(VDQuestion q, List<VDVote> vs){
        Long id = this.insertQuestion(q);
        for (VDVote v : vs){
            v.questionId= id;
            this.insertVote(v);
        }
        return id;
    }
}
