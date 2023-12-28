package com.proj.learningIT;

import com.google.gson.annotations.SerializedName;

public class AnswerOption {
    @SerializedName("IsCorrect")
    public boolean IsCorrect;
    @SerializedName("Content")
    public String Content;

    public AnswerOption(boolean isCorrect, String content) {
        IsCorrect = isCorrect;
        Content = content;
    }
}
