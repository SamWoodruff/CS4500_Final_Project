package com.compiler.controllers;

import com.compiler.classes.instruction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.management.DynamicMBean;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @RequestMapping("/")
    public String home(Map<String, Object> model, HttpSession session) {
        System.out.println("in spring\n");
        instruction instruction = new instruction();
        ArrayList<instruction> instructions = instruction.instructionList();
        session.setAttribute("instructions",instructions);
        ArrayList<instruction> selectedInstructions = new ArrayList<>();
        session.setAttribute("selectedInstructions",selectedInstructions);
        return "CompilerPage";
    }
    @RequestMapping(value="/retrieveList", method = RequestMethod.POST)
    public String getList(@RequestParam(value="editorList[]")String[] editorList, Map<String, Object> model, HttpSession session) {
        System.out.println("-----FROM JAVASCRIPT-----");
        for(int i = 0; i < editorList.length; i++){
            editorList[i] = editorList[i].replace(" ","");
            editorList[i] = editorList[i].replace("\n","");
            editorList[i] = editorList[i].replace("\t","");
            System.out.println(editorList[i]);
        }
        ArrayList<instruction> instructions = (ArrayList<instruction>)session.getAttribute("instructions");
        ArrayList<instruction> selectedInstructions = (ArrayList<instruction>)session.getAttribute("selectedInstructions");

        if(!selectedInstructions.isEmpty()) {
            selectedInstructions.clear();
        }
        for(int i = 0; i < editorList.length; i++){
                for(int k = 0; k < instructions.size();k++){
                    if(instructions.get(k).getName().equals(editorList[i])){
                        instruction temp = new instruction(editorList[i],instructions.get(k).getArgCount());
                        selectedInstructions.add(temp);
                    }
                }
        }
        System.out.println("-----LIST-----");
        for(int i = 0; i < selectedInstructions.size(); i++){
            System.out.println(selectedInstructions.get(i).getName());
            System.out.println(selectedInstructions.get(i).getArgCount());
        }

        session.setAttribute("selectedInstructions",selectedInstructions);

        return "CompilerPage";
    }
    @RequestMapping(value="/addToEditor", method = RequestMethod.POST)
    public String getInstruction(@RequestParam(value="instruction") String instruction, Map<String, Object> model) {
        System.out.println(instruction);
        return "CompilerPage";
    }
}
