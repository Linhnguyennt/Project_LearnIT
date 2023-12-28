package com.proj.learningIT;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;
import com.proj.learningIT.AnswerStatus.Status;

public class ProjectData {
    // Setting
    public static int timePerQuestion = 5;
    public static SettingConfiguration SettingConfig;
    private static Question CurrentQuestion;
    // Shuffle
    private static boolean IsShuffle;
    public static int SkipTime = 0;
    public static boolean IsFinish;
    public static int currentScore;
    // Data
    public static List<QuestionData> QuestionDataList = new ArrayList<>();
    private static String currentSubjectId;

    // Lấy câu hỏi hiện tại
    public static void SetSubject(String subjectId)
    {
        currentSubjectId = subjectId;
    }
    public static QuestionData GetCurrentSubject()
    {
        return GetQuestionData(currentSubjectId);
    }
    public static void Reset()
    {
        CurrentQuestion = null;
        QuestionData questionData = GetQuestionData(currentSubjectId);
        questionData.InitCourseQuestion();
        IsFinish = false;
    }

    public static void Init()
    {
        SettingConfig = new SettingConfiguration(false,false,5,false);
    }
    public static boolean IsShuffle()
    {
        return IsShuffle;
    }
    public static Question GetQuestion()
    {
        if(CurrentQuestion == null)
        {
            CurrentQuestion = GetQuestionData(currentSubjectId).GetQuestionList().get(0);
        }
        return CurrentQuestion;
    }

    public static int GetQuestionIndex(Question question)
    {
        return GetQuestionData(currentSubjectId).GetQuestionList().indexOf(question);
    }
    public static long GetTotalQuestion()
    {
        return GetQuestionData(currentSubjectId).GetQuestionList().stream().count();
    }
    // Lấy câu hỏi tiếp theo dữa trên câu hỏi hiện tại
    public static Question NextQuestion()
    {
        int currentIndex = GetQuestionData(currentSubjectId).GetQuestionList().indexOf(CurrentQuestion);
        if(currentIndex == GetQuestionData(currentSubjectId).GetQuestionList().stream().count() -1)
            return null;
        return CurrentQuestion;
    }
    public static boolean ChangeToNextQuestionIndex()
    {
        int currentIndex = GetQuestionData(currentSubjectId).GetQuestionList().indexOf(CurrentQuestion);
        if(currentIndex == GetQuestionData(currentSubjectId).GetQuestionList().stream().count() -1)
            return false;
        CurrentQuestion = GetQuestionData(currentSubjectId).GetQuestionList().get(currentIndex + 1);
        return true;
    }
    public static boolean IsNextQuestionAvailable()
    {
        int currentIndex = GetQuestionData(currentSubjectId).GetQuestionList().indexOf(CurrentQuestion);
        if(currentIndex == GetQuestionData(currentSubjectId).GetQuestionList().stream().count() -1)
            return false;
        return true;
    }
    public static boolean ChangeToPreviousQuestionIndex()
    {
        int currentIndex = GetQuestionData(currentSubjectId).GetQuestionList().indexOf(CurrentQuestion);
        if(currentIndex == 0)
            return false;
        CurrentQuestion = GetQuestionData(currentSubjectId).GetQuestionList().get(currentIndex - 1);
        return true;
    }
    public static boolean IsPreviousQuestionAvailable()
    {
        int currentIndex = GetQuestionData(currentSubjectId).GetQuestionList().indexOf(CurrentQuestion);
        if(currentIndex == 0)
            return false;
        return true;
    }
    public static void SetShuffleQuestion()
    {
        IsShuffle = true;
        GetQuestionData(currentSubjectId).Shuffle();
    }



    public static void FetchDataFromJson(String data,Context context)
    {
        Gson gson = new Gson();
        // Get Base Question
        QuestionJson BaseQuestionJson = gson.fromJson(data,QuestionJson.class);
        // Get Added Question
        AddQuestionData addQuestionData = ReadFromFile(context);
        QuestionJson AddQuestionJson = addQuestionData.questionDataList.stream()
                .filter(c -> c.id.equals(BaseQuestionJson.id))
                .findAny()
                .orElse(null);
        // Sum all question for app data
        List<Question> questionList = new ArrayList<>();
        questionList.addAll(BaseQuestionJson.list);
        if(AddQuestionJson != null)
            questionList.addAll(AddQuestionJson.list);
        List<AnswerStatus > answerStatus = new ArrayList<>();

        for(int i =0 ; i< questionList.size();i++)
        {
            answerStatus.add(new AnswerStatus("",false, Status.Blank));
        }

        QuestionDataList.add(new QuestionData(BaseQuestionJson.id,BaseQuestionJson.name, BaseQuestionJson.description,questionList, answerStatus));
    }

    public static void SetAnswerData(Question question, AnswerStatus status)
    {
        QuestionData data =GetQuestionData(currentSubjectId);
        int index = data.GetQuestionList().indexOf(question);
        data.GetAnswerStatusList().set(index,status);
    }

    public static List<AnswerStatus> GetAnswerStatusList()
    {
        return GetQuestionData(currentSubjectId).GetAnswerStatusList();
    }
    public static AnswerStatus GetAnswerStatus(Question question)
    {
        int index = GetQuestionData(currentSubjectId).GetQuestionList().indexOf(question);
        List<AnswerStatus> statuses = GetQuestionData(currentSubjectId).GetAnswerStatusList();
        return statuses.get(index);
    }

    private static QuestionData GetQuestionData(String id)
    {
        //QuestionDataList=ProjectData.SetShuffleQuestion();
        return QuestionDataList.stream()
                .filter(data ->data.id.equals(id))
                .findAny()
                .orElse(null);
    }
    public static void AddQuestionToFile(Question question, String courseId, Context context) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            AddQuestionData data = ReadFromFile(context);
            QuestionJson course = data.questionDataList.stream()
                    .filter(c -> c.id.equals(courseId))
                    .findAny()
                    .orElse(null);
            course.list.add(question);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("add_question.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(gson.toJson(data));
            outputStreamWriter.close();
            String path = context.getFileStreamPath("add_question.json").getAbsolutePath();
            Log.e("path","path: " + path);
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    private static String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("add_question.json");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
           return WriteNewFile(context);
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }
    private static void writeToFileNew(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("add_question.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            String path = context.getFileStreamPath("add_question.json").getAbsolutePath();
            Log.e("path","path: " + path);
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private static String WriteNewFile(Context context)
    {
        writeToFileNew(new Gson().toJson(
                new AddQuestionData(Arrays.asList(
                        new QuestionJson("KTPM", new ArrayList<>(0)),
                        new QuestionJson("LTC", new ArrayList<>(0)),
                        new QuestionJson("OOP", new ArrayList<>(0)))) ),context);
        return readFromFile(context);
    }

    private static AddQuestionData ReadFromFile(Context context)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        AddQuestionData data = gson.fromJson(readFromFile(context),AddQuestionData.class);
        if(data == null)
        {
            data = gson.fromJson(WriteNewFile(context),AddQuestionData.class);
        }
        return data;
    }
}
