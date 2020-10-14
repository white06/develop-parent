package com.tdu.develop.resource.pojo;

public class ErrorQues {
    private String Id;
    private String QuestionKey;
    private String StuKey;
    private String ErrorAns;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getQuestionKey() {
        return QuestionKey;
    }

    public void setQuestionKey(String questionKey) {
        QuestionKey = questionKey;
    }

    public String getStuKey() {
        return StuKey;
    }

    public void setStuKey(String stuKey) {
        StuKey = stuKey;
    }

    public String getErrorAns() {
        return ErrorAns;
    }

    public void setErrorAns(String errorAns) {
        ErrorAns = errorAns;
    }

}
