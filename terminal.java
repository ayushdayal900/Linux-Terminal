///////////////////////////////////////////////////////////////////////
////////////////////// IMPORTING LIBRARIES ///////////////////////////
///////////////////////////////////////////////////////////////////////

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;


import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;




////////////////////////////////////////////////////////////////////////////////
////////////////////////////// MAIN CLASS /////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
public class terminal extends JFrame implements ActionListener{

    static File f1;
//    static JFrame frame;
//    static JButton jb;
//    static JTextField jtf;
    static String c1;
    static String c2;
    static String c3;

    static JTextField textField1;
    static JTextField textField2;
    static JTextField textField3;


    static ArrayList<String> commandsList = new ArrayList<>();

    static Stack<ArrayList<String>> prev = new Stack<>();
    static Stack<ArrayList<String>> next = new Stack<>();

//    static Scanner sc = new Scanner(System.in);
    static String fixedPath = "/root ";
    static String addtnPath = "D:\\VS_code_rebuild-main\\clg\\FDS_PROJECT";
//    static String path = fixedPath + addtnPath;

    static JTextArea outputTextArea = new JTextArea(100, 100);
    static JScrollPane scrollPane = new JScrollPane(outputTextArea);




////////////////////////////////////////////////////////////////////////////////////
///////////////////////////// MAIN CONSTRUCTOR /////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////
terminal(){
        // CREATING A FRAME
        JFrame frame = new JFrame("$$...LINUX SHELL...$$");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        
        // CREATING A BUTTON
        JButton upButton = new JButton("UP");
        upButton.setBounds(100,100,20,20);
        frame.add(upButton);
        upButton.setVisible(true);

        JButton downButton = new JButton("DOWN");
        downButton.setBounds(100,200,20,20);
        frame.add(downButton);
        downButton.setVisible(true);


        // Create JTextFields
        textField1 = new JTextField("Main cmd", 20);
        textField2 = new JTextField("Src path", 20);
        textField3 = new JTextField("Dest path", 20);


        // Add JTextFields to JFrame
        frame.add(textField1);
        frame.add(textField2);
        frame.add(textField3);

        // ENTER BUTTON
        JButton enterButton = new JButton("Enter");
        frame.add(enterButton);

        // Create a JLabel to display the text
        JLabel outputLabel = new JLabel("Output :");
        frame.add(outputLabel);

        frame.add(scrollPane);

        // Add action listener to the button
        enterButton.addActionListener(e -> {
            
            ArrayList<String> cmd = getCommand(frame);

            c1 = cmd.get(0);
            c2 = cmd.get(1);
            c3 = cmd.get(2);

            ArrayList<String> prevArr = new ArrayList<>();
            prevArr.add(c1);
            prevArr.add(c2);
            prevArr.add(c3);
            prev.add(prevArr);

            switch (c1) {
                case "pwd" -> pwdFun();
                case "mkdir" -> mkdirFunc();
                case "touch" -> touchFunc();
                case "date" -> dateFunc();
                case "echo >" -> echo1Func();
                case "echo >>" -> echo2Func();
                case "rm" -> rmFunc();

//                case "rmdir"   : ;              break;
                case "cat" -> catFunc();
                default -> outputTextArea.append("\n Invalid command");
            }

});


///////////////////////// UP Button Functionality /////////////////////

    upButton.addActionListener(e -> {
      ArrayList<String> curr = new ArrayList<>();
      ArrayList<String> store ;

      store = prev.pop();
      curr.addAll(store);
      next.add(store);

      textField1.setText(curr.get(0));
      textField2.setText(curr.get(1));
      textField3.setText(curr.get(2));
      });


    KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (!prev.isEmpty()) {
                    ArrayList<String> curr = new ArrayList<>();
                    ArrayList<String> store = prev.pop();
                    curr.addAll(store);
                    next.add(store);

                    textField1.setText(curr.get(0));
                    textField2.setText(curr.get(1));
                    textField3.setText(curr.get(2));
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    };

    textField1.addKeyListener(keyListener);
    textField2.addKeyListener(keyListener);
    textField3.addKeyListener(keyListener);




///////////////////////// DOWN Button Functionality ////////////////
    downButton.addActionListener(e -> {
      ArrayList<String> curr = new ArrayList<>();
      ArrayList<String> store;

        store = next.pop();
        prev.add(store);
        curr.addAll(store);

      textField1.setText(curr.get(0));
      textField2.setText(curr.get(1));
      textField3.setText(curr.get(2));
      });

        frame.pack();
        frame.setVisible(true);
    }





//    KeyListener keyListener = new KeyListener() {
//        @Override
//        public void keyTyped(KeyEvent e) {}
//
//        @Override
//        public void keyPressed(KeyEvent e) {
//            if (e.getKeyCode() == KeyEvent.VK_UP) {
//                if (!prev.isEmpty()) {
//                    ArrayList<String> curr = new ArrayList<>();
//                    ArrayList<String> store = prev.pop();
//                    curr.addAll(store);
//                    next.add(store);
//
//                    textField1.setText(curr.get(0));
//                    textField2.setText(curr.get(1));
//                    textField3.setText(curr.get(2));
//                }
//            }
//        }
//
//        @Override
//        public void keyReleased(KeyEvent e) {}
//    };
//
//    textField1.addKeyListener(keyListener);
//    textField2.addKeyListener(keyListener);
//    textField3.addKeyListener(keyListener);
    

    // Function to retrieve input text from all JTextFields in a JFrame
    public static ArrayList<String> getCommand(JFrame frame) {


        ArrayList<String> inputTextList = new ArrayList<>();
        // Iterate through all components in the JFrame
        for (Component component : frame.getContentPane().getComponents()) {
            // Check if the component is a JTextField
            if (component instanceof JTextField textField) {
                // Cast the component to JTextField and retrieve its text
                // Add the text to the list
                inputTextList.add(textField.getText());
            }
        }

        // q.add(inputTextList.toString());
        return inputTextList;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////

    public static void pwdFun() {
        outputTextArea.append("\n"+fixedPath +" "+addtnPath+ " \n");
    }

    public static void mkdirFunc(){
        String name = c2+c3;
        f1 = new File(name);//FILENAME
        outputTextArea.append("\n if Directory created check? : "+f1.mkdir());
    }

    public static void touchFunc(){
        String name = c2+c3;
        f1 = new File(name);
        try {
            outputTextArea.append("\n if file created check? : "+f1.createNewFile());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    public static void dateFunc(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("\n yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        outputTextArea.append(dtf.format(now));
    }

    public static void echo1Func(){
        String s = c3;
        String p = c2;

        //--------------- Code to write in file --------------------------
        try {
            FileWriter fw = new FileWriter(p);
            fw.write(s);
            outputTextArea.append("\n wrote in file? : true");
            fw.close();
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }
    }

    public static void echo2Func(){
        String s = c3;
        try {
            Files.write(Paths.get(c2), s.getBytes(), StandardOpenOption.APPEND);
            outputTextArea.append("\n wrote in file? : true");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void rmFunc(){
        File del = new File(c2+c3);
        if(del.delete()){
            outputTextArea.append("\n successfully deleated the file "+del.getName());
        }
        else{
            outputTextArea.append("\n some problem occured while deleting the file");
        }
    }

    public static void catFunc(){
        File fr = new File(c2);
        try {
            Scanner sc = new Scanner(fr);
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                outputTextArea.append("\n"+line);
            }
            sc.close();
        } catch (FileNotFoundException e1) {
            throw new RuntimeException(e1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
    public static void command() {
    // ------------------ Creating command list ------------------->>
    
    // pwd: Print Working Directory - Shows the current directory you're in.
    commandsList.add("pwd");
    commandsList.add("echo >");
        commandsList.add("echo >>");
        // ls: List - Lists files and directories in the current directory.
        commandsList.add("ls");
        // cd: Change Directory - Allows you to change your current directory.
        commandsList.add("cd");
        // mkdir: Make Directory - Creates a new directory.
        commandsList.add("mkdir");
        // rmdir: Remove Directory - Deletes a directory (only if it's empty).
        commandsList.add("rmdir");
        // touch: Creates an empty file or updates the access and modification times of a file
        commandsList.add("touch");
        // rm: Remove - Deletes files or directories.
        commandsList.add("rm");
        // cp: Copy - Copies files or directories.
        commandsList.add("cp");
        // mv: Move - Moves or renames files or directories.
        commandsList.add("mv");
        // cat: Concatenate - Displays the contents of a file.
        commandsList.add("cat");
        // less/more: Allows you to view files page by page.
        commandsList.add("less");
        commandsList.add("more");
        // head: Displays the first few lines of a file.
        commandsList.add("head");
        // tail: Displays the last few lines of a file.
        commandsList.add("tail");
        // grep: Searches for a specific pattern in files.
        commandsList.add("grep");
        // chmod: Changes the permissions of a file or directory.
        commandsList.add("chmod");
        // chown: Changes the owner of a file or directory.
        commandsList.add("chown");
        // sudo: Allows a permitted user to execute a command as the superuser or
        // another user.
        commandsList.add("sudo");
        // passwd: Allows you to change your password.
        commandsList.add("passwd");
        // man: Displays the manual page for a command.
        commandsList.add("man");
        // history: Shows a list of previously executed commands.
        commandsList.add("history");
        // wget: Downloads files from the internet.
        commandsList.add("wget");
        // tar: Archives and extracts files.
        commandsList.add("tar");
        // ssh: Secure Shell - Allows you to securely connect to another computer.
        commandsList.add("ssh");
        // scp: Secure Copy - Copies files securely between computers.
        commandsList.add("scp");
        // df: Disk Free - Displays information about disk space usage.
        commandsList.add("df");
        // du: Disk Usage - Displays information about directory space usage.
        commandsList.add("du");
        // ps: Process Status - Displays information about active processes.
        commandsList.add("ps");
        // kill: Terminates processes.
        commandsList.add("kill");
        // top/htop: Shows system activity and resource usage.
        commandsList.add("top");
        commandsList.add("htop");
        // ifconfig/ip: Displays network interface information.
        commandsList.add("ip");
        commandsList.add("ifconfig");
        // ping: Tests network connectivity.
        commandsList.add("ping");
        // hostname: Displays or sets the system's hostname.
        commandsList.add("hostname");
        // shutdown/reboot: Shuts down or restarts the system.
        // shutdown/reboot: Shuts down or restarts the system.
        commandsList.add("shutdown");
        commandsList.add("reboot");
        // date: Displays or sets the system date and time.
        commandsList.add("date");

        // System.out.println(commandsList);
    }

}
