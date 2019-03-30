package com.compiler.classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class instruction {

    private int argCount;
    private String name;
    private int lineNum;
    private int argument;
    private int argument2;
    public ArrayList<String> args;
    ArrayList<Integer> argCounts;

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public instruction() {
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    public void addArg(String arg) {
        this.args.add(arg);
    }


    public ArrayList<Integer> getargCounts() {
        return argCounts;
    }

    public instruction(String name) {
        this.name = name;
    }

    //Set name and number of arguments to an instruction
    public instruction(String name, int argCount) {
        argCounts = new ArrayList<>();
        args = new ArrayList<>();
       for(int i = 0; i < argCount; i++){
            argCounts.add(1);
        }
        this.name = name;
        this.argCount = argCount;
    }

    //Set name and number of arguments to an instruction
    public instruction(String name, int argCount, int lineNum) {
        argCounts = new ArrayList<>();
        args = new ArrayList<>();
        for(int i = 0; i < argCount; i++){
            argCounts.add(1);
        }
        this.name = name;
        this.argCount = argCount;
        this.lineNum = lineNum;
    }


    /*--------Getters & Setters--------*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   /* public static int getArgCount() {
        return argCount;
    }*/

    public int getArgCount() {
        return argCount;
    }



    //List of all instructions
    public ArrayList<instruction> instructionList(){
        ArrayList<instruction> instructions = new ArrayList<>();
        instruction temp;

        //Will hold vars
        temp = new instruction("Variable",2);
        instructions.add(temp);
        //For Labels:
        temp = new instruction("Label:",1);
        instructions.add(temp);
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
        temp = new instruction("NOOP",0);
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
