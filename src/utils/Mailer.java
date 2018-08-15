package utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mailer {
    private static final String username = "photostock.shop@gmail.com";
    private static final String password = "Photostocksmtp1";
    private static Properties props;

    private static final String activationMessage = "Hi there! Welcome to Photostock! You may activate your account by clicking on this link: ";
    private static final String requestedPassword = "Hi there! Your passsword is: ";

    public static boolean sendActivationMail(String activationUsername, String emailAddress){

        setupProps();

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(activationUsername));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emailAddress));
            message.setSubject("Photostock - activation mail");
            message.setText(activationMessage + "http://localhost:8080/Photostock/rest/user/activate/id=" + activationUsername);

            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean sendPassword(String c_email, String c_password){
        setupProps();
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(c_email));
            message.setSubject("Photostock - password request");
            message.setText(requestedPassword + c_password + "\n \n Log in here: http://localhost:8080/Photostock/login");

            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public static void setupProps(){
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }
}