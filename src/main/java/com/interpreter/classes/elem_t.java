package com.interpreter.classes;

public class elem_t {
    private String name;
    private int val;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "elem_t{" +
                "name='" + name + '\'' +
                ", val=" + val +
                '}';
    }
}
