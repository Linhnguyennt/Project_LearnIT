package com.proj.learningIT;

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

public class create_yes_no extends AppCompatActivity {
    EditText TextQuesYN;
    CheckBox CheckBoxTrue ;
    CheckBox CheckBoxFalse ;
    Spinner courseIdSpinner;
    List<CheckBox> checkBoxes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_yes_no);
        ImageButton BtnBack = findViewById(R.id.BackButton);
        ImageButton createButton = findViewById(R.id.BntCreateQuesYN);
        TextQuesYN = findViewById(R.id.editTextYN);
        CheckBoxTrue = findViewById(R.id.checkBoxTrue);
        CheckBoxFalse = findViewById(R.id.checkBoxFalse);
        courseIdSpinner = findViewById(R.id.spinner2);
        checkBoxes.add(CheckBoxTrue);
        checkBoxes.add(CheckBoxFalse);
        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(create_yes_no.this, Choose_question_types.class);
                startActivity(intent);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckCreateQuestionData() == create_Multiple_choice.InputType.ReadyToSubmit)
                {
                    CreateQuestion();
                    LoadingDialog loadingDialog = new LoadingDialog(create_yes_no.this);
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
        question.Type = 1;
        question.Content = TextQuesYN.getText().toString();
        List<AnswerOption> list = new ArrayList<>();
        list.add(new AnswerOption(CheckBoxTrue.isChecked(),"True"));
        list.add(new AnswerOption(CheckBoxFalse.isChecked(),"False"));
        list.add(new AnswerOption(false,""));
        list.add(new AnswerOption(false,""));
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
    private create_Multiple_choice.InputType CheckCreateQuestionData()
    {
        if(TextQuesYN.getText().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Your question content is empty!", Toast.LENGTH_LONG).show();
            return create_Multiple_choice.InputType.EmptyContent;
        }
        int totalTrueAnswer = 0;
        for (CheckBox box : checkBoxes) {
            if(box.isChecked()) totalTrueAnswer++;
        }
        if(totalTrueAnswer > 1){
            Toast.makeText(getApplicationContext(), "Your have more than one right answer!", Toast.LENGTH_LONG).show();
            return create_Multiple_choice.InputType.MultipleCheckBox;
        }
        if(totalTrueAnswer == 0)
        {
            Toast.makeText(getApplicationContext(), "You don't have right answer!", Toast.LENGTH_LONG).show();
            return create_Multiple_choice.InputType.EmptyCheckBox;
        }
        return create_Multiple_choice.InputType.ReadyToSubmit;
    }

    private void Clear()
    {
        TextQuesYN.setText("");
        CheckBoxTrue.setChecked(false);
        CheckBoxFalse.setChecked(false);

    }


}
