package br.com.escola.email;

public interface EmailService {

 String sendSimpleMail(EmailDetails details);

 String sendMailWithAttachment(EmailDetails details);
}