///////////////////////////////////////////////////////////////////////
////////////////////// IMPORTING LIBRARIES ///////////////////////////
///////////////////////////////////////////////////////////////////////

package FDS_PROJECT;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
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
    
    static boolean x;
    static File f1;
    static JFrame frame;
    static JButton jb;
    static JTextField jtf;
    static JTextArea outputTextArea;

    static String c1;
    static String c2;
    static String c3;

    static JTextField textField1;
    static JTextField textField2;
    static JTextField textField3;


    static ArrayList<String> inputTextList;
    static ArrayList<String> commandsList = new ArrayList<>();

    static Queue<String> q = new LinkedList<>();
    static Stack<ArrayList<String>> prev = new Stack<>();
    static Stack<ArrayList<String>> next = new Stack<>();
    
    static String cmd;
    static Scanner sc = new Scanner(System.in);
    static String fixedPath = "shrirang@learning_bird MINGW64 /d/VS_code_rebuild-main (main) :";
    static String addtnPath = "D:\\VS_code_rebuild-main\\clg\\FDS_PROJECT";
    static String path = fixedPath + addtnPath;

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

        // Create a JTextArea to display the OUTPUT
        JTextArea outputTextArea = new JTextArea(50, 50);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        frame.add(scrollPane);

        // Add action listener to the button
        enterButton.addActionListener(e -> {
            
            ArrayList<String> cmd = getInputTextFromJFrame(frame);
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_UP) {
                        outputTextArea.append("UP arrow key pressed");
                        x = true;                
                    }

                }
            });

            

            c1 = cmd.get(0);
            c2 = cmd.get(1);
            c3 = cmd.get(2);

            ArrayList<String> newArr = new ArrayList<>();
            newArr.add(c1);
            newArr.add(c2);
            newArr.add(c3);

            prev.add(newArr);

            if(x==true){
                outputTextArea.append("\n if file created check?"+ " \n"+f1.mkdir());              
                c1 = q.poll();                
            }
            

    ////////////////////////// pwd /////////////////////////////////////////
    if(c1.equals("pwd")){
        outputTextArea.append("\n"+fixedPath +" "+addtnPath+ " \n");
    }

    ////////////////////////// mkdir /////////////////////////////////////////
    else if(c1.equals("mkdir")){
                        
        addtnPath = cmd.get(1); //PATH
        f1 = new File(cmd.get(2));//FILENAME
        outputTextArea.append("\n if file created check?"+ " \n"+f1.mkdir());
    }

    //////////////////////// creating a file ////////////////////////////////
    else if(c1.equals("touch")){

        addtnPath = cmd.get(1); //PATH
        f1 = new File(cmd.get(2));//FILENAME
        try {
            outputTextArea.append("\n if file created check?"+ " \n"+f1.createNewFile());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    ////////////////////////// date /////////////////////////////////////////
    else if(c1.equals("date")){
                        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("\n yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        outputTextArea.append(dtf.format(now));            
    }else{
        outputTextArea.append("\n Invalid command");              
    }
});

    upButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      ArrayList<String> curr = new ArrayList<>();
      curr.addAll(prev.pop());
    
      textField1.setText(curr.get(0));
      textField2.setText(curr.get(1));
      textField3.setText(curr.get(2));
      }  
    });



  // Add key listener to the frame (or appropriate component)
//   frame.addKeyListener(keyListener);
  
        // Set JFrame size and visibility
        frame.pack();
        frame.setVisible(true);
    }


    //   // Key listener (assuming this should be active throughout)
    //   KeyAdapter keyListener = new KeyAdapter() {
    //     @Override
    //     public void keyPressed(KeyEvent e) {
    //       if (e.getKeyCode() == KeyEvent.VK_UP) {
    //         outputTextArea.append("UP arrow key pressed");
    //         // Access previous state from prev (add your functionality here)
    //         ArrayList<String> curr = new ArrayList<>();
    //         curr.addAll(prev.pop());
    //         textField1.setText(curr.get(0));
    //         textField2.setText(curr.get(1));
    //         textField3.setText(curr.get(2));
    //       }
    //     }
    //   };
      



    // -------------->> show by echo
    public static void echoShow(){

        System.out.println("Enter String to insert ");
        String s = sc.nextLine();

        System.out.println("Enter file path ");
        String p = sc.nextLine();



        //--------------- Code to write in file --------------------------
       try {
        FileWriter fw = new FileWriter(p);
        fw.write(s);
        fw.close();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

        
    }


    // Function to retrieve input text from all JTextFields in a JFrame
    public static ArrayList<String> getInputTextFromJFrame(JFrame frame) {


        ArrayList<String> inputTextList = new ArrayList<>();
        // Iterate through all components in the JFrame
        for (Component component : frame.getContentPane().getComponents()) {
            // Check if the component is a JTextField
            if (component instanceof JTextField) {
                // Cast the component to JTextField and retrieve its text
                JTextField textField = (JTextField) component;
                // Add the text to the list
                inputTextList.add(textField.getText());
            }
        }

        q.add(inputTextList.toString());
        return inputTextList;
    }
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
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
































// public class terminal extends JFrame implements ActionListener{

//     static JFrame frame;
//     static JButton jb;
//     static JTextField jtf;
//     static JTextArea outputTextArea;

//     static ArrayList<String> inputTextList;
//     static ArrayList<String> commandsList = new ArrayList<>();
    
//     static String cmd;
//     static Scanner sc = new Scanner(System.in);
//     static String fixedPath = "shrirang@learning_bird MINGW64 /d/VS_code_rebuild-main (main) :";
//     static String addtnPath = "D:\\VS_code_rebuild-main\\clg\\FDS_PROJECT";
//     static String path = fixedPath + addtnPath;


//     terminal(){

//         frame = new JFrame("$$...LINUX TERMINAL...$$");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setLayout(new FlowLayout());

//         // Create JTextFields
//         JTextField textField1 = new JTextField("Main Command", 20);
//         JTextField textField2 = new JTextField("Sourse Path", 20);
//         JTextField textField3 = new JTextField("Destination Path", 20);

//         // Add JTextFields to JFrame
//         frame.add(textField1);
//         frame.add(textField2);
//         frame.add(textField3);

//         // Add a button to trigger text retrieval
//         JButton enterButton = new JButton("Enter");
//         frame.add(enterButton);

//         // Create a JLabel to display the retrieved input text
//         JLabel outputLabel = new JLabel("Output :");
//         frame.add(outputLabel);

//         // Create a JTextArea to display the retrieved input text
//         outputTextArea = new JTextArea(15, 200);
//         JScrollPane scrollPane = new JScrollPane(outputTextArea);
//         frame.add(scrollPane);

//         // Add action listener to the button
//         enterButton.addActionListener(e -> {

//             // Call function to retrieve input text
//             inputTextList = getInputTextFromJFrame(frame);

//             // Clear previous text
//             outputTextArea.setText("");


//             switch (inputTextList.get(0)) {
//                 case "pwd"    : pwd();       break;
//                 // case "ls"     : ls();        break;
//                 // case "cd"     : cd();        break;
//                 // case "mkdir"  : mkdir();     break;
//                 // case "rmdir"  : rmdir();     break;
//                 // case "touch"  : touch();     break;
//                 // case "rm"     : rm();        break;
//                 // case "echo >" : echoShow();  break;
//                 // case "echo >>": echoAppend();break;
//                 // case "cat"    : cat();       break;
//                 // case "date"   : date();      break;
        
//                 // case "cp": cp(); break;
//                 // case "mv": mv(); break;
//                 // case "less": less(); break;
//                 // case "more": more(); break;
//                 // case "head": head(); break;
//                 // case "tail": tail(); break;
//                 // case "grep": grep(); break;
//                 // case "chmod": chmod(); break;
//                 // case "chown": chown(); break;
//                 // case "sudo": sudo(); break;
//                 // case "passwd": passwd(); break;
//                 // case "man": man(); break;
//                 // case "history": history(); break;
//                 // case "wget": wget(); break;
//                 // case "tar": tar(); break;
//                 // case "ssh": ssh(); break;
//                 // case "scp": scp(); break;
//                 // case "df": df(); break;
//                 // case "du": du(); break;
//                 // case "ps": ps(); break;
//                 // case "kill": kill(); break;
//                 // case "top": top(); break;
//                 // case "htop": htop(); break;
//                 // case "ip": ip(); break;
//                 // case "ifconfig": ifconfig(); break;
//                 // case "ping": ping(); break;
//                 // case "hostname": hostname(); break;
//                 // case "shutdown": shutdown(); break;
//                 // case "reboot": reboot(); break;
        
//                 default:    outputTextArea.setText("invalid command");
        
//             }
            
//         });

//         // Set JFrame size and visibility
//         frame.pack();
//         frame.setVisible(true);

        
//     }
    
//     @Override
//     public void actionPerformed(ActionEvent e) {
//         if(e.getSource()==jb){
//             // frame.repaint();
//         }
//     } 

    
//     //////////////////// getInputTextFromJFrame /////////////////
    
//     public static ArrayList<String> getInputTextFromJFrame(JFrame frame) {
//         // Iterate through all components in the JFrame
//         for (Component component : frame.getContentPane().getComponents()) {
//             // Check if the component is a JTextField
//             if (component instanceof JTextField) {
//                 // Cast the component to JTextField and retrieve its text
//                 JTextField textField = (JTextField) component;
//                 // Add the text to the list
//                 inputTextList.add(textField.getText());
//             }
//         }
//         return inputTextList;
//     }
    
    
//     //////////////////////////////////// mkdir ////////////////////////////////////
 
//     // public static void mkdir(){
       
//     //     String path = inputTextList.get(1);

//     //     // Instantiate the File class
//     //     File f1 = new File(path);
//     //     // Creating a folder using mkdir() method

//     //     boolean bool = f1.mkdir();
//     //     if (bool) {
//     //         System.out.println("Folder is created successfully");
//     //     } else {
//     //         System.out.println("Error Found!");
//     //     }
//     // }
    
// ////////////////////////////////////////// ls///////////////////////////////////

//     // ------------>> listing all files  ls function
//     // --------------- helper function
//     // static void RecursivePrint(File[] arr, int index, int level) {
//     //     // terminate condition
//     //     if (index == arr.length)
//     //         return;

//     //     // tabs for internal levels
//     //     for (int i = 0; i < level; i++)
//     //     outputTextArea.setText("\t");

//     //     // for files
//     //     if (arr[index].isFile())
//     //     outputTextArea.setText(arr[index].getName());

//     //     // for sub-directories
//     //     else if (arr[index].isDirectory()) {
//     //         outputTextArea.setText("[" + arr[index].getName()
//     //                 + "]");

//     //         // recursion for sub-directories
//     //         RecursivePrint(arr[index].listFiles(), 0,
//     //                 level + 1);
//     //     }

//     //     // recursion for main directory
//     //     RecursivePrint(arr, ++index, level);
//     // }

//     // // main ls function

//     // public static void ls() {
//     //     // ------------->> main code
//     //     addtnPath = inputTextList.get(1);
//     //     // File object
//     //     File maindir = new File(addtnPath);

//     //     if (maindir.exists() && maindir.isDirectory()) {

//     //         // array for files and sub-directories
//     //         // of directory pointed by maindir
//     //         File arr[] = maindir.listFiles();

//     //                 outputTextArea.setText(
//     //                 "************************************************************************************");
//     //                 outputTextArea.setText(
//     //                 "Files from main directory : " + maindir);
//     //                 outputTextArea.setText(
//     //                 "************************************************************************************");

//     //         // Calling recursive method
//     //         RecursivePrint(arr, 0, 0);
//     //     }
//     // }


    
    
   
//    //////////////// pwd command ///////////////////////////////////////////
//     // ------------>> show current directory
//     public static void pwd() {
//        String path = System.getProperty("user.dir");
//        outputTextArea.setText("Working Directory = " + path);
//     // outputTextArea.setText(".................................pwd.................................");
//    }
   
// //    ///////////// contains command /////////////////////////
// //    public static boolean containsCommand(String cmd) {
// //        return commandsList.contains(cmd);
// //    }
   
   
//    ///////////// getting commands from user ///////////////
// //    public static String getCommand() {
// //        System.out.println();
// //        jtf.setText(fixedPath);
// //        return sc.nextLine();
// //    }
   
   
   
//    ////////////////////// INITIALIZING COMMANDS //////////////////////////////
//    //------------------------------------------------------------------------------------------------------------
// //    public static void command() {
   
// //        // JTextField t1 = new JTextField();
// //        // StringBuilder stringBuilder = new StringBuilder();
       
// //        // ------------------ Creating command list ------------------->>
   
// //        // pwd: Print Working Directory - Shows the current directory you're in.
// //        commandsList.add("pwd");
// //        commandsList.add("echo >");
// //        commandsList.add("echo >>");
// //        // ls: List - Lists files and directories in the current directory.
// //        commandsList.add("ls");
// //        // cd: Change Directory - Allows you to change your current directory.
// //        commandsList.add("cd");
// //        // mkdir: Make Directory - Creates a new directory.
// //        commandsList.add("mkdir");
// //        // rmdir: Remove Directory - Deletes a directory (only if it's empty).
// //        commandsList.add("rmdir");
// //        // touch: Creates an empty file or updates the access and modification times of a file
// //        commandsList.add("touch");
// //        // rm: Remove - Deletes files or directories.
// //        commandsList.add("rm");
// //        // cp: Copy - Copies files or directories.
// //        commandsList.add("cp");
// //        // mv: Move - Moves or renames files or directories.
// //        commandsList.add("mv");
// //        // cat: Concatenate - Displays the contents of a file.
// //        commandsList.add("cat");
// //        // less/more: Allows you to view files page by page.
// //        commandsList.add("less");
// //        commandsList.add("more");
// //        // head: Displays the first few lines of a file.
// //        commandsList.add("head");
// //        // tail: Displays the last few lines of a file.
// //        commandsList.add("tail");
// //        // grep: Searches for a specific pattern in files.
// //        commandsList.add("grep");
// //        // chmod: Changes the permissions of a file or directory.
// //        commandsList.add("chmod");
// //        // chown: Changes the owner of a file or directory.
// //        commandsList.add("chown");
// //        // sudo: Allows a permitted user to execute a command as the superuser or
// //        // another user.
// //        commandsList.add("sudo");
// //        // passwd: Allows you to change your password.
// //        commandsList.add("passwd");
// //        // man: Displays the manual page for a command.
// //        commandsList.add("man");
// //        // history: Shows a list of previously executed commands.
// //        commandsList.add("history");
// //        // wget: Downloads files from the internet.
// //        commandsList.add("wget");
// //        // tar: Archives and extracts files.
// //        commandsList.add("tar");
// //        // ssh: Secure Shell - Allows you to securely connect to another computer.
// //        commandsList.add("ssh");
// //        // scp: Secure Copy - Copies files securely between computers.
// //        commandsList.add("scp");
// //        // df: Disk Free - Displays information about disk space usage.
// //        commandsList.add("df");
// //        // du: Disk Usage - Displays information about directory space usage.
// //        commandsList.add("du");
// //        // ps: Process Status - Displays information about active processes.
// //        commandsList.add("ps");
// //        // kill: Terminates processes.
// //        commandsList.add("kill");
// //        // top/htop: Shows system activity and resource usage.
// //        commandsList.add("top");
// //        commandsList.add("htop");
// //        // ifconfig/ip: Displays network interface information.
// //        commandsList.add("ip");
// //        commandsList.add("ifconfig");
// //        // ping: Tests network connectivity.
// //        commandsList.add("ping");
// //        // hostname: Displays or sets the system's hostname.
// //        commandsList.add("hostname");
// //        // shutdown/reboot: Shuts down or restarts the system.
// //        // shutdown/reboot: Shuts down or restarts the system.
// //        commandsList.add("shutdown");
// //        commandsList.add("reboot");
// //        // date: Displays or sets the system date and time.
// //        commandsList.add("date");
   
// //        //RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
// //        // t1.setText(commandsList);
// //        // for (String str : commandsList) {
// //        //     stringBuilder.append(str).append("\n"); // Append each string followed by a newline
// //        // }
// //        // String text = stringBuilder.toString();
// //        // Set the text of JTextArea
// //        // jtf.setText(text);
// //        // System.out.println(text);
// //        // RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR      
   
// //    }
//    }
   