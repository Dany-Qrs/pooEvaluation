package model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Subject {
    @Getter
    private final String fromEmail = "daniela.cruzp@outlook.com";
    @Getter @Setter
    private String toEmail ="";
    @Getter @Setter
    private String name;
    @Getter @Setter
    private List<Student> students = new ArrayList<>();
    private Map<Double, Integer> mostRepeatGrade;
    private Map<Double, Integer> lessRepeatGrade;
    private Map<Double, Integer> repeatGrade;
    @Getter
    private List<Student> minGradesStudents;
    @Getter
    private List<Student> maxGradesStudents;
    @Getter
    private List<Student> mostRepeatStudents;
    @Getter
    private List<Student> lessRepeatStudents;

    public double getAverage(){
        return this.getStudents().stream().mapToDouble(Student::getGrade).average().orElse(0.0);
    }
    public double getMaxGrade(){
        this.maxGradesStudents = new ArrayList<>();
        double maxGrade = this.getStudents().stream().mapToDouble(Student::getGrade).max().orElse(0.0);
        for(Student st: this.students){
            if(maxGrade == st.getGrade()){
                this.maxGradesStudents.add(st);
            }
        }
        return maxGrade;
    }
    public double getMinGrade(){
        this.minGradesStudents = new ArrayList<>();
        double minGrade = this.getStudents().stream().mapToDouble(Student::getGrade).min().orElse(0.0);
        for(Student st: this.students){
            if(minGrade == st.getGrade()){
                this.minGradesStudents.add(st);
            }
        }
        return minGrade;
    }
    public Map<Double, Integer> getMostRePeat(){
        this.mostRepeatGrade = new HashMap<>();
        this.mostRepeatStudents = new ArrayList<>();
        getRePeat();
        if(!this.repeatGrade.isEmpty()){
            int max = this.repeatGrade.values().stream().mapToInt(val -> val).max().orElse(0);
            this.repeatGrade.forEach((key, value)->{
                if(value == max){
                    this.mostRepeatGrade.put(key, value);
                    students.forEach(val->{
                        if(key == val.getGrade()){
                            mostRepeatStudents.add(val);
                        }
                    });
                }
            });
        }
        return this.mostRepeatGrade;
    }
    public Map<Double, Integer> getLessRePeat(){
        this.lessRepeatGrade = new HashMap<>();
        this.lessRepeatStudents = new ArrayList<>();
        getRePeat();
        if(!this.repeatGrade.isEmpty()){
            int min = this.repeatGrade.values().stream().mapToInt(val -> val).min().orElse(0);
            this.repeatGrade.forEach((key, value)->{
                if(value == min){
                    this.lessRepeatGrade.put(key, value);
                    students.forEach(val->{
                        if(key == val.getGrade()){
                            this.lessRepeatStudents.add(val);
                        }
                    });
                }
            });
        }
        return this.lessRepeatGrade;
    }
    private void getRePeat(){
        this.repeatGrade = new HashMap<>();
        this.students.forEach(val-> this.repeatGrade.merge(val.getGrade(), 1, Integer::sum));
    }
}
