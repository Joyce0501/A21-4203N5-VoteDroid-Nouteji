package com.example.votedroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    public List<Questions> list;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ViewHolder(TextView view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = view;
        }

     /*   public TextView getTextView() {
            return textView;
        }  */
    }

    public QuestionAdapter() {
        list = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        TextView v = (TextView) LayoutInflater.from(viewGroup.getContext())
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
        viewHolder.textView.setText(questionactuel.questions);
    }

    // Return the size of your list (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
