package com.proj.learningIT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class course_activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_course);
        ImageButton BackButton =  findViewById(R.id.BackButton);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(course_activity.this, home_activity.class);
                startActivity(intent);
            }
        });
        ImageButton ButtonTesting =  findViewById(R.id.btn_course_test);
        ButtonTesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProjectData.SetSubject("KTPM");
                Intent intent = new Intent(course_activity.this, course_introduction_activity.class);
                startActivity(intent);
            }
        });
        ImageButton ButtonC =  findViewById(R.id.btn_course_C);
        ButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProjectData.SetSubject("LTC");
                Intent intent = new Intent(course_activity.this, course_introduction_activity.class);
                startActivity(intent);
            }
        });
        ImageButton ButtonOOP =  findViewById(R.id.btn_course_OOP);
        ButtonOOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProjectData.SetSubject("OOP");
                Intent intent = new Intent(course_activity.this, course_introduction_activity.class);
                startActivity(intent);
            }
        });
    }
}