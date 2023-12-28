package com.proj.learningIT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class question_activity extends AppCompatActivity {
    List<ImageButton> answerButton = new ArrayList<>();
    List<ImageView> answerPicked = new ArrayList<>();
    List<ImageView> answerNameHolder = new ArrayList<>();
    List<TextView> answerTexts = new ArrayList<>();
    List<TextView> answerName = new ArrayList<>();
    TextView TVDiem;
    Dialog reviewDialog;
    ImageButton nextButton;
    ImageButton closeButton;
    ImageButton previousButton;
    // Timer
    TextView timerText;

    CountDownTimer countDownTimer;
    int timeLeft = 0;

    public void Awake()
    {
        closeButton = findViewById(R.id.CloseButton);
        TVDiem = findViewById(R.id.TVDiem);
        answerButton.add(findViewById(R.id.ButtonQuesLongA));
        answerButton.add(findViewById(R.id.ButtonQuesLongB));
        answerButton.add(findViewById(R.id.ButtonQuesLongC));
        answerButton.add(findViewById(R.id.ButtonQuesLongD));
        answerPicked.add(findViewById(R.id.picked_A));
        answerPicked.add(findViewById(R.id.picked_B));
        answerPicked.add(findViewById(R.id.picked_C));
        answerPicked.add(findViewById(R.id.picked_D));
        answerTexts.add(findViewById(R.id.AnsA));
        answerTexts.add(findViewById(R.id.AnsB));
        answerTexts.add(findViewById(R.id.AnsC));
        answerTexts.add(findViewById(R.id.AnsD));
        answerName.add(findViewById(R.id.txt_answer_A));
        answerName.add(findViewById(R.id.txt_answer_B));
        answerName.add(findViewById(R.id.txt_answer_C));
        answerName.add(findViewById(R.id.txt_answer_D));
        answerNameHolder.add(findViewById(R.id.answer_name_holder_A));
        answerNameHolder.add(findViewById(R.id.answer_name_holder_B));
        answerNameHolder.add(findViewById(R.id.answer_name_holder_C));
        answerNameHolder.add(findViewById(R.id.answer_name_holder_D));
        nextButton = findViewById(R.id.btnNext);
        previousButton = findViewById(R.id.btnPrevious);
        timerText = findViewById(R.id.timerText);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_questionc1);
        Awake();
        ProjectData.Reset();
        ProjectData.currentScore = 0;
        reviewDialog = InitReviewDialog();
        InitReviewDialogFunction(reviewDialog);
        ProjectData.SetShuffleQuestion();
        Question question =ProjectData.GetQuestion();
        AnswerStatus status = ProjectData.GetAnswerStatus(question);
        ShowQuestion(question,status);
        if(ProjectData.SettingConfig.timerOn)
        {

            StartTimer(ProjectData.timePerQuestion);
        }
        else
        {
            UpdateTimerText("∞");
        }

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ProjectData.IsFinish)
                {
                    Intent intent = new Intent(question_activity.this, donequiz_activity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(question_activity.this, course_activity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StopTimer();
                NextQuestionData(); // Without delay
                OnChangeQuestion();
            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StopTimer();
                ProjectData.ChangeToPreviousQuestionIndex();
                Question question =ProjectData.GetQuestion();
                AnswerStatus status = ProjectData.GetAnswerStatus(question);
                ShowQuestion(question,status);
                OnChangeQuestion();
                if(status.status == AnswerStatus.Status.Blank &&  ProjectData.SettingConfig.timerOn)
                {
                    StartTimer(ProjectData.timePerQuestion);
                }
            }
        });
        // Setup
        OnChangeQuestion();
        //submit
        Button btnSubmit=(Button)findViewById(R.id.btnSubmit);
        (btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSubmit.setVisibility(View.VISIBLE);
                Intent intent = new Intent(question_activity.this, donequiz_activity.class);
                startActivity(intent);
            }
        });
        ProjectData.SkipTime++;
    }



    public void OnChangeQuestion()
    {
        if(!ProjectData.IsPreviousQuestionAvailable())
        {
            previousButton.setVisibility(View.INVISIBLE);
        }
        else
        {
            previousButton.setVisibility(View.VISIBLE);
        }
        if(!ProjectData.IsNextQuestionAvailable())
        {
            nextButton.setVisibility(View.INVISIBLE);
        }
        else
        {
            nextButton.setVisibility(View.VISIBLE);
        }
    }
    public Dialog InitReviewDialog()
    {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.review);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        SetupReviewData(dialog);
        return dialog;
    }

    private void SetupReviewData(Dialog dialog)
    {
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(new ReviewerAdapter(getApplicationContext(),ProjectData.GetAnswerStatusList()));
    }
    public void InitReviewDialogFunction(Dialog dialog)
    {
        Handler handler = new Handler(Looper.getMainLooper());
        ImageButton closeButton = dialog.findViewById(R.id.backButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeButton.setImageResource(R.drawable.close_btn_press);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closeButton.setImageResource(R.drawable.close_btn);
                        dialog.hide();
                    }
                }, 150);
            }
        });
        ImageButton imageButton = dialog.findViewById(R.id.backButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });

    }
    public void ShowQuestion(Question question, AnswerStatus status)
    {
        TextView questionNumber = findViewById(R.id.question_number_text);
        TextView questionContent = findViewById(R.id.question_content_text);
        questionNumber.setText(""+ (ProjectData.GetQuestionIndex(question)+1) +"/" + (ProjectData.GetTotalQuestion()));
        questionContent.setText(question.Content);
        for (int i=0;i<answerTexts.stream().count();i++)
        {
            answerTexts.get(i).setText(question.Options.get(i).Content);
        }
        if(status.status == AnswerStatus.Status.Blank)
        {
            for (int i = 0 ;i<answerButton.stream().count();i++)
            {
                answerButton.get(i).setImageResource(GetDrawableId(i));
                AnswerOption option = question.Options.get(i);
                int finalI = i;
                answerButton.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AnswerStatus answerStatus = new AnswerStatus(GetLetter(finalI),option.IsCorrect, AnswerStatus.Status.Answered);
                        OnClickAnswer(question,answerStatus);
                        if(ProjectData.SettingConfig.soundOn)
                            Setting.Instance.playSound(option.IsCorrect);
                        NextQuestion();
                    }
                });
                if(question.Type == 1 )
                {
                    if(i < 2)
                    {
                        answerButton.get(i).setVisibility(View.VISIBLE);
                        answerName.get(i).setVisibility(View.VISIBLE);
                        answerNameHolder.get(i).setVisibility(View.VISIBLE);
                        answerName.get(i).setText(GetLetter(i,true));
                    }
                    else
                    {
                        answerButton.get(i).setVisibility(View.INVISIBLE);
                        answerName.get(i).setVisibility(View.INVISIBLE);
                        answerNameHolder.get(i).setVisibility(View.INVISIBLE);
                    }
                }
                else
                {
                    answerButton.get(i).setVisibility(View.VISIBLE);
                    answerName.get(i).setVisibility(View.VISIBLE);
                    answerNameHolder.get(i).setVisibility(View.VISIBLE);
                    answerName.get(i).setText(GetLetter(i));
                }

            }
            for (int i = 0 ;i<answerPicked.stream().count();i++)
            {
                answerPicked.get(i).setVisibility(View.INVISIBLE);
            }
        }
        else
        {
            if(ProjectData.IsFinish) // Review
            {
                for (int i =0 ;i<answerButton.stream().count();i++)
                {
                    AnswerOption answerOption = question.Options.get(i);
                    ImageButton button = answerButton.get(i);
                    if(answerOption.IsCorrect)
                    {
                        button.setImageResource(R.drawable.true_question);
                    }
                    else
                    {
                        button.setImageResource(R.drawable.false_question);
                    }
                }
            }
            for (int i =0 ;i<answerButton.stream().count();i++) // Visible
            {
                answerButton.get(i).setOnClickListener(null);
                if(question.Type == 1 )
                {
                    if(i < 2)
                    {
                        answerButton.get(i).setVisibility(View.VISIBLE);
                        answerName.get(i).setVisibility(View.VISIBLE);
                        answerNameHolder.get(i).setVisibility(View.VISIBLE);
                        answerName.get(i).setText(GetLetter(i,true));
                    }
                    else
                    {
                        answerButton.get(i).setVisibility(View.INVISIBLE);
                        answerName.get(i).setVisibility(View.INVISIBLE);
                        answerNameHolder.get(i).setVisibility(View.INVISIBLE);
                    }
                }
                else
                {
                    answerButton.get(i).setVisibility(View.VISIBLE);
                    answerName.get(i).setVisibility(View.VISIBLE);
                    answerNameHolder.get(i).setVisibility(View.VISIBLE);
                    answerName.get(i).setText(GetLetter(i));
                }
            }
            for (int i = 0 ;i<answerPicked.stream().count();i++) // Visible picked (yellow border)
            {
                if(GetLetter(status.chooseOption) == i)
                    answerPicked.get(i).setVisibility(View.VISIBLE);
                else
                    answerPicked.get(i).setVisibility(View.INVISIBLE);
            }
            if(ProjectData.SettingConfig.timerOn)
            {
                if(status.status == AnswerStatus.Status.TimeOut)
                    UpdateTimerText("T-out");
                else
                    UpdateTimerText("-");
            }
        }
    }

    private void NextQuestion() {
        StopTimer();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                NextQuestionData();
                OnChangeQuestion();
            }
        }, 150);
    }

    private void NextQuestionData()
    {
        if(ProjectData.ChangeToNextQuestionIndex())
        {
            Question question =ProjectData.GetQuestion();
            AnswerStatus status = ProjectData.GetAnswerStatus(question);
            ShowQuestion(question,status);
            if(!ProjectData.IsFinish && status.status == AnswerStatus.Status.Blank && ProjectData.SettingConfig.timerOn)
                StartTimer(ProjectData.timePerQuestion);
        }
        else
        {
            Intent intent = new Intent(question_activity.this, donequiz_activity.class);
            startActivity(intent);
            ProjectData.IsFinish = true;
        }
    }

    private void OnClickAnswer(Question question, AnswerStatus status)
    {
        ProjectData.SetAnswerData(question,status);
        if(status.isCorrect)
        {
            ProjectData.currentScore++;
        }
        for (int i = 0 ;i<answerPicked.stream().count();i++)
        {
            if(GetLetter(status.chooseOption) == i)
                answerPicked.get(i).setVisibility(View.VISIBLE);
            else
                answerPicked.get(i).setVisibility(View.INVISIBLE);
        }
        UpdateScore();
    }
    private int GetDrawableId(int i)
    {
        switch (i)
        {
            case 0:
                return R.drawable.a_question;
            case 1:
                return R.drawable.b_question;
            case 2:
                return R.drawable.c_question;
            case 3:
                return R.drawable.d_question;
        }
        return R.drawable.a_question;
    }
    private String GetLetter(int i)
    {
        switch (i)
        {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
        }
        return "";
    }
    private String GetLetter(int i,boolean isTrueFalseType)
    {
        switch (i)
        {
            case 0:
                return "Y";
            case 1:
                return "N";
        }
        return "";
    }
    private int GetLetter(String i)
    {
        switch (i)
        {
            case "A":
            case "Y":
                return 0;
            case "B":
            case "N":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
        }
        return -1;
    }
    private void UpdateScore() {
        //hiển thị
        TVDiem.setText("Score : " + ProjectData.currentScore);
        //update chỉ nên thực hiện đúng tên gọi của nó là update

    }

    @Override
    protected void onStart() {
        super.onStart();
        Question question =ProjectData.GetQuestion();
        AnswerStatus status = ProjectData.GetAnswerStatus(question);
        ShowQuestion(question,status);
    }

    private void StartTimer(int time)
    {
        countDownTimer = new CountDownTimer((time+1)*1000,1000) {
            @Override
            public void onTick(long l) {
                int timeInt = (int)Math.ceil(l/1000);
                if(timeLeft != timeInt)
                {
                    timeLeft = timeInt;
                    UpdateTimerText();
                }
            }

            @Override
            public void onFinish() {
                Question question = ProjectData.GetQuestion();
                AnswerStatus status = new AnswerStatus("",false, AnswerStatus.Status.TimeOut);
                ProjectData.SetAnswerData(question,status);
                NextQuestion();
            }
        }.start();
    }
    private void StopTimer()
    {
        if(ProjectData.SettingConfig.timerOn)
            countDownTimer.cancel();
    }

    private void UpdateTimerText()
    {
        timerText.setText(""+timeLeft+"s");
    }
    private void UpdateTimerText(String s)
    {
        timerText.setText(""+s);
    }

}