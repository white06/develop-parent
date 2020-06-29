package com.tdu.develop.resource.pojo;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author 志阳
 * @create 2019-12-06-13:44
 */
public class MailAuthenticator extends Authenticator {
    public static String USERNAME = "";
    public static String PASSWORD = "";

    public MailAuthenticator() {
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(USERNAME, PASSWORD);
    }
}
