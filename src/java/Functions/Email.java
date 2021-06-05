package Functions;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {

    private static final String FROM = "";
    private static final String NAME = "Hotel Reservation System";
    private static final String PASSWORD = "";

    // Mail server
    private static final String HOST = "smtp.gmail.com";
    private static final String PORT = "465";
    private static final String CLASS = "javax.net.ssl.SSLSocketFactory";
    private static final String AUTH = "true";

    public static void send(String _to, String _subject, String _body) {
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", Email.HOST);
        properties.put("mail.smtp.socketFactory.port", Email.PORT);
        properties.put("mail.smtp.socketFactory.class", Email.CLASS);
        properties.put("mail.smtp.auth", Email.AUTH);
        properties.put("mail.smtp.port", Email.PORT);

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Email.FROM, Email.PASSWORD);
                    }
                }
        );

        try {

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(Email.FROM, Email.NAME));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(_to));

            message.setSubject(_subject);

            message.setContent(_body, "text/html");

            Transport.send(message);
        } catch (Exception _e) {
            _e.printStackTrace();
        }
    }
}
