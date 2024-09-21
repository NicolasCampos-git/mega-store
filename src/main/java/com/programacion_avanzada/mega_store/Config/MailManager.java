package com.programacion_avanzada.mega_store.Config;


import java.util.UUID;

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

    // Método para generar un token único
    private String generarToken() {
        String tokenSimulado = UUID.randomUUID().toString();
        String link = "https://mega-store.com/confirmar-cuenta?token=" + tokenSimulado; 

        return link;
    }

    public void enviarCorreo(String email){

        String linkConfirmacion = generarToken();
        MimeMessage mensaje = javaMailSender.createMimeMessage();
        try{
            mensaje.setSubject("¡Bienvenido a Mega Store! ");
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);
            helper.setTo(email);
            helper.setText(
                "Gracias por registrarte en Mega Store! \n" +
                "Por favor, haz clic en el siguiente enlace para confirmar tu cuenta:" 
                + linkConfirmacion 
            );
            helper.setFrom("nicolascampos4899@gmail.com");
            javaMailSender.send(mensaje);


        }catch(MessagingException e){
            throw new RuntimeException(e);
        }
    }
}
