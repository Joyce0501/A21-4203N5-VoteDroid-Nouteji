package com.example.votedroid.modele;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = VDQuestion.class,
        parentColumns = "idQuestion",
        childColumns = "questionId"),
        indices = {@Index("questionId")}
        )

public class VDVote {
    //TODO Champs à définir

    @PrimaryKey(autoGenerate = true)
    public Long idVote;

    @ColumnInfo
    public String nomVotant;

    @ColumnInfo
    public Long questionId;


}
