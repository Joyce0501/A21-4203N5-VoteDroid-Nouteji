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
public interface MonDao {
    @Insert
    public Long insertQuestion(VDQuestion v);

    //TODO Compléter les autres actions"

    @Query("SELECT * FROM VDQuestion")
    public List<VDQuestion> toutesLesQuestions();

    @Query("SELECT * FROM VDVote")
    public List<VDVote> tousLesVotes();

    @Query("SELECT * FROM VDVote WHERE questionId = :idQuestion")
    public List<VDVote> tousLesVotesPourUneQuestion(Long idQuestion);

    @Query("DELETE FROM VDQuestion")
    void deleteQuestions();

    @Query("DELETE FROM VDVote")
    void deleteVotes();

    @Insert
    public Long insertVote(VDVote vote);


}


