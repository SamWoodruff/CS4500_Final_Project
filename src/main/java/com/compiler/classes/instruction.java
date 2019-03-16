package com.compiler.classes;

import java.util.ArrayList;
import java.util.List;

public class instruction {
    private int argCount;
    private String name;
    private int argument;
    private int argument2;

    public int getArgCount() {
        return argCount;
    }

    ArrayList<Integer> argCounts;
    private int lineNumber;
    List<String> selectedInstructions;
    public ArrayList<Integer> getArgCounts() {
        return argCounts;
    }

    public instruction(){

    }

    public instruction(String name) {
        this.name = name;
    }

    public instruction(String name, int argCount) {
        argCounts = new ArrayList<>();
        for(int i = 0; i < argCount; i++){
            argCounts.add(0);
        }
        this.name = name;
        this.argCount = argCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   /* public static int getArgCount() {
        return argCount;
    }*/

    public void setArgCount(int argCount) {
        this.argCount = argCount;
    }

    public int getArgument() {
        return argument;
    }

    public void setArgument(int argument) {
        this.argument = argument;
    }

    public int getArgument2() {
        return argument2;
    }

    public void setArgument2(int argument2) {
        this.argument2 = argument2;
    }

    public ArrayList<instruction> instructionList(){
        ArrayList<instruction> instructions = new ArrayList<>();
        instruction temp;

        //ADD
        temp = new instruction("ADD",1);
        instructions.add(temp);
        //BR
        temp = new instruction("BR",1);
        instructions.add(temp);
        //BRNEG
        temp = new instruction("BRNEG",1);
        instructions.add(temp);
        //BRZNEG
        temp = new instruction("BRZNEG",1);
        instructions.add(temp);
        //BRPOS
        temp = new instruction("BRPOS",1);
        instructions.add(temp);
        //BRZPOS
        temp = new instruction("BRZPOS",1);
        instructions.add(temp);
        //BRZERO
        temp = new instruction("BRZERO",1);
        instructions.add(temp);
        //COPY
        temp = new instruction("COPY",2);
        instructions.add(temp);
        //DIV
        temp = new instruction("DIV",1);
        instructions.add(temp);
        //MULT
        temp = new instruction("MULT",1);
        instructions.add(temp);
        //READ
        temp = new instruction("READ",1);
        instructions.add(temp);
        //WRITE
        temp = new instruction("WRITE",1);
        instructions.add(temp);
        //STOP
        temp = new instruction("STOP",0);
        instructions.add(temp);
        //STORE
        temp = new instruction("STORE",1);
        instructions.add(temp);
        //SUB
        temp = new instruction("SUB",1);
        instructions.add(temp);
        //NOOP
        temp = new instruction("NOOP",1);
        instructions.add(temp);
        //LOAD
        temp = new instruction("LOAD",1);
        instructions.add(temp);
        //PUSH
        temp = new instruction("PUSH",0);
        instructions.add(temp);
        //POP
        temp = new instruction("POP",0);
        instructions.add(temp);
        //STACKW
        temp = new instruction("STACKW",1);
        instructions.add(temp);
        //STACKR
        temp = new instruction("STACKR",1);
        instructions.add(temp);

        return instructions;
    }
}
