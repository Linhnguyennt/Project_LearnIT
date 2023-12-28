package com.proj.learningIT;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AnswerReviewer extends RecyclerView.ViewHolder {

    ImageView frame;
    TextView answerIndexText,answerOptionText;
    public AnswerReviewer(@NonNull View itemView) {
        super(itemView);
        frame = itemView.findViewById(R.id.frame);
        answerIndexText = itemView.findViewById(R.id.answer_index_text);
        answerOptionText = itemView.findViewById(R.id.answer_option_text);
    }
}
