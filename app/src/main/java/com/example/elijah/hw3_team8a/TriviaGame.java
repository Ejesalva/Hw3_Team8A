package com.example.elijah.hw3_team8a;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TriviaGame extends AppCompatActivity {

    private static int counter = 0; //Count Array place
    private static int quizCounter = 1; //Number of questions that have been answered
    private static int correct = 0; //Number right answers
    private ArrayList<String> questionInfo = new ArrayList<>();
    RadioGroup options;
    ServerQuiz quiz;
    TextView quizQuestion, quizNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_game);
        options = (RadioGroup) findViewById(R.id.radiogroup_quiz);
        quizQuestion = (TextView) findViewById(R.id.quiz_question);
        quizNumber = (TextView) findViewById(R.id.textV_quizNumber);
        questionInfo = new ArrayList<String>();


        RequestParams params = new RequestParams("GET", "http://dev.theappsdr.com/apis/trivia_fall15/getAll.php");
        new GetData().execute(params);


        findViewById(R.id.quiz_quit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.quiz_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(options.getCheckedRadioButtonId() == -1){
                    Toast.makeText(TriviaGame.this, "Plese choose an answer", Toast.LENGTH_SHORT).show();
                } else {
                    quiz.setAnswerID(options.getCheckedRadioButtonId());

                    Log.d("test userAnswer: ", String.valueOf(quiz.getAnswerID()));
                    counter++;
                    parser(questionInfo.get(counter));
                    populator();

                    RequestParams params = new RequestParams("POST", " http://dev.theappsdr.com/apis/trivia_fall15/checkAnswer.php");
                    params.addParam("gid", "e871e9df5b6b15411af5ec81c10adcad");
                    params.addParam("qid", String.valueOf(quiz.getQuestionID()));
                    params.addParam("a", String.valueOf(quiz.getAnswerID()));
                    new GetAnswer().execute(params);
                }
            }
        });
    }

    private void parser(String quizInfo){
        options.removeAllViews();

        quiz = new ServerQuiz();
        String[] ss=quizInfo.split(";");

        quiz.setQuestionID(Integer.parseInt(ss[0]));
        quiz.setQuestion(ss[1]);
        for(int i=2;i<ss.length;i++)
        {
            if(ss[i].startsWith("http://")){
                quiz.setUserPic(ss[i]);
            } else if(ss[i].matches("") || ss[i].matches("-1")){
                //No picture
            } else {
                Log.d("test Index: ", String.valueOf(i));
                Log.d("test Value: ", ss[i]);
                Log.d("test Length: ", String.valueOf(ss.length));
                RadioButton rdbtn = new RadioButton(TriviaGame.this);
                rdbtn.setText(ss[i]);
                options.addView(rdbtn);
                quiz.addUserOptions(ss[i]);
            }
        }
    }

    private void populator(){
        quizQuestion.setText(quiz.getQuestion());
        quizNumber.setText(String.valueOf(quizCounter));
        quizCounter++;
    }

    private class GetData extends AsyncTask<RequestParams, Void, String> {

        ProgressDialog pDialog;

        protected String doInBackground(RequestParams... params){
            BufferedReader reader = null;

            try {
                HttpURLConnection con = params[0].setupConnection();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                    questionInfo.add(line);
                    Log.d("demo", line);
                }
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TriviaGame.this);
            pDialog.setMessage("Getting Questions...");
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
        }

        protected void onPostExecute(String results) {
            pDialog.dismiss();
            Log.d("demo", questionInfo.get(0));
            new LoadFirst().execute();
        }
    }

    private class LoadFirst extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    parser(questionInfo.get(0));
                    populator();
                }
            });


            return null;
        }
    }

    private class GetAnswer extends AsyncTask<RequestParams, Void, Void> {


        protected Void doInBackground(RequestParams... params){
            BufferedReader reader = null;

            try {
                HttpURLConnection con = params[0].setupConnection();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                correct += Integer.valueOf(reader.readLine());
                Log.d("test Answer: ", String.valueOf(correct));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
