package com.proj.learningIT;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddQuestionData {

    @SerializedName("list")
    public List<QuestionJson> questionDataList;
    public AddQuestionData(List<QuestionJson> questionDataList)
    {
        this.questionDataList = questionDataList;
    }
}
