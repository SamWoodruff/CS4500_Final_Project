package com.interpreter.classes;

import java.util.Arrays;

public class instruct_t {
    private int[] args = new int[2];
    private String functionName;

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    @Override
    public String toString() {
        return "instruct_t{" +
                "args=" + Arrays.toString(args) +
                ", functionName='" + functionName + '\'' +
                '}';
    }

    public String getFunctionName() {
        return functionName;
    }

    public int getArg1(){
        return args[0];
    }
    public void setArg(int index,int arg){
        this.args[index] = arg;
    }
    public int getArg(int index){
        return args[index];
    }
    public int getArg2(){
        return args[1];
    }
}
