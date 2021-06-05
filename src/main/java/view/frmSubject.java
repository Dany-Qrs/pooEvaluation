package view;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.Properties;

public class frmSubject {
    private JButton button5;
    private JButton button6;
    private JPanel jpPrincipal;
    private JButton button1;

    public frmSubject() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarExcel();
            }
        });
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMail();
            }
        });
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("frmSubject");
        frame.setContentPane(new frmSubject().jpPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    private void cargarExcel(){
        try {
            FileInputStream f = new FileInputStream("./ejemplo1.xlsx");
            Workbook w = WorkbookFactory.create(f);
            Sheet s = w.getSheet("Hoja1");
            int numRows = s.getLastRowNum();
            System.out.println(s.getRow(0).getCell(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMail(){
        //String name="daniela.cruzp@outlook.com";
        String name="daniela.cruz16@formacion.edu.sv";
        //String pass="da24NH11y";//
        String pass="A18m12y12!";//
        //String from = "daniela.cruzp@outlook.com";
        String from = "daniela.cruz16@formacion.edu.sv";
        //String toEmail = "goregrindcore@live.com";
        String toEmail = "goregrindcore95@gmail.com";
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        //prop.put("mail.smtp.host","smtp-mail.outlook.com");
        prop.put("mail.smtp.host","smtp.gmail.com");
        prop.put("mail.smtp.port","587");
        prop.put("mail.smtp.user",name);
        prop.put("mail.smtp.password", pass);
        Session sess = Session.getInstance(prop, new javax.mail.Authenticator(){
            protected  PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(name, pass);
            }
        });
        MimeMessage message = new MimeMessage(sess);
        try {
            message.setFrom(new InternetAddress(from));
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("SUBJECT");
            message.setText("hola, hola y hola y me ces mal");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
