package com.proj.learningIT;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {
    @SerializedName("Id")
    public int Id;
    @SerializedName("Content")
    public String Content;
    @SerializedName("Type")
    public int Type;
    @SerializedName("Options")
    public List<AnswerOption> Options;
}

