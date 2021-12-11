package com.example.votedroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votedroid.databinding.ActivityQuestionBinding;
import com.example.votedroid.modele.VDQuestion;
import com.example.votedroid.modele.VDVote;

import java.util.ArrayList;
import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder>  {

    public List<VDQuestion> list;
    public List<VDVote> listv;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewQuestion;
        public View Image;
        public LinearLayout linearLayout;

        public ViewHolder(LinearLayout view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textViewQuestion = view.findViewById(R.id.tvQuestion);
            Image = view.findViewById(R.id.imgbutton);
            linearLayout = view;
        }
    }

    public QuestionsAdapter() {
        list = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public QuestionsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)  {
        // Create a new view, which defines the UI of the list item
         LinearLayout v = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.question_item, viewGroup, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        VDQuestion questionactuel = list.get(position);
        viewHolder.textViewQuestion.setText(questionactuel.texteQuestion);

        // Vote
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent voter = new Intent(view.getContext(),VoteActivity.class);
                voter.putExtra("idposition",questionactuel.idQuestion);
                voter.putExtra("texte",questionactuel.texteQuestion);
                view.getContext().startActivity(voter);
            }
        });

        // Image
        viewHolder.Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent results = new Intent(view.getContext(),ResultsActivity.class);
                results.putExtra("idposition",questionactuel.idQuestion);
                view.getContext().startActivity(results);
            }
        });

    }


    // Return the size of your list (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
