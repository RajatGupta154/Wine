package com.evozon.training.Util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class SendEmail {
    public static void sendMethod(String to, Integer id,String Uuid) {
        final String username = "truewolf10@gmail.com";
        final String password = "741852963marcusg";
        final String from = "truewolf10@gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        String sb = "<h2>Confirm Registration</h2>\n" +
                "<p><a href=\"http://localhost:8080/wine-1/register/";
        sb += id;
        sb += "\">Click it to confirm the registration. </a></p>";


//        String confirmationUrl = "http://localhost:8080" + "/wine-1/register.html?token=" + Uuid;
//
//        String sb = "<h2>Confirm Registration</h2>\n" +
//                "<p><a href=\"";
//        sb += confirmationUrl;
//        sb += "?id=";
//        sb += id;
//        sb += "\">Click it to confirm the registration. </a></p>";
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Testing Subject");
            message.setContent(sb,"text/html");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
