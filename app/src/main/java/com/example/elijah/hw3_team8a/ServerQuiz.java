package com.example.elijah.hw3_team8a;

import java.net.URL;
import java.util.ArrayList;
/**
 * Created by Elijah on 9/25/2015.
 */

// This class is for SERVER MADE questions in the TriviaGame Activity, to retrieve the user made questions use UserNewQuiz Class

public class ServerQuiz {
    private int answerID, questionID;
    private String Question;
    private ArrayList<String> userOptions = new ArrayList<String>();
    private String userPic;

    public ServerQuiz() {
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public void setUserOptions(ArrayList<String> userOptions) {
        this.userOptions = userOptions;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public void addUserOptions(String option) {
        userOptions.add(option);
    }

    public String getUserPic() {
        return userPic;
    }

    public String getQuestion() {
        return Question;
    }

    public String getUserOptions() {
        String result = "";

        for(String s :userOptions) {
            result += s + ";";
        }
        return result;
    }
}
