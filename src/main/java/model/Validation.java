package model;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private String regex;

    private boolean validate(String text){
        Pattern pattern = Pattern.compile(this.regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
    private String find(String line){
        Pattern pattern = Pattern.compile(this.regex);
        Matcher matcher = pattern.matcher(line);
        String found = "";
        while(matcher.find()){
            found = matcher.group();
        }
        return found;
    }
    public boolean validateName(String text){
        this.regex = "^([A-ZÁ-Ú][a-záéíóú]+)(\\s[A-ZÁ-Ú][a-záéíóú]+)*";
        return validate(text);
    }
    public boolean validateEnd(String text){
        this.regex = "STATISTICS";
        Pattern pattern = Pattern.compile(this.regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
    public boolean validateGrade(String text){
        this.regex = "^10(\\.(00|0))?|^[0-9](\\.[0-9]{1,2})?";
        return validate(text);
    }
    public boolean validateEmail(String text){
        this.regex = "^[\\w\\.\\-\\+]+@[a-z]+[a-z\\.]+[a-z]+";
        return validate(text);
    }
    public boolean toAsk(String text){
        //variable to return True if it matches regex
        Scanner scan = new Scanner(System.in);
        String exit;
        System.out.print("Do you want " + text +"? (y/yes) ");
        exit = scan.nextLine();
        Pattern pattern = Pattern.compile("^[Yy]([eE][sS])?|^[sS][iI]?");
        Matcher matcher = pattern.matcher(exit);
        return matcher.matches();
    }
    public String findName(String line){
        this.regex = "([A-ZÁ-Ú][a-záéíóú]+)(\\s[A-ZÁ-Ú][a-záéíóú]+)*";
        return find(line);
    }
    public double findGrade(String line){
        this.regex = "10(\\.(00|0))?|[0-9](\\.[0-9]{1,2})?";
        String found = find(line);
        return validateGrade(found)? Double.parseDouble(found): 0;
    }
    public String findEmail(String line){
        this.regex = "[\\w\\.\\-\\+]+@[a-z]+[a-z\\.]+[a-z]+";
        return find(line);
    }
}
