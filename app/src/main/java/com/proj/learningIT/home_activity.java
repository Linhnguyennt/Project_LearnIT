package com.proj.learningIT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class home_activity extends AppCompatActivity {
    ImageButton ButtonCourse;
    ImageButton ButtonCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);


        ButtonCourse = (ImageButton) findViewById(R.id.ButtonCourse);
        ButtonCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_activity.this, course_activity.class);
                startActivity(intent);
            }
        });


        ButtonCreate = (ImageButton) findViewById(R.id.ButtonCreate);
        ButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_activity.this, Choose_question_types.class);
                startActivity(intent);
            }
        });

        ImageButton ButtonSetting = findViewById(R.id.ButtonSetting);
        ButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(home_activity.this, Setting.class);
                    startActivity(intent);
            }
        });
    }

    }
