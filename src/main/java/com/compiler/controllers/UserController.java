package com.compiler.controllers;

import com.SSHConnection.ConnectSSH;
import com.compiler.classes.instruction;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class UserController {

    @RequestMapping("/")
    public String home(Map<String, Object> model, HttpSession session) {
        System.out.println("in spring\n");

        //Create new instruction object
        instruction instruction = new instruction();
        //Populate instruction object with list of instructions
        ArrayList<instruction> instructions = instruction.instructionList();
        //add instructions to session to use in CompilerPage.jsp
        session.setAttribute("instructions",instructions);
        ArrayList<instruction> selectedInstructions = new ArrayList<>();
        session.setAttribute("selectedInstructions",selectedInstructions);
        return "CompilerPage";
    }

    @RequestMapping(value="/retrieveList", method = RequestMethod.POST)
    public String getList(@RequestParam(value="arguments")String arguments, @RequestParam(value="editorList[]")String[] editorList, HttpSession session) {
        int skip = -1;/*Keep track of which element is new and skip it when assigning arguments*/

        ArrayList<instruction> instructions = (ArrayList<instruction>)session.getAttribute("instructions");
        ArrayList<instruction> selectedInstructions = (ArrayList<instruction>)session.getAttribute("selectedInstructions");

        if(!selectedInstructions.isEmpty()) {
            selectedInstructions.clear();
        }

        //This pass filters out line #'s and any whitespace
        for(int i = 0; i < editorList.length; i++){
            if(!editorList[i].contains(")")){
                skip = i;
            }
            editorList[i] = editorList[i].replaceAll("\\d","");
            editorList[i] = editorList[i].replace(")","");
            editorList[i] = editorList[i].replace(" ","");
            editorList[i] = editorList[i].replace("\n","");
            editorList[i] = editorList[i].replace("\t","");
            if(editorList[i].isEmpty()){
                editorList[i] = "Variable";
            }
            if(editorList[i].equals(":")){
                editorList[i] = "Label:";
            }
        }

        for (int i = 0; i < editorList.length; i++) {
            for (int k = 0; k < instructions.size(); k++) {
                if (instructions.get(k).getName().equals(editorList[i])) {
                     instruction temp = new instruction(editorList[i], instructions.get(k).getArgCount(), (i + 1));
                     selectedInstructions.add(temp);
                }
            }
        }

        String args[] = arguments.split(",");
        selectedInstructions = handleArgs(args,selectedInstructions,skip);
        session.setAttribute("selectedInstructions",selectedInstructions);

        return "CompilerPage";
    }

    @RequestMapping(value="/serverConnect", method = RequestMethod.POST)
    public void connectSSH() throws IOException, SftpException {
        ConnectSSH connection = new ConnectSSH();
        try {
            connection.connectSession();
            connection.connectShellChannel();


        } catch (JSchException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value="/createFile", method = RequestMethod.POST)
    public String exportFile(@RequestParam(value="arguments[]")String[] arguments, HttpSession session, HttpServletResponse response) throws IOException {
         ArrayList<instruction> selectedInstructions = (ArrayList<instruction>) session.getAttribute("selectedInstructions");
        //Ensures most updated arguments are in the proper locations
        selectedInstructions = handleArgs(arguments, selectedInstructions, -1);
        session.setAttribute("selectedInstructions", selectedInstructions);

        File export = new File("Export.asm");
        String temp = "";
        for (int i = 0; i < selectedInstructions.size(); i++) {
            if (!selectedInstructions.get(i).getName().equals("Variable") && !selectedInstructions.get(i).getName().equals("Label:")) {
                temp += selectedInstructions.get(i).getName();
                temp += " ";
            }
            if (selectedInstructions.get(i).getName().equals("Label:")) {
                selectedInstructions.get(i).args.set(0, selectedInstructions.get(i).args.get(0) + ":");
                temp += selectedInstructions.get(i).args.get(0);
                temp += " ";
                continue;
            }
            for (int k = 0; k < selectedInstructions.get(i).getArgCount(); k++) {
                temp += selectedInstructions.get(i).args.get(k);
                temp += " ";
            }
            temp += "\n";
        }
        //Create file
        String fileName = "src/main/webapp/WEB-INF/downloads/Export.asm";
        PrintWriter writer = new PrintWriter(fileName);
        writer.println(temp);
        writer.close();
        System.out.println(temp);

        return "CompilerPage";
    }

    @RequestMapping("/downloadFile")
    public ResponseEntity<ByteArrayResource> downloadFile(HttpSession session, HttpServletResponse response) throws IOException {
        String fileName ="src/main/webapp/WEB-INF/downloads/Export.asm";
        Path path = Paths.get(fileName);
        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + path.getFileName().toString())
                .contentType(MediaType.APPLICATION_PDF).contentLength(data.length)
                .body(resource);

    }

    @RequestMapping(name="/importFile", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public String importFile(@RequestParam MultipartFile file, HttpSession session) throws IOException {
        ArrayList<instruction> instructions = (ArrayList<instruction>)session.getAttribute("instructions");
        ArrayList<instruction> selectedInstructions = (ArrayList<instruction>)session.getAttribute("selectedInstructions");

        int isLabel = -1;
        String content = new String(file.getBytes());//Write file contents to string
        String lines[] = content.split("\n");

        if(!selectedInstructions.isEmpty()) {
            selectedInstructions.clear();
        }

        for(int i = 0; i < lines.length; i++) {
            String words[] = lines[i].split("\\s+");
            for(int k = 0; k < words.length; k++){
                System.out.println(words[k]);

                //Check line for instruction
               for(int t = 0; t < instructions.size(); t++){
                    if(instructions.get(t).getName().equals(words[k])){
                       instruction ins = new instruction(words[k],instructions.get(t).getArgCount(),(i+1));
                        //assign args
                        for(int m = 0; m < ins.getArgCount(); m++){
                            if(k+1 < words.length) {
                                k++;
                                ins.args.add(words[k]);
                            }
                        }
                        selectedInstructions.add(ins);
                       continue;
                    }
                }

                //Check line for variable
                if (words[k].length() == 1 && (k+1) < words.length) {
                        if(words[k + 1].matches("\\d+")){
                            instruction ins = new instruction("Variable",2,(i+1));
                            ins.args.add(words[k]);
                            k++;
                            ins.args.add(words[k]);
                            selectedInstructions.add(ins);
                            continue;
                        }else{
                            //return no/improper assignment to var error
                        }
                }
                //check line for label
                if(words[k].contains(":") && k==0){
                    instruction ins = new instruction("Label:",1,(i+1));
                    words[k].replace(":","");//Get rid of colon
                    ins.args.add(words[k]);
                    selectedInstructions.add(ins);
                    continue;
                }else if(words[k].contains(":") && k!=0){
                    //Label in wrong spot return error
                }
            }
        }

        return "CompilerPage";
    }

    //Handles args when execute button is clicked
    ArrayList<instruction> handleArgs(String[] arguments,ArrayList<instruction> selectedInstructions, int skip){
            int count = 0, start = 0;
            //Clear arraylists each instruction has holding the args
            for (int i = 0; i < selectedInstructions.size(); i++) {
                if (!(selectedInstructions.get(i).args.isEmpty())) {
                    selectedInstructions.get(i).args.clear();
                }
            }

            //assign the new args to instructions
            for (int i = 0; i < selectedInstructions.size(); i++) {
                for (int k = 0; k < selectedInstructions.get(i).getArgCount(); k++) {
                    if(skip == i){
                        break;
                    }
                    selectedInstructions.get(i).args.add(arguments[count]);
                    count++;
                }
            }

            return selectedInstructions;
        }
}