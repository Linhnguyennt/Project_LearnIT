package com.proj.learningIT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class course_introduction_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_courses_introduction);
        TextView courseNameText = findViewById(R.id.course_name);
        courseNameText.setText(ProjectData.GetCurrentSubject().name);
        TextView courseDescriptionText = findViewById(R.id.course_desciption);
        courseDescriptionText.setText(ProjectData.GetCurrentSubject().description);
        ImageButton startButton = findViewById(R.id.start_course_btn);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(course_introduction_activity.this, question_activity.class);
                startActivity(intent);
            }
        });
        ImageButton backButton = findViewById(R.id.imageButton7);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(course_introduction_activity.this, course_activity.class);
                startActivity(intent);
            }
        });

    }
}