package com.proj.learningIT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionData {
    public String id;

    public String name;
    public String description;
    private List<Question> _questionListRaw;

    // For shuffle without same question each times play
    private List<Question> _questionListShuffle; // Shuffle raw list
    private List<Question> _questionListLimitShuffled; // Limit question with shuffle list
    private List<Question> _questionListLimitRaw; // Limit question with raw list
    private List<AnswerStatus> answerStatusList;

    private boolean isOutOfQuestion = false;

    public QuestionData(String id,String name,String description, List<Question> questionList, List<AnswerStatus> answerStatusList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.answerStatusList = answerStatusList;
        _questionListRaw = questionList;
        _questionListShuffle =new ArrayList<>(_questionListRaw);
        Collections.shuffle(_questionListShuffle);
    }
    public  List<Question> GetQuestionList(){
        List<Question> list = new ArrayList<>();
        if(!ProjectData.IsShuffle())
        {

            list = _questionListLimitRaw;
        }

        else
        {
            list = _questionListLimitShuffled;
        }
        return list;

    }
    public void Shuffle()
    {
        Collections.shuffle(_questionListLimitShuffled);
    }
    public List<AnswerStatus> GetAnswerStatusList()
    {
        return answerStatusList;
    }

    public void InitCourseQuestion()
    {
        InitList();
        if(_questionListLimitShuffled.stream().count() < ProjectData.SettingConfig.questionQuantity)
        {
            int count = (int)_questionListLimitShuffled.stream().count();
            for(int i = 0; i < ProjectData.SettingConfig.questionQuantity- count ;i++)
            {
                _questionListLimitShuffled.add(_questionListShuffle.get(i));
            }
            ProjectData.SkipTime = 0;
            isOutOfQuestion = true;
        }
        if(_questionListLimitRaw.stream().count() < ProjectData.SettingConfig.questionQuantity)
        {
            int count = (int)_questionListLimitRaw.stream().count();
            for(int i = 0; i < ProjectData.SettingConfig.questionQuantity- count ; i++)
            {
                _questionListLimitRaw.add(_questionListRaw.get(i));
            }
            ProjectData.SkipTime = 0;
            isOutOfQuestion = true;
        }
        for (int i =0; i< GetAnswerStatusList().stream().count();i++)
        {
            AnswerStatus status =  GetAnswerStatusList().get(i);
            if(i <  ProjectData.SettingConfig.questionQuantity)
            {
                status.SetBlankData();
            }
            else
            {
                status.Reset();
            }

        }
    }

    private void InitList()
    {
        _questionListLimitRaw = _questionListRaw.stream().skip(ProjectData.SkipTime* ProjectData.SettingConfig.questionQuantity).limit(ProjectData.SettingConfig.questionQuantity).collect(Collectors.toList());
        if(isOutOfQuestion)
        {
            _questionListShuffle =new ArrayList<>(_questionListRaw);
            Collections.shuffle(_questionListShuffle);
        }
        _questionListLimitShuffled = _questionListShuffle.stream().skip(ProjectData.SkipTime* ProjectData.SettingConfig.questionQuantity).limit(ProjectData.SettingConfig.questionQuantity).collect(Collectors.toList());
    }
     public int TotalAvailableQuestion()
     {
         return (int)_questionListRaw.stream().count();
     }


}
