package com.proj.learningIT;

import static com.proj.learningIT.R.id.checkBoxAns1;
import static com.proj.learningIT.R.id.spinner;
import static com.proj.learningIT.R.id.textInputMCQ;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class create_Multiple_choice extends AppCompatActivity {
    EditText TextQuesMCQ;
    EditText Ans1QuesMCQ ;
    EditText Ans2QuesMCQ;
    EditText Ans3QuesMCQ ;
    EditText Ans4QuesMCQ;
    CheckBox CheckBoxAns1 ;
    CheckBox CheckBoxAns2 ;
    CheckBox CheckBoxAns3;
    CheckBox CheckBoxAns4;
    Spinner courseIdSpinner;

    List<EditText> texts = new ArrayList<>();
    List<CheckBox> checkBoxes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_muptiple_choice);
         TextQuesMCQ = findViewById(R.id.textInputMCQ);
         Ans1QuesMCQ = findViewById(R.id.Ans1QuesMCQ);
         Ans2QuesMCQ = findViewById(R.id.Ans2QuesMCQ);
         Ans3QuesMCQ = findViewById(R.id.Ans3QuesMCQ);
         Ans4QuesMCQ = findViewById(R.id.Ans4QuesMCQ);
         CheckBoxAns1 = findViewById(R.id.checkBoxAns1);
         CheckBoxAns2 = findViewById(R.id.checkBoxAns2);
         CheckBoxAns3 = findViewById(R.id.checkBoxAns3);
         CheckBoxAns4 = findViewById(R.id.checkBoxAns4);
        courseIdSpinner = findViewById(R.id.spinner);
        ImageButton BtnBack = findViewById(R.id.BackButon);
        ImageButton createButton = findViewById(R.id.BntCreateQuestionMQC);
        texts.add(Ans1QuesMCQ);
        texts.add(Ans2QuesMCQ);
        texts.add(Ans3QuesMCQ);
        texts.add(Ans4QuesMCQ);
        checkBoxes.add(CheckBoxAns1);
        checkBoxes.add(CheckBoxAns2);
        checkBoxes.add(CheckBoxAns3);
        checkBoxes.add(CheckBoxAns4);
        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(create_Multiple_choice.this, Choose_question_types.class);
                startActivity(intent);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckCreateQuestionData() == InputType.ReadyToSubmit)
                {
                    CreateQuestion();
                     LoadingDialog loadingDialog = new LoadingDialog(create_Multiple_choice.this);
                     loadingDialog.startLoadingAlertDialog();
                    Handler handle = new Handler();
                    handle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Clear();
                            loadingDialog.dismissDialog();
                            Toast.makeText(getApplicationContext(), "Question created!", Toast.LENGTH_LONG).show();
                            MainActivity.Instance.FetchData();
                        }
                    },5000);
                }
            }
        });

    }

    private void CreateQuestion()
    {
        Question question = new Question();
        question.Type = 0;
        question.Content = TextQuesMCQ.getText().toString();
        List<AnswerOption> list = new ArrayList<>();
        list.add(new AnswerOption(CheckBoxAns1.isChecked(),Ans1QuesMCQ.getText().toString()));
        list.add(new AnswerOption(CheckBoxAns2.isChecked(),Ans2QuesMCQ.getText().toString()));
        list.add(new AnswerOption(CheckBoxAns3.isChecked(),Ans3QuesMCQ.getText().toString()));
        list.add(new AnswerOption(CheckBoxAns4.isChecked(),Ans4QuesMCQ.getText().toString()));
        question.Options = list;
        String courseId = GetCourseID((String) courseIdSpinner.getSelectedItem());
        ProjectData.AddQuestionToFile(question,courseId, getApplicationContext());
    }
    private String GetCourseID(String spinnerId)
    {
        switch (spinnerId)
        {
            case "Software Testing":
                return "KTPM";
            case "OOP":
                return "OOP";
            case "C Language":
                return "LTC";
        }
        return spinnerId;
    }
    private InputType CheckCreateQuestionData()
    {
        if(TextQuesMCQ.getText().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Your question content is empty!", Toast.LENGTH_LONG).show();
            return InputType.EmptyContent;
        }
        for (EditText text : texts) {

            if(text.getText().equals(""))
            {
                Toast.makeText(getApplicationContext(), "Your answer content is empty!", Toast.LENGTH_LONG).show();
                return InputType.EmptyAnswerContent;
            }

        }
        int totalTrueAnswer = 0;
        for (CheckBox box : checkBoxes) {
            if(box.isChecked()) totalTrueAnswer++;
        }
        if(totalTrueAnswer > 1){
            Toast.makeText(getApplicationContext(), "Your have more than one right answer!", Toast.LENGTH_LONG).show();
            return InputType.MultipleCheckBox;
        }
        if(totalTrueAnswer == 0)
        {
            Toast.makeText(getApplicationContext(), "You don't have right answer!", Toast.LENGTH_LONG).show();
            return InputType.EmptyCheckBox;
        }
        return InputType.ReadyToSubmit;
    }

    private void Clear()
    {
        TextQuesMCQ.setText("");
        CheckBoxAns1.setChecked(false);
        Ans1QuesMCQ.setText("");
        CheckBoxAns2.setChecked(false);
        Ans2QuesMCQ.setText("");
        CheckBoxAns3.setChecked(false);
        Ans3QuesMCQ.setText("");
        CheckBoxAns4.setChecked(false);
        Ans4QuesMCQ.setText("");
    }


    public enum InputType
    {
        EmptyAnswerContent,
        EmptyCheckBox,
        MultipleCheckBox,
        EmptyContent,
        ReadyToSubmit
    }
}
