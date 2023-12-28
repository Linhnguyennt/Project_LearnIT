package com.proj.learningIT;

public class AnswerStatus {
    public String chooseOption;
    public boolean isCorrect;
    public Status status;

    public AnswerStatus(String chooseOption, boolean isCorrect, Status status) {
        this.chooseOption = chooseOption;
        this.isCorrect = isCorrect;
        this.status = status;
    }
    public enum Status
    {
        TimeOut,
        Blank,
        Answered,
        Uncountable
    }
    public void Reset()
    {
        chooseOption = "";
        isCorrect = false;
        status = Status.Uncountable;
    }
    public void SetBlankData()
    {
        chooseOption = "";
        isCorrect = false;
        status = Status.Blank;
    }
}

