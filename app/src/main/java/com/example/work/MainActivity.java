package com.example.work;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    TextView question, score, questionCount;
    EditText answer;
    Button submit;
    ProgressBar progressBar;
    ArrayList<QuestionModel> questionModelArrayList;

    int currentPosition = 0;
    int numberOfCorrectAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question = findViewById(R.id.question);
        questionCount = findViewById(R.id.noQuestion);
        score = findViewById(R.id.score);
        answer = findViewById(R.id.answer);
        submit = findViewById(R.id.submit);
        progressBar = findViewById(R.id.progress);

        questionModelArrayList = new ArrayList<>();
        setUpQuestion();
        setData();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

    }

    public void checkAnswer() {
        String answerString = answer.getText().toString().trim();
        if (answerString.equalsIgnoreCase(questionModelArrayList.get(currentPosition).getAnswer())){
            numberOfCorrectAnswer++;

            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Good job!")
                    .setContentText("Right Answer")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            currentPosition++;
                            setData();
                            answer.setText("");

                        }
                    }).show();
        }else {
            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Wrong Answer")
                    .setContentText("The right answer is : " + questionModelArrayList.get(currentPosition).getAnswer())
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            currentPosition ++;
                            setData();
                            answer.setText("");

                        }
                    }).show();
        }

        int x = ((currentPosition + 1) * 100) / questionModelArrayList.size();
        progressBar.setProgress(x);
    }


    private void setUpQuestion() {
        questionModelArrayList.add(new QuestionModel("What is 1 + 2 ?", "3"));
        questionModelArrayList.add(new QuestionModel("What is 8 * 8 ?", "64"));
        questionModelArrayList.add(new QuestionModel("What is 9 * 12 ?", "108"));
        questionModelArrayList.add(new QuestionModel("What is 6 * 8 ?", "48"));
        questionModelArrayList.add(new QuestionModel("What is 12/2 ?", "6"));
    }

    private void setData() {

        if(questionModelArrayList.size()>currentPosition){
            question.setText(questionModelArrayList.get(currentPosition).getQuestionString());
            score.setText("Score : " + numberOfCorrectAnswer +"/" +questionModelArrayList.size());
            questionCount.setText("Question No : " + (currentPosition+1));

        }else {
            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText("You have successfully completed the quiz")
                    .setContentText("Your score is" + numberOfCorrectAnswer +"/" +questionModelArrayList.size())
                    .setConfirmText("Restart")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            currentPosition = 0;
                            numberOfCorrectAnswer = 0;
                            progressBar.setProgress(0);
                            setData();
                        }
                    })
                    .setCancelText("Close")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            finish();
                        }
                    }).show();
        }
    }

}




