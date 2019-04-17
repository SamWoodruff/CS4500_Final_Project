package com.interpreter.classes;

public class Stack {
    private int[] s = new int[1000];
    private int tos= - 1;

    public int getTos() {
        return tos;
    }

    public void setTos(int tos) {
        this.tos = tos;
    }
    public int getLoc(int loc){
        return s[loc];
    }

    public void setLoc(int loc,int ACC){
        s[loc] = ACC;
    }
}
