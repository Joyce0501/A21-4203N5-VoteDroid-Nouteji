package com.example.votedroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.votedroid.bd.BD;
import com.example.votedroid.databinding.ActivityMainBinding;
import com.example.votedroid.exceptions.MauvaiseQuestion;
import com.example.votedroid.modele.VDQuestion;
import com.example.votedroid.service.ServiceImplementation;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    QuestionsAdapter adapter;
    private ServiceImplementation service;
    private BD maBD;


    //  ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maBD =  Room.databaseBuilder(getApplicationContext(), BD.class, "BDQuestions")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        service = ServiceImplementation.getInstance(maBD);
        //creerQuestion();


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        this.initRecycler();
        this.remplirRecycler();


//ne pas oublier de remttre QuestionActivity
        binding.buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajouter = new Intent(MainActivity.this, QuestionActivity.class);
                startActivity(ajouter);
            }
        });
    }

    public void initRecycler()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        //use a linear layout manager
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //specify an adapter
        adapter = new QuestionsAdapter();
        recyclerView.setAdapter(adapter);

        binding.recyclerview.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
    }

    private void remplirRecycler(){
        adapter.list.clear();
        adapter.list.addAll(service.toutesLesQuestions());
       adapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.SupQuestions) {
            service.SupprimerQuestions();
            remplirRecycler();
            return true;
        }
        if (id == R.id.SupVotes) {
            service.SupprimerVotes();
            remplirRecycler();
            return true;
        }
        Intent Supprimer = new Intent(MainActivity.this, MainActivity.class);
        startActivity(Supprimer);
        return super.onOptionsItemSelected(item);
    }
}
