package cn.store.utils;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtils {

    public static void sendMail(String email,String emialMsg)throws Exception{

        Properties prop = new Properties();

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //设置发送人的帐号和密码
                return new PasswordAuthentication("admin@store.com","admin");
            }
        };

        Session session = Session.getInstance(prop,auth);

        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress("admin@store.com"));

        message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));

        message.setSubject("用户激活");


    }
}
