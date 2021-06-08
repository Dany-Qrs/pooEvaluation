package model;

import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

public class Email {
    @Getter
    private String fromEmail="daniela.cruzp@outlook.com";
    @Getter
    private String pass = "";
    @Getter @Setter
    private String toEmail;
}
