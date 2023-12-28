package com.proj.learningIT;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity {

    public static Setting Instance;
    MediaPlayer quizSong;
    MediaPlayer trueSound;
    MediaPlayer falseSound;
    Switch musicSwitch ;
    Switch soundSwitch;
    Switch timerSwitch;
    EditText quantityText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Instance = this;
        if(!ProjectData.SettingConfig.isSoundReferencesInit)
        {
            quizSong = MediaPlayer.create(Setting.this, R.raw.quizz);
            trueSound = MediaPlayer.create(Setting.this, R.raw.correct);
            falseSound = MediaPlayer.create(Setting.this, R.raw.wrong);
            quizSong.setLooping(true);
            ProjectData.SettingConfig.quizSong = quizSong;
            ProjectData.SettingConfig.trueSound = trueSound;
            ProjectData.SettingConfig.falseSound = falseSound;
            ProjectData.SettingConfig.isSoundReferencesInit = true;
        }
        else
        {
            quizSong =  ProjectData.SettingConfig.quizSong;
            trueSound = ProjectData.SettingConfig.trueSound;
            falseSound =  ProjectData.SettingConfig.falseSound;

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);
        ImageButton BtnBack = findViewById(R.id.ButtonBack);
        quantityText = findViewById(R.id.questionQuantityText);
         musicSwitch = findViewById(R.id.musicSwitch);
         soundSwitch = findViewById(R.id.soundSwitch);
         timerSwitch = findViewById(R.id.timerSwitch);

        musicSwitch.setChecked(ProjectData.SettingConfig.musicOn);
        timerSwitch.setChecked(ProjectData.SettingConfig.timerOn);
        soundSwitch.setChecked(ProjectData.SettingConfig.soundOn);
        quantityText.setText(""+ProjectData.SettingConfig.questionQuantity);
        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this, home_activity.class);
                startActivity(intent);
                Save();
            }
        });

        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    playMusic();
                }
                else
                {
                    pauseMusic();
                }
            }
        });


    }
    public void playMusic(){
        quizSong.start();
    }
    public void pauseMusic(){ quizSong.pause();
    }

    public void playSound(boolean correct)
    {
        if(correct)
            trueSound.start();
        else
            falseSound.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Save();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Save();
    }

    private void Save()
    {
        ProjectData.SettingConfig.musicOn = musicSwitch.isChecked();
        ProjectData.SettingConfig.soundOn = soundSwitch.isChecked();
        ProjectData.SettingConfig.timerOn = timerSwitch.isChecked();
        int minQuestion = 0;
        for (QuestionData data: ProjectData.QuestionDataList) {
            if(minQuestion == 0)
            {
                minQuestion = data.TotalAvailableQuestion();
                continue;
            }
            if(minQuestion > data.TotalAvailableQuestion())
            {
                minQuestion = data.TotalAvailableQuestion();
            }
        }
        int input = Integer.parseInt(quantityText.getText().toString());
        if(input > minQuestion)
        {
            input = minQuestion;
            Toast.makeText(getApplicationContext(), "Your input question number is reached limit! Automated set to "+ minQuestion, Toast.LENGTH_LONG).show();
        }
        ProjectData.SettingConfig.questionQuantity = input ;
    }
}
