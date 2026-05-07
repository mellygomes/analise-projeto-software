package com.jello.jello_app.utils;

public class EmailUtils {

    public static String getEmailMessage(String name, String host, String token) {
        return "Hello" + name + ",\n\n" +
                "Your new account has been created. " +
                "Please click on the link below to verify your account.\n\n" +
                getVerificationUrl(host, token) +
                "\n\nThe Support Team";
    }

    public static String getVerificationUrl(String host, String token) {
        return host + "/verify/account?token=" + token;
    }
}
