package com.example.work;

public class QuestionModel {
    public QuestionModel(String questionString, String answer) {
        QuestionString = questionString;
        Answer = answer;
    }

    private String Answer;
    String QuestionString;

    public String getQuestionString() {
        return QuestionString;
    }

    public void setQuestionString(String questionString) {
        QuestionString = questionString;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

}

