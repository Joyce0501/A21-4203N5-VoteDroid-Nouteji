package com.example.votedroid;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    public List<Questions> list;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewQuestion;
        public ImageButton Image;
        public ViewHolder(LinearLayout view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textViewQuestion = view.findViewById(R.id.tvQuestion);
            Image = view.findViewById(R.id.imgbutton);
        }

     /*   public TextView getTextView() {
            return textView;
        }  */
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
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Questions questionactuel = list.get(position);
        viewHolder.textViewQuestion.setText(questionactuel.questions);
   
    }

    // Return the size of your list (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
