package com.proj.learningIT;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;

public class donequiz_activity extends AppCompatActivity {
    ImageButton HomeButton;
    ImageButton Restart;
    ImageButton Review;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_donequiz);
        HomeButton = (ImageButton) findViewById(R.id.BntHome);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(donequiz_activity.this, course_activity.class);
                startActivity(intent);
            }
        });
        Restart = (ImageButton) findViewById(R.id.ButtonRestart);
        Restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(donequiz_activity.this, question_activity.class);
                startActivity(intent);
                finish();
            }
        });
        Review = findViewById(R.id.ButtonReview);
        Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(donequiz_activity.this, question_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        TextView scoreText = findViewById(R.id.txt_diem);
        TextView falseText = findViewById(R.id.txt_socausai);
        TextView trueText = findViewById(R.id.txt_caudung);
        TextView timeoutText = findViewById(R.id.txt_socautimeout);
        TextView blankText = findViewById(R.id.txt_socaukhongtraloi);
        scoreText.setText(""+ProjectData.currentScore);
        List<AnswerStatus> answerStatusList = ProjectData.GetAnswerStatusList();

        int falseQuestion = (int)answerStatusList.stream().filter(data ->!data.isCorrect && data.status == AnswerStatus.Status.Answered).count();
        int trueQuestion = (int)answerStatusList.stream().filter(data ->data.isCorrect && data.status == AnswerStatus.Status.Answered).count();
        int timeoutQuestion = (int)answerStatusList.stream().filter(data ->data.status == AnswerStatus.Status.TimeOut).count();
        int blankQuestion = (int)answerStatusList.stream().filter(data ->data.status == AnswerStatus.Status.Blank).count();
        falseText.setText(""+falseQuestion);
        trueText.setText(""+trueQuestion);
        timeoutText.setText(""+timeoutQuestion);
        blankText.setText(""+blankQuestion);

    }
}