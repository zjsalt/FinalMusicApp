package tdtu.report.Repository;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class EmailSender {

    public static void sendConfirmationEmail(final String to, final String confirmationCode) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    // Set up mail server properties
                    Properties props = new Properties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", "smtp.your-email-provider.com");
                    props.put("mail.smtp.port", "587");

                    // Set up the session
                    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("your-email@gmail.com", "your-email-password");
                        }
                    });

                    // Create a default MimeMessage object
                    Message message = new MimeMessage(session);

                    // Set the sender and recipient addresses
                    message.setFrom(new InternetAddress("your-email@gmail.com"));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

                    // Set the email subject and text
                    message.setSubject("Confirmation Code");
                    message.setText("Your confirmation code is: " + confirmationCode);

                    // Log email server properties
                    Log.d("EmailSender", "Email Server Properties: " + props.toString());

                    // Send the email
                    Transport.send(message);

                    Log.d("EmailSender", "Email sent successfully");
                } catch (MessagingException e) {
                    Log.e("EmailSender", "Error sending email", e);
                }

                return null;
            }
        }.execute();
    }
}
