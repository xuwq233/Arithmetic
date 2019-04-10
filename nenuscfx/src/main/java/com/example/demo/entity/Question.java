package com.example.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class Question {

    @JSONField(name="QUESTION")
    public String question;
    @JSONField(name="ANSWER")
    private String answer;

}
