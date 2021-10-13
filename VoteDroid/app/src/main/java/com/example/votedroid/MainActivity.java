package com.example.votedroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.votedroid.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    /*    listView=(ListView)findViewById(R.id.listquestion);
        ArrayList<String> Questions = new ArrayList<>();
        Questions.add("joyce");
        Questions.add("Fait il beau aujourdhui");

        ArrayAdapter QuestionAdapter = new ArrayAdapter(this,android.res.layout.simple_List_item_1,Questions); */

        binding.buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ajouter = new Intent(MainActivity.this, QuestionActivity.class);
                startActivity(ajouter);
            }
        });
    }
}
