package model;
import lombok.Getter;
import lombok.Setter;

public class Student {
    @Getter @Setter
    private int id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private double grade;
}
