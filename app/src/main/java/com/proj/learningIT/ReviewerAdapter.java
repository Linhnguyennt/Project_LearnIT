package com.proj.learningIT;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewerAdapter extends RecyclerView.Adapter<AnswerReviewer> {

    public ReviewerAdapter(Context context, List<AnswerStatus> answerStatuses) {
        this.context = context;
        this.answerStatuses = answerStatuses;
    }

    Context context;
    List<AnswerStatus> answerStatuses;
    @NonNull
    @Override
    public AnswerReviewer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnswerReviewer(LayoutInflater.from(context).inflate(R.layout.answer_status_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerReviewer holder, int position) {

        AnswerStatus answerStatus = answerStatuses.get(position);
        holder.answerIndexText.setText(""+(position+1));
        if(answerStatus.status == AnswerStatus.Status.Blank)
        {
            holder.frame.setImageResource(R.drawable.answer_unchoose);
            holder.answerOptionText.setText("?");
        }
        else
        {
            if(answerStatus.isCorrect)
            {
                holder.frame.setImageResource(R.drawable.answer_true);
            }
            else
            {
                holder.frame.setImageResource(R.drawable.answer_false);
            }
            holder.answerOptionText.setText(answerStatus.chooseOption);
        }


    }

    @Override
    public int getItemCount() {
        return answerStatuses.size();
    }
}
