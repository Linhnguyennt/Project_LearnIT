package com.proj.learningIT;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class LoadingQuestion_Long extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_questionc1);
        init();
       //loadScore();
    }

//    private void loadHighScore() {
//        SharedPreferences preferences = getSharedPreferences("share", MODE_PRIVATE);
//        highscore = preferences.getInt("highscore", 0);
//        TVDiem.setText("Điểm : "+score);
//    }

    TextView AnsA, AnsB, AnsC, AnsD;
    ImageButton ButtonQuesLongA,ButtonQuesLongB,ButtonQuesLongC,ButtonQuesLongD;
    TextView question_content_text;
    Button btnNext;
    String selectedAnswer, rightAnswer;

    public int score_now;
    int questionIndex = 0;
    public void FetchData()
    {
        String data = Utils.getJsonFromAssets(getApplicationContext(),"course_1.json");
        ProjectData.FetchDataFromJson(data,getApplicationContext());
    }
    public void init(){

        Question  question = ProjectData.GetQuestion();
        List<AnswerOption> options = question.Options;
        question_content_text.setText(question.Content);

        for (int i = 0; i< options.stream().count();i++)
        {

                AnswerOption option = options.get(i);
                AnsA.setText((CharSequence) option);

            // Khúc này xữ lý nuế là i= 0 thì A, i= 1 thì là B,....
            // Còn khi mà chon được câu hỏi đúng hay sang câu hỏi tiếp theo thì chỉ cần refresh lại trang này
            /*
             * Refresh bằng cách là Linh gọi ProjectData.NextQuestion();
             * xong rồi Linh tiếp tục setText tiếp
             * Cứ vậy cho hết câu hỏi thôi
             * */
        }

        ButtonQuesLongA.setOnClickListener(this);
        ButtonQuesLongB.setOnClickListener(this);
        ButtonQuesLongC.setOnClickListener(this);
        ButtonQuesLongD.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        ProjectData.ChangeToNextQuestionIndex();
    }
    public void onClick(View view) {
//        Button clickedButton = (Button) view;
//
//        if (clickedButton.getId() == R.id.btnNext){
//            if (selectedAnswer == rightAnswer){
//                score++;
//                score_now=score;
//            }
//            ProjectData.ChangeToNextQuestionIndex();
////            if (questionIndex >= question.size()){
////
////            } else {
////                ProjectData.GetQuestion();
////            }
//        } else {
//
//            selectedAnswer = clickedButton.getText().toString().trim();
//        }
//    }
//    public void loadScore(){
//        score_now=score;
//
   }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}