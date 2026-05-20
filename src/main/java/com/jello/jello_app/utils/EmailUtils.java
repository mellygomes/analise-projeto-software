package com.jello.jello_app.utils;

public class EmailUtils {
    public static String getEmailMessage(String name, String host, String token) {
        return "Hello, @" + name + "\n\n" +
                "Your new account has been created! " +
                "Please, click on the link below to verify your account.\n\n" +
                getVerificationUrl(host, token) +
                "\n\nThe Support Team";
    }

    public static String getResetPasswordMessage(String name, String host, String token) {
        return "Hello" + name + ",\n\n" +
                "This is your link to reset password." +
                "If you did not do this request, disconsider this email!\n" +
                "Click on the link below to create a new password:\n\n" +
                getResetPasswordUrl(host, token) +
                "\n\nThe Support Team";
    }

    public static String getVerificationUrl(String host, String token) {
        return host + "/verify/account?token=" + token;
    }

    public static String getResetPasswordUrl(String host, String token) {
        return host + "/verify/password?token=" + token;
    }
}
