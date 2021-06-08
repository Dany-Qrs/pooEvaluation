package controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.Getter;
import model.Student;
import model.Subject;
import model.Validation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubjectController {
    @Getter
    private final Subject subject;
    private String message;

    public SubjectController(String name, String toEmail){
        subject = new Subject();
        subject.setName(name);
        subject.setToEmail(toEmail);
        createFile();
    }

    private void createFile(){
        File file1 = new File("./"+this.subject.getName()+".txt");
        if(!file1.exists()){
            try {
                file1.createNewFile();
            } catch (IOException e) {
                System.out.println("An error occurred: "+e.getMessage());
            }
        }
    }

    public List<Student> get(){
        List<Student> students = new ArrayList<>();
        try {
            Validation val = new Validation();
            Student student;
            File file = new File("./"+this.subject.getName()+".txt");
            Scanner scan= new Scanner(file);
            while(scan.hasNextLine()){
                String data = scan.nextLine();
                if(val.validateEnd(data)){
                    break;
                }else {
                    subject.setToEmail(val.findEmail(data));
                    student = new Student();
                    student.setName(val.findName(data));
                    student.setGrade(val.findGrade(data));
                    if (!student.getName().equals("") && student.getGrade() != 0) {
                        student.setId(students.size()+1);
                        students.add(student);
                    }
                }
            }
            subject.setStudents(students);
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: "+e.getMessage());
        }
        return students;
    }
    public String post(Student student){
        try {
            get();
            List<Student> students = subject.getStudents();
            student.setId(students.size()+1);
            students.add(student);
            subject.setStudents(students);
            writeFiles(writeData());
            get();
            this.message = "successfully created!";
        }catch (Exception e){
            this.message = "An error occurred: "+e.getMessage();
        }
        return message;
    }
    public String put(Student student){
        try {
            get();
            List<Student> students = subject.getStudents();
            students.set(student.getId()-1, student);
            subject.setStudents(students);
            writeFiles(writeData());
            get();
            this.message = "Successfully modified!";
        }catch (Exception e){
            this.message = "An error occurred: "+e.getMessage();
        }
        return message;
    }
    public String delete(Student student){
        try {
            get();
            List<Student> students = subject.getStudents();
            students.remove(student.getId()-1);
            subject.setStudents(students);
            writeFiles(writeData());
            get();
            this.message = "successfully removed!";
        }catch (Exception e){
            this.message = "An error occurred: "+e.getMessage();
        }
        return message;
    }
    public StringBuilder writeData(){
        StringBuilder builder = new StringBuilder();
        try {
            builder.append(subject.getToEmail()).append("\n\n");
            builder.append("STUDENTS AND GRADES\n");
            for(Student st: subject.getStudents()){
                builder.append(st.getId()).append("- ").append(st.getName()).append("\t").append(st.getGrade()).append("\n");
            }
            builder.append("\n\nSTATISTICS\n\n");
            builder.append("Average: ").append(String.format("%.2f", subject.getAverage())).append("\n");
            builder.append("Min grade: ").append(subject.getMinGrade()).append("\n");
            builder.append("Max grade: ").append(subject.getMaxGrade()).append("\n");
            builder.append("Most repeated: ").append(subject.getMostRePeat()).append("\n");
            builder.append("Less repeated: ").append(subject.getLessRePeat()).append("\n");
            builder.append("\n\t\tLIST STUDENTS WITH MIN GRADE\n");
            for(Student st: subject.getMinGradesStudents()){
                builder.append("\t\t").append(st.getName()).append("\t").append(st.getGrade()).append("\n");
            }
            builder.append("\n\t\tLIST STUDENTS WITH MAX GRADE\n");
            for(Student st: subject.getMaxGradesStudents()){
                builder.append("\t\t").append(st.getName()).append("\t").append(st.getGrade()).append("\n");
            }
            builder.append("\n\t\tLIST STUDENTS WITH MOST REPEATED GRADE\n");
            for(Student st: subject.getMostRepeatStudents()){
                builder.append("\t\t").append(st.getName()).append("\t").append(st.getGrade()).append("\n");
            }
            builder.append("\n\t\tLIST STUDENTS WITH LESS REPEATED GRADE\n");
            for(Student st: subject.getLessRepeatStudents()){
                builder.append("\t\t").append(st.getName()).append("\t").append(st.getGrade()).append("\n");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: "+e.getMessage());
        }
        return builder;
    }
    private void writeDataPdf(Document doc){
        doc.add(new Paragraph(subject.getToEmail()));
        doc.add(new Paragraph(""));
        doc.add(new Paragraph("STUDENTS AND GRADES"));
        for(Student st: subject.getStudents()){
            doc.add(new Paragraph(st.getId()+"- "+st.getName()+" "+st.getGrade()));
        }
        doc.add(new Paragraph(""));
        doc.add(new Paragraph(""));
        doc.add(new Paragraph("STATISTICS"));
        doc.add(new Paragraph("Average: "+String.format("%.2f", subject.getAverage())));
        doc.add(new Paragraph("Min grade: "+subject.getMinGrade()));
        doc.add(new Paragraph("Max grade: "+subject.getMaxGrade()));
        doc.add(new Paragraph("Most repeated: "+subject.getMostRePeat()));
        doc.add(new Paragraph("less repeated: "+subject.getLessRePeat()));
        doc.add(new Paragraph(""));
        doc.add(new Paragraph("LIST STUDENTS WITH MIN GRADE"));
        for(Student st: subject.getMinGradesStudents()){
            doc.add(new Paragraph(st.getName()+" "+st.getGrade()));
        }
        doc.add(new Paragraph(""));
        doc.add(new Paragraph("LIST STUDENTS WITH MAX GRADE"));
        for(Student st: subject.getMaxGradesStudents()){
            doc.add(new Paragraph(st.getName()+" "+st.getGrade()));
        }
        doc.add(new Paragraph(""));
        doc.add(new Paragraph("LIST STUDENTS WITH MOST REPEATED GRADE"));
        for(Student st: subject.getMostRepeatStudents()){
            doc.add(new Paragraph(st.getName()+" "+st.getGrade()));
        }
        doc.add(new Paragraph(""));
        doc.add(new Paragraph("LIST STUDENTS WITH LESS REPEATED GRADE"));
        for(Student st: subject.getLessRepeatStudents()){
            doc.add(new Paragraph(st.getName()+" "+st.getGrade()));
        }
    }
    private void writeFiles(StringBuilder builder){
        try{
            FileWriter file = new FileWriter("./"+this.subject.getName()+".txt");
            file.write(builder.toString());
            file.close();

            PdfWriter writer = new PdfWriter("./"+this.subject.getName()+".pdf");
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf, pdf.getDefaultPageSize());
            writeDataPdf(doc);
            doc.close();
        } catch (IOException e) {
        System.out.println("An error occurred: "+e.getMessage());
        }
    }
}
