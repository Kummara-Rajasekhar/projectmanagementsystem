package com.Rajasekhar.service;


import org.springframework.stereotype.Service;

@Service

public class EmailServiceImpl implements EmailService{
    @Override
    public void serdEmailWithToken(String uesrEmail, String link) {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessagehelper(mimeMessage,"utf-8");
        String subjects="John Project Team Invitaion";
        String text="Click the link to join the project team: "+link;

        helper.setSubject(subject);
        helper.setText(text,true);
        helper.setTo(userEmail);
        try{
            javaMailSender.send(mimeMessage);
        }catch (MailSenderException e){
            throw new MailSenderException("Failed to send email");
        }
    }
}
