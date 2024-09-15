package com.example.file;

import org.springframework.stereotype.Component;

@Component
public class Parser {

    public String[] getParseArr(String str){
        return str.split("(?<=\\S)\\s+(?=\\S)");

    }
}
