package com.example.votedroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.votedroid.bd.BD;
import com.example.votedroid.databinding.ActivityVoteBinding;
import com.example.votedroid.exceptions.MauvaisVote;
import com.example.votedroid.exceptions.MauvaiseQuestion;
import com.example.votedroid.modele.VDQuestion;
import com.example.votedroid.modele.VDVote;
import com.example.votedroid.service.ServiceImplementation;

public class VoteActivity extends AppCompatActivity {

    private  ActivityVoteBinding binding;
    private ServiceImplementation service;
    private BD maBD;

    RatingBar ratingBar;
    float ratevalue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        maBD =  Room.databaseBuilder(getApplicationContext(), BD.class, "BDQuestions")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        service = ServiceImplementation.getInstance(maBD);

        setContentView(R.layout.activity_vote);
        ratingBar = findViewById(R.id.etoiles);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratevalue = ratingBar.getRating();
            }
        });

        binding = ActivityVoteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Long monId = getIntent().getLongExtra("id",-1);
        String leTexte = getIntent().getStringExtra("texte");
        binding.LaQuestion.setText(leTexte);
        binding.buttonVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingBar rbar = (RatingBar) findViewById(R.id.etoiles);
                binding.LaQuestion.setText(leTexte);
                VDVote monVote = new VDVote();
                monVote.nomVotant = binding.editNom.getText().toString();
                monVote.nbreVote = (int)rbar.getRating();
                monVote.questionId = monId;
                try{
                    service.creerVote(monVote);
                    Intent Liste = new Intent(VoteActivity.this,MainActivity.class);
                    startActivity(Liste);
                }
                catch (MauvaisVote Exception){
                    Toast.makeText(VoteActivity.this, Exception.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent vote = new Intent(VoteActivity.this,VoteActivity.class);
                    startActivity(vote);
                }
            }
        });
    }
}
