package com.proj.learningIT;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Dialog signInDialog;
    public static MainActivity Instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Instance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sign_in);
        //Tạo dialog sign_in
        signInDialog = InitSignInDialog();
        InitSignInLayoutFunction(signInDialog);
        ProjectData.Init();
        FetchData();
    }
    public void FetchData()
    {
        ProjectData.QuestionDataList.clear();
        ProjectData.FetchDataFromJson(Utils.getJsonFromAssets(getApplicationContext(),"course_1.json"),getApplicationContext());
        ProjectData.FetchDataFromJson(Utils.getJsonFromAssets(getApplicationContext(),"course_2.json"),getApplicationContext());
        ProjectData.FetchDataFromJson(Utils.getJsonFromAssets(getApplicationContext(),"course_3.json"),getApplicationContext());
    }
    public Dialog InitSignInDialog()
    {
        Dialog dialog = new Dialog(this);
        Handler handler = new Handler(Looper.getMainLooper());
        dialog.setContentView(R.layout.dialog_sign_in);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageButton closeButton = dialog.findViewById(R.id.exit_button);
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
        ImageButton okayButton = dialog.findViewById(R.id.okay_button);
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okayButton.setImageResource(R.drawable.ok_btn_press);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        okayButton.setImageResource(R.drawable.ok_btn);
                    }
                }, 150);
            }
        });
        ImageButton registerButton = dialog.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerButton.setImageResource(R.drawable.register_button_press);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        registerButton.setImageResource(R.drawable.register_button);
                    }
                }, 150);
            }
        });
        return dialog;
    }
    public void InitSignInLayoutFunction(Dialog dialog)
    {
        // Sign_in button
        TextView signInText = findViewById(R.id.sign_in_button);
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đóng dialog khi nhấn OK
                dialog.show();

            }
        });
        // Next button
        Handler handler = new Handler(Looper.getMainLooper());
        ImageButton button = findViewById(R.id.nextButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, home_activity.class);
                startActivity(intent);
            }
        });
    }


    //
}