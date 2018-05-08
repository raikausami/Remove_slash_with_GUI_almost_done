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
import java.awt.Font;
import java.net.*;

public class Slash extends JFrame implements ActionListener{

    JTextField text1 =null;
    JTextArea text2;
    JTextArea label;
     JTextArea label2;

    public static void main(String[] args){
        Slash m = new Slash("sample");
        m.setVisible(true);
    }

    Slash(String title){
        int width = 1400;
        int height =1000;

        setBounds(500, 500, width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        Container con = getContentPane();

        text2 = new JTextArea("どうや");
        text2.setBounds(100, 100, 500, 500);
        text2.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
        panel.add(text2,BorderLayout.WEST);
        text2.setLineWrap(true);



        JButton button = new JButton("変換");

        button.setActionCommand("trans");
        button.addActionListener(this);
        label = new JTextArea();
        label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
        label.setBounds(600,600,500,500);
        label.setLineWrap(true);

        panel.add(button);
        panel.add(label,BorderLayout.EAST);
        Container contentPane = getContentPane();
        contentPane.add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){ 
        if("trans".equals(e.getActionCommand())){
            boot_process();
            String str=rem_slash(text2.getText());
            String after=runSample(str);
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
        StringBuilder buf = new StringBuilder();
        try{
            Socket socket = new Socket("localhost", 6001);
            System.out.println("e");
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            DataInputStream dis = new DataInputStream(is);
            DataOutputStream dos = new DataOutputStream(os);

            byte[] message_got= message.getBytes();
            dos.write(message_got, 0, message_got.length);
            System.out.println("send");
            while(true){
                byte[] b = new byte[1024]; 
                if(dis.read(b)==-1) break;
                String recv = new String(b,"UTF-8");
                buf.append(recv);
            }
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

