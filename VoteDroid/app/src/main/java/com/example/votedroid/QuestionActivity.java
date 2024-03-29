package com.example.votedroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.votedroid.bd.BD;
import com.example.votedroid.databinding.ActivityQuestionBinding;
import com.example.votedroid.exceptions.MauvaiseQuestion;
import com.example.votedroid.modele.VDQuestion;
import com.example.votedroid.service.ServiceImplementation;

public class QuestionActivity extends AppCompatActivity {

    private ActivityQuestionBinding binding;
    private ServiceImplementation service;
    private BD maBD;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        maBD =  Room.databaseBuilder(getApplicationContext(), BD.class, "BDQuestions")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        service = ServiceImplementation.getInstance(maBD);

        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.buttonPoser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VDQuestion maQuestion = new VDQuestion();
                maQuestion.texteQuestion = binding.edit.getText().toString();

                try{
                    service.creerQuestion(maQuestion);
                    Intent accueil = new Intent(QuestionActivity.this,MainActivity.class);
                    startActivity(accueil);
                }
                catch (MauvaiseQuestion Exception){
                    Toast.makeText(QuestionActivity.this, Exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
