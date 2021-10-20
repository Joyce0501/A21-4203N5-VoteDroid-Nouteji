package com.example.votedroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votedroid.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    QuestionAdapter adapter;

  //  ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

     /*   listView = findViewById(R.id.listquestion);
        String[] values = new String[]{"Quelle est la définition d’un système informatique?",
                "Quels sont les composants de base d’un système informatique?",
        "Qu’est-ce qu’un microprocesseur?",
        "Nommez quelques-uns des derniers processeurs informatiques?",
        "Quelle est la différence entre un processeur 32 bits et un processeur 64 bits?"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position >= 0 ){
                    Intent vote = new Intent(MainActivity.this,VoteActivity.class);
                    startActivity(vote);
                }
            }
        }); */
        this.initRecycler();
        this.remplirRecycler();



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
        adapter = new QuestionAdapter();
        recyclerView.setAdapter(adapter);
    }
    private void remplirRecycler(){
       for(int i = 0; i <= 20; i++)
       {
           Questions q = new Questions();
           q.questions = "Question" + i;
           adapter.list.add(q);
       }

       adapter.notifyDataSetChanged();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

}
