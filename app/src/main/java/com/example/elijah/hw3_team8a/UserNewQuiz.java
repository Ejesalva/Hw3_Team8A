package com.example.elijah.hw3_team8a;

import java.util.ArrayList;

/**
 * Created by Elijah on 9/26/2015.
 */

// This class is for USER MADE questions in the CreateQuestion Activity, to retrieve API other peoples questions use ServerQuiz

public class UserNewQuiz {
    private String userQuestion = "", URLBitStream = "";
    private String filePath = "";
    private ArrayList<String> userOptions = new ArrayList<>();
    private int answerID;

    public  UserNewQuiz() {

    }

    public String getURLBitStream() {
        return URLBitStream;
    }

    public void setURLBitStream(String URLBitStream) {
        this.URLBitStream = URLBitStream;
    }

    public String getFilePathFinal() {
        return filePath + ";";
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setUserQuestion(String userQuestion) {
        this.userQuestion = userQuestion;
    }

    public void setUserOptions(ArrayList<String> userOptions) {
        this.userOptions = userOptions;
    }

    public int getAnswerID() {
        return answerID;
    }


    public String getAnswerIDFinal() {
        return Integer.toString(answerID) + ";";
    }

    public ArrayList<String> getUserOptions() {
        return userOptions;
    }

    public String getUserQuestion() {
        return userQuestion;
    }

    public String getUserQuestionFinal() {
        return userQuestion + ";";
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public void addOption(String option) {
        userOptions.add(option);
    }

    public String getUserOptionsFinal() {
        String result = "";
        for(String o : userOptions){
            result += o + ";";
        }
        return result;
    }
}
