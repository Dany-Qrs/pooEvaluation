package controller;

import model.Student;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentController {
    private InputStream fileInput;
    private Workbook excel;
    private Sheet sheet;
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
            this.fileInput.close();
            this.excel.write(new FileOutputStream(this.fileName));
            this.message = "successfully created!";
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
            if(this.sheet.getRow(student.getId() -1) != null){
                Row row = this.sheet.getRow(student.getId()-1);
                row.getCell(0).setCellValue(student.getName());
            }else{
                Row row = this.sheet.createRow(student.getId() -1);
                row.createCell(0).setCellValue(student.getName());
            }
            this.fileInput.close();
            this.excel.write(new FileOutputStream(this.fileName));
            this.message = "Successfully modified!";
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
                }
                List<String> names = new ArrayList<>();
                int numRows = this.sheet.getLastRowNum();
                for (int i = 0; i <= numRows ; i++) {
                    if(this.sheet.getRow(i) != null){
                        names.add(this.sheet.getRow(i).getCell(0).toString());
                        this.sheet.removeRow(this.sheet.getRow(i));
                    }
                }
                for (int i = 0; i < names.size() ; i++) {
                    Row row = this.sheet.createRow(i);
                    row.createCell(0).setCellValue(names.get(i));
                }
                this.fileInput.close();
                this.excel.write(new FileOutputStream(this.fileName));
                this.message = "successfully removed!";
        }catch (IOException e) {
            this.message = "An error occurred: "+e.getMessage();
        }
        return this.message;
    }

    public void createFile() {
        File file = new File(this.fileName);
        boolean isExist = !file.exists();
        try {
            if(isExist){
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
}
