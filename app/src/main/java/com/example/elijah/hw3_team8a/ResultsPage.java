package com.example.elijah.hw3_team8a;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ResultsPage extends AppCompatActivity {

    ProgressBar progressPercentCorrect;
    TextView percentCorrect;
    double numCorrect = 0, numQuestions = 1;
    double progress = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            numCorrect = extras.getInt("numCorrect");
            numQuestions = extras.getInt("numQuestions");
            Log.d("test correct", String.valueOf(numCorrect));
            Log.d("test questions", String.valueOf(numQuestions));
        }

        progressPercentCorrect = (ProgressBar) findViewById(R.id.progress_percentCorrect);
        percentCorrect = (TextView) findViewById(R.id.results_percentage);

        progress = (numCorrect / numQuestions) * 100;
        Log.d("test result", String.valueOf(numCorrect/numQuestions));
        progressPercentCorrect.setMax(100);
        percentCorrect.setText((int)progress+ "%");
        progressPercentCorrect.setProgress((int)progress);


        findViewById(R.id.results_quitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(getBaseContext(), MainActivity.class);
                startActivity(mainActivity);

            }
        });

        findViewById(R.id.results_tryAgainButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trivia = new Intent(getBaseContext(), TriviaGame.class);
                startActivity(trivia);
            }
        });
    }


}
