package view;

import controller.EmailController;
import controller.StudentController;
import controller.SubjectController;
import model.Email;
import model.Student;
import model.Validation;

import java.util.List;
import java.util.Scanner;

public class EnrollStudents {
    private static final Scanner scan = new Scanner(System.in);
    private static final Validation val = new Validation();
    public static void clearScreen(){
        //loop for print spaces
        for(int i=0; i<50; i++){
            System.out.println();
        }
    }
    public static String chooseSubject(){
        String option;
        System.out.println("To choose mathematica press 1");
        System.out.println("To choose language press 2");
        System.out.println("To choose english press 3");
        System.out.println("To choose science press 4");
        System.out.print("option: ");
        option = scan.nextLine();
        return option;
    }
    public static void sendMail(String op){
        String subject = "";
        String email = "";
        switch (op){
            case "1":
                subject = "mathematica";
                email = "daniela18122012@gmail.com";
                break;
            case "2":
                subject = "language";
                email = "daniela18122012@gmail.com";
                break;
            case "3":
                subject = "english";
                email = "daniela18122012@gmail.com";
                break;
            case "4":
                subject = "science";
                email = "daniela18122012@gmail.com";
                break;
        }
        if(!subject.equals("") && !email.equals("")){
            SubjectController controller = new SubjectController(subject, email);
            controller.get();
            Email mail = new Email();
            mail.setToEmail("daniela18122012@gmail.com");
            EmailController eController = new EmailController();
            String message = eController.sendEmail(mail,controller.getSubject().getName());
            System.out.println(message);
        }
    }
    public static void saveStudent(){
        Student student;
        StudentController sController = new StudentController();
        while(true){
            System.out.print("New student: ");
            String name= scan.nextLine();
            if(val.validateName(name)){
                student = new Student();
                student.setName(name);
                String message = sController.post(student);
                System.out.println(message);
            }else{
                System.out.println("Try again!");
            }
            if(val.toAsk("to exit the submenu")){
                break;
            }
        }
        List<Student> students =  sController.get();
        for(Student st : students){
            System.out.println(st.getId()+"- "+st.getName());
        }
    }
    public static void printStatistics(String op){
        String subject = "";
        switch (op){
            case "1" -> subject = "mathematica";
            case "2"-> subject = "language";
            case "3"-> subject = "english";
            case "4"-> subject =  "science";
        }
        if(!subject.equals("")){
            SubjectController controller = new SubjectController(subject, "daniela18122012@gmail.com");
            controller.get();
            System.out.println(controller.writeData());
        }
    }
    public static void registerToSubject(String op){
        String subject;
        switch (op){
            case "1" -> subject = "mathematica";
            case "2"-> subject = "language";
            case "3"-> subject = "english";
            case "4"-> subject =  "science";
            default -> subject =  "";
        }
        if(!subject.equals("")){
            while(true){
                String option;
                System.out.println("To save press 1");
                System.out.println("To delete press 2");
                System.out.print("option: ");
                option = scan.nextLine();
                SubjectController sController = new SubjectController(subject, "daniela18122012@gmail.com");
                Student student;
                List<Student> students = sController.get();
                for(Student st: students){
                    System.out.println(st.getId()+"- "+st.getName()+" "+st.getGrade());
                }
                switch (option){
                    case "1":
                        StudentController stController = new StudentController();
                        List<Student> data = stController.get();
                        for(Student st: data){
                            if(val.toAsk("to save to "+st.getName()+" in "+subject)){
                               System.out.print("Grade: ");
                               String score = scan.nextLine();
                               if(val.validateGrade(score)){
                                   student = new Student();
                                   student.setGrade(Double.parseDouble(score));
                                   student.setName(st.getName());
                                   String message = sController.post(student);
                                   System.out.println(message);
                               }
                            }
                        }
                        students = sController.get();
                        for(Student st: students){
                            System.out.println(st.getId()+"- "+st.getName()+" "+st.getGrade());
                        }
                        break;
                    case "2":
                        System.out.print("What number will you eliminate? ");
                        int num = scan.nextInt();
                        student = new Student();
                        student.setId(num);
                        sController.delete(student);
                        students = sController.get();
                        for(Student st: students){
                            System.out.println(st.getId()+"- "+st.getName()+" "+st.getGrade());
                        }
                    break;
                }
                if(val.toAsk("to exit the submenu")){
                    break;
                }
                if(val.toAsk("to clean the screen")){
                    clearScreen();
                }
            }
        }
    }
    public static String chooseOption(){
        String option;
        System.out.println("GRADE EVALUATION");
        System.out.println("CHOOSE A OPTION");
        System.out.println("To save new student press 1");
        System.out.println("To print statistics press 2");
        System.out.println("To register students for the subject press 3");
        System.out.println("To send mail with reports 4");
        System.out.println("To exit the menu press 5");
        System.out.print("option: ");
        option = scan.nextLine();
        return option;
    }
    public static void main(String[] args){
        String option;
        while(true){
            option = chooseOption();
            clearScreen();
            switch (option){
                //fill the variable for print data
                case "1" :
                    saveStudent();
                    break;
                case "2" :
                    printStatistics(chooseSubject());
                    break;
                case "3" :
                    registerToSubject(chooseSubject());
                    break;
                case "4" :
                    sendMail(chooseSubject());
                    break;
                case "5" : if(val.toAsk("to exit the program")) System.exit(0);
                    break;
                default : System.out.println("The option is incorrect");
                    break;
            }
            if(val.toAsk("to clean the screen")){
                clearScreen();
            }
        }
    }
}
