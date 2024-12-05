package com.programacion_avanzada.mega_store.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.Modelos.Producto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class SenderService implements ISenderService {
    
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    IProductoService productoService;


    /*
     * Metodo encargado de enviar el correo electronico al nuevo usuario.
     * Usamos un hack para generar el token, ya que no implementamos JWT.
     */
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

    @Override
    public void notificarBajoStock(long idProducto) {
        MimeMessage mensaje = javaMailSender.createMimeMessage();
        Producto producto = productoService.buscarPorId(idProducto);

        try{
            mensaje.setSubject("¡Stock bajo! ");
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);
            helper.setTo("nicolascampos4899@gmail.com");
            helper.setText(
                "El stock del producto:" +
                producto.getId() +"\n "+
                producto.getNombre() +"\n "+
                producto.getTamano() +"\n "+
                producto.getColor()+"\n "+
                producto.getMarca().getNombre() +"\n "+
                producto.getSubcategoria().getNombre()+"\n "+
                producto.getSubcategoria().getCategoria().getNombre() +"\n "+
                " está bajo"
            );
            helper.setFrom("nicolascampos4899@gmail.com");
            javaMailSender.send(mensaje);


        }catch(MessagingException e){
            throw new RuntimeException(e);
        }

    }

    // Metodo encargado de simular el token.
    private String generarToken() {
        String tokenSimulado = UUID.randomUUID().toString();
        String link = "https://mega-store.com/confirmar-cuenta?token=" + tokenSimulado; 

        return link;
    }

}
