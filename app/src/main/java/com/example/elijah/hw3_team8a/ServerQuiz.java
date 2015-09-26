package com.example.elijah.hw3_team8a;

import java.net.URL;
import java.util.ArrayList;
/**
 * Created by Elijah on 9/25/2015.
 */
public class ServerQuiz {
    private int answerID;
    private String Question;
    private ArrayList<String> userOptions;
    private URL userPic;

    public ServerQuiz(String question) {
        Question = question;
    }

    public void setUserPic(URL userPic) {
        this.userPic = userPic;
    }

    public void addUserOptions(String option) {
        userOptions.add(option);
    }

    public URL getUserPic() {
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
