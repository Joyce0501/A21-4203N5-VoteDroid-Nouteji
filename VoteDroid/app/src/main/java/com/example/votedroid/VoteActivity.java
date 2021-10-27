package com.example.votedroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.votedroid.databinding.ActivityVoteBinding;

public class VoteActivity extends AppCompatActivity {

    private  ActivityVoteBinding binding;

    RatingBar ratingBar;
    float ratevalue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        binding.buttonVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Liste = new Intent(VoteActivity.this,MainActivity.class);
                startActivity(Liste);
            }
        });

    }
}
