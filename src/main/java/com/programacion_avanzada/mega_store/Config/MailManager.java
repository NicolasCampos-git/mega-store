package com.programacion_avanzada.mega_store.Config;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Component
public class MailManager {
    
    JavaMailSender javaMailSender;

    

    public MailManager(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }



    public void enviarCorreo(String email){

        MimeMessage mensaje = javaMailSender.createMimeMessage();
        try{
            mensaje.setSubject("¡Bienvenido a Mega Store! ");
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);
            helper.setTo(email);
            helper.setText("Gracias por registrarte en Mega Store.");
            helper.setFrom("nicolascampos4899@gmail.com");
            javaMailSender.send(mensaje);


        }catch(MessagingException e){
            throw new RuntimeException(e);
        }
    }
}
