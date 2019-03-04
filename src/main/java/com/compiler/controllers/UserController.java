package com.compiler.controllers;

import com.compiler.classes.instruction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class UserController {
    @RequestMapping("/")
    public String home(Map<String, Object> model) {

        instruction instruction = new instruction();
        ArrayList<instruction> instructions = instruction.instructionList();
        model.put("instructions", instructions);
        return "CompilerPage";
    }
}
