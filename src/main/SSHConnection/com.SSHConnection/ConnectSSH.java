/*
 * Connect to delmar.umsl.edu via SSH
 * Send .asm file to test
 * Test .asm file with Assembly Virtual Machine
 * Provide an interactive shell for input
 *
 * Question: Should we use a general login for delmar?
 *      RISKS: Connection security to delmar DNE due to lack of key checking
 *
 * Question: Should we ask user's to enter their own ssoid credentials?
 *      RISKS: We would need to check if virtual machine exists, then build
 *             through SSH connection
 */

import com.jcraft.jsch.*;

import java.io.*;
import java.util.Properties;


/**
 *
 * @author tdkxc6
 */
public class ConnectSSH {

    
    /**
     * @var user for SSH connection
     *      substitute "general" user information once a profile is created for us
     *      Currently: use personal credentials
     */
    private static final String user = "tdkxc6";
    
    
    /**
     * 
     * @var host for SSH connection
     */
    private static final String host = "delmar.umsl.edu";
    
    
    /**
     * ATTENTION: I have taken out my personal password for this
     *            Need to figure out secure connection without password in plain text
     * @var password for SSH connection
     */
    private static final String password = "";
    

    /**
     * 
     *
     */
    private static Channel myChannel;
    
    
    /**
     * 
     *
     */
    private static ChannelSftp sftpChannel;
    
    
    /**
     * 
     *
     */
    private static Session mySession;
    
    
    /**
     * 
     *
     */
    private static JSch jsch;


    //Update these Strings based on where the location of virtMach is located
    private static String changeDirTo="/accounts/students/t/tdkxc6/assemblyVirtMach";
    private static String filename="hello.txt";
    private static String fileToSend="/Users/tdkxc6/testSFTP/hello.txt";
    
    

    
    
    //no-arg constructor
    public void ConnectSSH() {
    
    }



    /**
     *  Establishes connection to sftp channel
     *
     */
    public void connectSftpChannel () {

        try {
            sftpChannel = (ChannelSftp) mySession.openChannel("sftp");
            System.out.println("Connecting sftp channel...");
            sftpChannel.connect(3000);
            System.out.println("Sftp channel connected...");
        } catch (Exception e) {}

    }


    /**
     *  send file generated by web application
     *  TODO: Where will we save users' file?
     *
     */
    public void sendFile () throws FileNotFoundException, SftpException {

        //change directory to virtmach executable
        sftpChannel.cd(changeDirTo);

        //Check if file is already in directory
        try {
            SftpATTRS check = sftpChannel.stat(filename);
            // If we get here, then file exists. delete it.
            System.out.println("File has been found, deleting...");
            deleteFile();
        } catch (SftpException e) {
            System.out.println("File DNE");
        }


        try {
            File f = new File(fileToSend);
            System.out.println("Sending file...");
            sftpChannel.put(new FileInputStream(f), f.getName());
            System.out.println("File has been sent...");
        } catch (FileNotFoundException e) {
            throw e;
        }

    }

    public void deleteFile () throws SftpException {
        sftpChannel.rm(filename);
        System.out.println("File deleted successfully...");

    }

    public void disconnectSftpChannel () {
        sftpChannel.disconnect();
        System.out.println("Sftp channel disconnected...");
    }

    /**
     *  Establishes session connection to delmar
     *
     */
    public void connectSession () throws JSchException {
        jsch = new JSch();
        mySession = jsch.getSession(user, host, 22);
        mySession.setPassword(password);
            
        Properties config = new Properties();
        config.setProperty("StrictHostKeyChecking", "no");
        mySession.setConfig(config);
        
        System.out.println("Connecting session...");
        mySession.connect(30000);
        System.out.println("Session connected... \n");
    }



    public void connectShellChannel () throws JSchException, IOException, SftpException {
        myChannel = mySession.openChannel("shell");
            
        myChannel.setInputStream(System.in);
        myChannel.setOutputStream(System.out);
        
        System.out.println("Connecting channel...");
        myChannel.connect(3000);
        System.out.println("Channel connected... \n");


        //execute script to run virtMach with send file
        //TODO - change script to run sent file. currently runs test.asm
        executeCommand("./assemblyVirtMach/execVirtMachine.sh");
    }

    public void disconnectShellChannel () {
        myChannel.disconnect();
        System.out.println("Shell channel has been disconnected...");
    }



    public void disconnectSession () throws IOException {
        mySession.disconnect();
        System.out.println("Session disconnected...");
    }
    
    /**
     * execute command on server
     *
     * @param command
     * @throws IOException
     * @throws com.jcraft.jsch.JSchException
     */
    public void executeCommand (String command) throws IOException, JSchException {
        PrintStream ps = new PrintStream(myChannel.getOutputStream());
        
        ps.println(command);
        
        ps.flush();
    }

}
