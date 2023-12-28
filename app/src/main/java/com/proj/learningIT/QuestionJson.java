package com.proj.learningIT;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionJson {
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("description")
    public String description;
    @SerializedName("list")
    public List<Question> list;

    public QuestionJson(String id,List<Question> list)
    {
        this.id = id;
        this.list = list;
    }

}
