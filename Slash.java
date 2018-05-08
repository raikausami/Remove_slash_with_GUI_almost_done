import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.io.*;
import java.net.*;

public class Slash extends JFrame implements ActionListener{

    JTextField text1 =null;
    JTextField text2 = null;
    JTextArea label;

    public static void main(String[] args){
        Slash m = new Slash("sample");
        m.setVisible(true);
    }

    Slash(String title){
        setBounds(100, 100, 300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        Container con = getContentPane();

        text1 = new JTextField("バクスラ消したい文章", 20);

        JButton button = new JButton("変換");

        button.setActionCommand("trans");
        button.addActionListener(this);
        label = new JTextArea();
        panel.add(text1);

        panel.add(button);
        panel.add(label);
        Container contentPane = getContentPane();
        contentPane.add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        /*if("trans".equals(e.getActionCommand())){
            boot_process();
            System.out.println("e");
            String str=rem_slash(text1.getText());
            System.out.println("f");
            String after=runSample(str);
            System.out.println(after);
            System.out.println("g");
            label.setText(null);
            label.append(after+"\n");
        }
        
        if("trans".equals(e.getActionCommand())){
            //boot_process();
            String str=rem_slash(text1.getText());
            System.out.println("flag1");
            String after=runSample(str);
            System.out.println("flag2");
            System.out.println(after);
            label.setText(null);
            label.append(after+"\n");
        }*/
        if("trans".equals(e.getActionCommand())){
            boot_process();
            String str=rem_slash(text1.getText());
            System.out.println("flag1");
            String after=runSample(str);
            System.out.println("flag2");
            System.out.println(after);
            label.setText(null);
            label.append(after+"\n");
        }


    }

   static void setClipboardString(String str) {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Clipboard clip = kit.getSystemClipboard();
		StringSelection ss = new StringSelection(str);
		clip.setContents(ss, ss);
	}


    static String rem_slash(String line){
        line.replace("\r\n"," ");
        line.replace("\n"," ");
        return line;
    }

    static String runSample(String message) {
        //StringBuffer sb = new StringBuffer("");
        StringBuilder buf = new StringBuilder();
        try{
            Socket socket = new Socket("localhost", 6001);
            System.out.println("e");
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            DataInputStream dis = new DataInputStream(is);
            DataOutputStream dos = new DataOutputStream(os);

            byte[] message_got= message.getBytes();
            //System.out.println(message_got);
            dos.write(message_got, 0, message_got.length);
            //System.out.println(message_got);
            //System.out.println(message_got.length);
            System.out.println("send");
            //char recv;
            //String recv = new String(); 
            while(true){
                byte[] b = new byte[1024]; 
                if(dis.read(b)==-1) break;
                //recv = (char)(dis.readByte()); 
                //String recv = new String(dis.readByte(),StandardChar);
                //recv = new String(dis.readByte(),"UTF-8");
                //String recv = new String(dis.readUTF());
                String recv = new String(b,"UTF-8");
                //if(recv == '\0') break; 
                //System.out.println(recv);
                buf.append(recv);
                // if(recv==null) break;

            }
            //System.out.println(buf);
            //System.out.println(recv);
            dis.close();
            dos.close();
            os.close();
            is.close();
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            System.out.println(new String(buf));
            return new String (buf);
        }
    }


    static void boot_process() {
        try{
            Runtime r = Runtime.getRuntime();
            Process process = r.exec("python gtranslate.py");
        }catch(IOException e){
        }
    }
}

