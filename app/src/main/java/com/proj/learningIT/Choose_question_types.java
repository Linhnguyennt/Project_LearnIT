package com.proj.learningIT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class Choose_question_types extends AppCompatActivity {
    ImageButton ButtonYesNo;
    ImageButton ButtonMCQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_type);


        ButtonYesNo = (ImageButton) findViewById(R.id.btn_yesno);
        ButtonYesNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Choose_question_types.this, create_yes_no.class);
                startActivity(intent);
            }
        });


        ButtonMCQ = (ImageButton) findViewById(R.id.btn_mcq);
        ButtonMCQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Choose_question_types.this, create_Multiple_choice.class);
                startActivity(intent);
            }
        });

        ImageButton BtnBack = findViewById(R.id.Bnt_choose_back);
        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Choose_question_types.this, home_activity.class);
                startActivity(intent);
            }
        });
    }
}
