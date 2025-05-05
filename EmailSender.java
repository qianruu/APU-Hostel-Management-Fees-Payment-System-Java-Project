
package apuhostelmanagementfeessystem;


import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.activation.*;

public class EmailSender {

    public static void sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); 
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        final String username = "hostel.management.apu@gmail.com";
        final String password = "iusn milg esog vthk"; 

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            JOptionPane.showMessageDialog(null,"Email sent successfully to " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
           JOptionPane.showMessageDialog(null,"Failed to send email to " + to);
        }
    }
}

