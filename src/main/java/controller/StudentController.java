package controller;

import model.Student;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentController {
    private InputStream fileInput;
    private XSSFWorkbook excel;
    private XSSFSheet sheet;
    private String message;
    private final String fileName = "./dataBase.xlsx";

    public StudentController(){
        createFile();
    }

    public List<Student> get(){
        List<Student> students = new ArrayList<>();
        try {
            this.fileInput = new FileInputStream(this.fileName);
            this.excel = new XSSFWorkbook(this.fileInput);
            this.sheet = this.excel.getSheetAt(0);
            int numRows = this.sheet.getLastRowNum();
            for(int i = 0; i <= numRows; i++){
                if(this.sheet.getRow(i) != null){
                    Student student = new Student();
                    student.setName(this.sheet.getRow(i).getCell(0).toString());
                    student.setId(i+1);
                    students.add(student);
                }
            }
            this.fileInput.close();
        }catch (IOException e) {
            System.out.println("An error occurred: "+e.getMessage());
        }
        return students;
    }

    public String post(Student student){
        try {
            this.fileInput = new FileInputStream(this.fileName);
            this.excel = new XSSFWorkbook(this.fileInput);
            this.sheet = this.excel.getSheetAt(0);
            int numRows = this.sheet.getLastRowNum()+1;
            Row row = this.sheet.createRow(numRows);
            row.createCell(0).setCellValue(student.getName());
            this.message = "successfully created!";
            arrange(this.sheet);
            this.fileInput.close();
            this.excel.write(new FileOutputStream(this.fileName));
        }catch (IOException e) {
            this.message = "An error occurred: "+e.getMessage();
        }
        return this.message;
    }

    public String put(Student student){
        try {
            this.fileInput = new FileInputStream(this.fileName);
            this.excel = new XSSFWorkbook(this.fileInput);
            this.sheet = this.excel.getSheetAt(0);
            Row row;
            if(this.sheet.getRow(student.getId() -1) != null){
                row = this.sheet.getRow(student.getId()-1);
                this.message = "Successfully modified!";
            }else{
                row = this.sheet.createRow(student.getId() -1);
                this.message = "It doesn't exist, but it was created successfully!";
            }
            row.createCell(0).setCellValue(student.getName());
            arrange(this.sheet);
            this.fileInput.close();
            this.excel.write(new FileOutputStream(this.fileName));
        }catch (IOException e) {
            this.message = "An error occurred: "+e.getMessage();
        }
        return this.message;
    }

    public String delete(int id){
        try {
                this.fileInput = new FileInputStream(this.fileName);
                this.excel = new XSSFWorkbook(this.fileInput);
                this.sheet = this.excel.getSheetAt(0);
                if(this.sheet.getRow(id-1) != null){
                    this.sheet.removeRow(this.sheet.getRow(id-1));
                    this.message = "successfully removed!";
                }
                arrange(this.sheet);
                this.fileInput.close();
                this.excel.write(new FileOutputStream(this.fileName));
        }catch (IOException e) {
            this.message = "An error occurred: "+e.getMessage();
        }
        return this.message;
    }

    private void createFile() {
        try {
            File file = new File(this.fileName);
            if(!file.exists()){
                XSSFWorkbook excel = new XSSFWorkbook();
                XSSFSheet sheet = excel.createSheet("sheet 1");
                Row row = sheet.createRow(0);
                row.createCell(0).setCellValue("Name Example");
                FileOutputStream fileOut = new FileOutputStream(this.fileName);
                excel.write(fileOut);
                fileOut.close();
            }
        }catch (IOException e) {
            System.out.println("An error occurred: "+e.getMessage());
        }
    }
    private void arrange(XSSFSheet sheet){
        List<String> names = new ArrayList<>();
        int numRows = sheet.getLastRowNum();
        for (int i = 0; i <= numRows ; i++) {
            if(sheet.getRow(i) != null){
                names.add(sheet.getRow(i).getCell(0).toString());
                sheet.removeRow(sheet.getRow(i));
            }
        }
        for (int i = 0; i < names.size() ; i++) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(names.get(i));
        }
    }
}
