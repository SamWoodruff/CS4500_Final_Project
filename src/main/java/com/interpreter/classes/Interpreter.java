package com.interpreter.classes;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Interpreter {
    public Interpreter() {
    }
    public static Scanner in;
    public static Stack stack = new Stack();

    public static int ACC = 0;
    public static int IP = 0;
    public static ArrayList<elem_t> Vars = new ArrayList<>();
    public static int NumVars = 0;
    public static ArrayList<elem_t> Labels = new ArrayList<>();
    public static int NumLabels = 0;
    public static ArrayList<instruct_t> Instructs = new ArrayList<>();
    public static int NumInstructs = 0;
    public static int NumNumbers = 0;

    public static void cleanUp(){
        ACC = 0;
        IP = 0;
        Vars.clear();
        NumVars = 0;
        Labels.clear();
        NumLabels = 0;
        Instructs.clear();
        NumInstructs = 0;
        NumNumbers = 0;
        stack.setTos(-1);


    }

    public static String error(String s) {
        System.out.println("Error: " + s);
        cleanUp();
        return "Error: " + s;
        //System.exit(0);
    }

    public static void ADD(int a, int b) {
        ACC += Vars.get(a).getVal();
    }

    public static void SUB(int a, int b) {
        ACC -= Vars.get(a).getVal();
    }

    public static void MULT(int a, int b) {
        ACC *= Vars.get(a).getVal();
    }

    public static void DIV(int a, int b) {
        ACC /= Vars.get(a).getVal();
    }

    public static void LOAD(int a, int b) {
        ACC = Vars.get(a).getVal();
    }

    public static void STORE(int a, int b) {
        Vars.get(a).setVal(ACC);
    }

    public static void COPY(int a, int b) {
        Vars.get(a).setVal(Vars.get(b).getVal());
    }

    public static void READ(int a, int b) {
        Scanner in = new Scanner(System.in);
        System.out.println("Give number: ");
        int num = in.nextInt();
        Vars.get(a).setVal(num);
    }

    public static String WRITE(int a, int b) {
        System.out.println("Number is: " + Vars.get(a).getVal());
        return "Number is: " + Vars.get(a).getVal() + "\n";
    }

    public static void STOP(int a, int b) {
        //System.exit(0);
    }

    public static void NOOP(int a, int b) {
        return;
    }

    public static void BR(int a, int b) {
        IP = Labels.get(a).getVal() - 1;
    }

    public static void BRNEG(int a, int b) {
        if (ACC < 0) {
            IP = Labels.get(a).getVal() - 1;
        }
    }

    public static void BRZNEG(int a, int b) {
        if (ACC <= 0) {
            IP = Labels.get(a).getVal() - 1;
        }
    }

    public static void BRPOS(int a, int b) {
        if (ACC > 0) {
            IP = Labels.get(a).getVal() - 1;
        }
    }

    public static void BRZPOS(int a, int b) {
        if (ACC >= 0) {
            IP = Labels.get(a).getVal() - 1;
        }
    }

    public static void BRZERO(int a, int b) {
        if (ACC == 0) {
            IP = Labels.get(a).getVal() - 1;
        }
    }

    public static void PUSH(int a, int b) {
        if (stack.getTos() == 1000 - 1) {
            error("Stack overflow");
        }
        stack.setTos(stack.getTos() + 1);//increment tos
    }

    public static void POP(int a, int b) {
        if (stack.getTos() < 0) {
            error("Stack underflow");
        }
        stack.setTos(stack.getTos() - 1);//decrement tos
    }

    public static void STACKW(int a, int b) {
        int loc;
        loc = stack.getTos() - Vars.get(a).getVal();
        if (loc < 0 || loc > stack.getTos()) {
            error("Stack write error");
        }
        stack.setLoc(loc, ACC);
    }

    public static void STACKR(int a, int b) {
        int loc;
        loc = stack.getTos() - Vars.get(a).getVal();
        if (loc < 0 || loc > stack.getTos()) {
            error("Stack read error");
        }
        ACC = stack.getLoc(loc);
    }

    public static String Reserved[] = {"ADD", "SUB", "MULTI", "DIV", "LOAD", "STORE", "COPY", "READ",
            "WRITE", "STOP", "NOOP", "BR", "BRNEG", "BRZNEG", "BRPOS", "BRZPOS", "BRZERO",
            "PUSH", "POP", "STACKW", "STACKR", ""};

    public static instructInfo_t InstructInfo[] = {
            new instructInfo_t("ADD", 1, 1),
            new instructInfo_t("SUB", 1, 1),
            new instructInfo_t("MULT", 1, 1),
            new instructInfo_t("DIV", 1, 1),
            new instructInfo_t("LOAD", 1, 1),
            new instructInfo_t("STORE", 1, 0),
            new instructInfo_t("COPY", 2, 0),
            new instructInfo_t("READ", 1, 0),
            new instructInfo_t("WRITE", 1, 1),
            new instructInfo_t("STOP", 0, 0),
            new instructInfo_t("NOOP", 0, 0),
            new instructInfo_t("BR", 1, 0),
            new instructInfo_t("BRNEG", 1, 0),
            new instructInfo_t("BRZNEG", 1, 0),
            new instructInfo_t("BRPOS", 1, 0),
            new instructInfo_t("BRZPOS", 1, 0),
            new instructInfo_t("BRZERO", 1, 0),
            new instructInfo_t("PUSH", 0, 0),
            new instructInfo_t("POP", 0, 0),
            new instructInfo_t("STACKW", 1, 1),
            new instructInfo_t("STACKR", 1, 1)
    };

    public static int inReserved(String s) {
        int i;
        for (i = 0; ; i++) {
            if (Reserved[i].equals("")) {
                return -1;
            }
            if (Reserved[i].equals(s)) {
                return i;
            }
        }
    }

    public static int nothing(String s) {
        if (s == null || s.matches("\\s+") || s.isEmpty()) {
            return 1;
        } else
            return 0;
    }

    public static int isNumber(String s) {
        int i = 0;
        if (s == null || s.isEmpty()) {
            return 0;
        }
        if ((s.charAt(0) == '-' || s.charAt(0) == '+')) {
            if (!Character.isDigit(s.charAt(1))) {
                error("Minus/plus by itself");
            } else {
                i++;
            }
        }
        for (i = i; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return 0;
            }
        }
        return 1;
    }


    public static String interpret(ArrayList<String> input){
        pass1(input);//count instructs,labels, and variables
        //For Debugging pass1
        //System.out.println("NumLabels: " + NumLabels + " NumNumbers: " + NumNumbers + " NumVars: " + NumVars + " NumInstructs: " + NumInstructs);
        pass2(input);//writes variables and labels
        //For Debugging pass2
       /* System.out.println("Labels: " + Labels.toString());
        System.out.println("Variables: " + Vars.toString());*/
        pass3(input);
        //For Debugging pass3
       /* int i;
        for(int m = 0; m < NumInstructs; m++) {
            for (i = 0; i < 21; i++) {
                if (InstructInfo[i].getFunctionName().equals(Instructs.get(m).getFunctionName())){
                    System.out.print(Reserved[i]);
                    break;
                }
            }
            System.out.print("   ");
            for (int k = 0; k < InstructInfo[i].getNumArgs(); k++) {
                System.out.print(Instructs.get(m).getArg(k));
            }
            System.out.println();
        }*/
        String returnToUser = "";
        for(int  p = 0; IP < Instructs.size(); IP++){
            returnToUser += run(Instructs.get(IP).getFunctionName(),IP);
            p = IP;
        }
        return returnToUser;
}
    public static String run(String name, int i){
        /*System.out.println("TEST IN RUN FUNCTION:");
        System.out.println(name);*/
        String temp = "";
        switch (name){
            case "ADD":
                ADD(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "PUSH":
                PUSH(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "STACKW":
                STACKW(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "STACKR":
                STACKR(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "POP":
                POP(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "STOP":
                STOP(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "STORE":
                STORE(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "WRITE":
                temp = WRITE(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "LOAD":
                LOAD(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "SUB":
                SUB(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "MULT":
                MULT(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "DIV":
                DIV(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "COPY":
                COPY(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "READ":
                READ(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "NOOP":
                NOOP(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "BR":
                BR(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "BRNEG":
                BRNEG(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "BRZNEG":
                BRZNEG(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "BRPOS":
                BRPOS(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "BRZPOS":
                BRZPOS(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
            case "BRZERO":
                BRZERO(Instructs.get(i).getArg(0), Instructs.get(i).getArg(1));
                break;
        }
        return temp;
    }

    public static void pass1(ArrayList<String> input){
        String line;
        int instruct = -1;
        for(int i = 0; i < input.size(); i++){
            if(nothing(input.get(i)) == 1){
                continue;
            }
            int index = -1;
            index = input.get(i).indexOf(":");
            if(index > 0){
                NumLabels++;
                index++;//Move index after label
                line = input.get(i).substring(index).trim();
            }else {
                line = input.get(i);
            }

            String[] tokens = line.split(" ");
            for(int k = 0; k < tokens.length; k++){
                if(nothing(tokens[k]) == 1){
                    continue;
                }
                instruct = inReserved(tokens[k]);
                if(instruct >= 0){
                    NumInstructs++;
                    if(InstructInfo[instruct].useImmediate == 1){
                        if(isNumber(tokens[k+1]) == 1) {
                            NumNumbers++;
                            break;
                        }else{
                            break;
                        }
                    }else{
                        break;
                    }
                }else if(isNumber(tokens[k]) == 0){
                    NumVars++;
                    break;
                }
            }
        }
    }

    public static void pass2(ArrayList<String> input){
        int numLabels = 0, numVarsNumbers = 0, numInstructs = 0, instruct = -1;
        String name = null, line;
        for(int i = 0; i < input.size(); i++){
            if(nothing(input.get(i)) == 1){continue;};
            int index = -1;
            index = input.get(i).indexOf(":");
            if(index > 0){
                elem_t Label = new elem_t();
                Labels.add(Label);
                name = input.get(i);
                name = name.substring(0,index);
                Labels.get(numLabels).setName(name);
                Labels.get(numLabels).setVal(numInstructs);
                numLabels++;
                index++;
                line = input.get(i).substring(index).trim();
            }else{
                line = input.get(i);
            }

            String[] tokens = line.split(" ");
            for(int k = 0; k < tokens.length; k++){
                if(nothing(tokens[k]) == 1){
                    continue;
                }
                instruct = inReserved(tokens[k]);
                if(instruct < 0){
                    elem_t var = new elem_t();
                    Vars.add(var);
                    Vars.get(numVarsNumbers).setName(tokens[k]);
                    for(int m = 0; m < numVarsNumbers; m++){
                        if(Vars.get(m).getName() == tokens[k]){
                            error("Multiply Defined Variable\n");
                        }
                    }
                    if(k + 1 < tokens.length) {
                        k++;
                        if(tokens[k] == null || isNumber(tokens[k]) == 0){
                            error("Variable name must be followed by integer");
                        }
                        Vars.get(numVarsNumbers).setVal(Integer.parseInt(tokens[k]));
                    }
                    if(k + 1 < tokens.length) {
                        k++;
                        if(nothing(tokens[k]) == 0){
                            error("Left over on variable definition line");
                        }
                    }
                    numVarsNumbers++;
                }else{//instruction
                    for(int m = 0; m < InstructInfo[instruct].numArgs; m++){
                        if(k + 1 < tokens.length) {
                            k++;
                            if((tokens[k] == null) || (InstructInfo[instruct].useImmediate == 0 && isNumber(tokens[k]) == 1)){

                                    error("Invalid argument");
                            }
                            if(isNumber(tokens[k]) == 1){
                                elem_t var = new elem_t();
                                Vars.add(var);
                                Vars.get(numVarsNumbers).setName(tokens[k]);
                                Vars.get(numVarsNumbers).setVal(Integer.parseInt(tokens[k]));
                                numVarsNumbers++;
                            }
                        }
                    }
                    numInstructs++;
                }
            }
        }
    }

    public static int findInTable(ArrayList<elem_t> table, String name){
        for(int i = 0; i < table.size(); i++){
            table.get(i).setName(table.get(i).getName().replace("\\s",""));
            if(table.get(i).getName().equals(name)){

                return i;
            }
        }
        return -1;
    }

    public static void pass3(ArrayList<String> input){
        int numInstructs = 0, instruct, label;
        String line;
        for(int i = 0; i < input.size(); i++){
            if(nothing(input.get(i)) == 1){continue;}
            int index = -1;
            index = input.get(i).indexOf(":");
            if(index > 0){
                index++;//Move index after label
                line = input.get(i).substring(index).trim();
            }else {
                line = input.get(i);
            }
            String[] tokens = line.split(" ");
            for(int k = 0; k < tokens.length; k++){
                instruct = inReserved(tokens[k]);
                if(instruct >= 0){
                    instruct_t ins = new instruct_t();
                    Instructs.add(ins);
                    Instructs.get(numInstructs).setFunctionName(InstructInfo[instruct].getFunctionName());
                    for(int m = 0; m < InstructInfo[instruct].numArgs; m++){
                        if(k + 1 < tokens.length) {
                            k++;
                            if(instruct >=11 && instruct <= 16){//BR's
                                label = findInTable(Labels, tokens[k] + " ");
                                if(label == -1){
                                    error("BRs must be followed by defined labels " + tokens[k] + label);
                                }
                                Instructs.get(numInstructs).setArg(m, label);
                            }else{
                                label = findInTable(Vars, tokens[k]);
                                if(label < 0){
                                    error("Unknown argument");
                                }
                                Instructs.get(numInstructs).setArg(m,label);
                            }
                        }
                    }
                    numInstructs++;
                }
            }
        }
    }
}
