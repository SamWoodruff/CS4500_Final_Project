package com.interpreter.classes;

public class instructInfo_t {
    private String functionName;
    int numArgs;
    int useImmediate;

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public int getNumArgs() {
        return numArgs;
    }

    public void setNumArgs(int numArgs) {
        this.numArgs = numArgs;
    }

    public int getUseImmediate() {
        return useImmediate;
    }

    public void setUseImmediate(int useImmediate) {
        this.useImmediate = useImmediate;
    }

    public instructInfo_t(String functionName, int numArgs, int useImmediate) {
        this.functionName = functionName;
        this.numArgs = numArgs;
        this.useImmediate = useImmediate;
    }
}
