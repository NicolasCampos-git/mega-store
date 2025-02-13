package com.programacion_avanzada.mega_store.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.ReclamoDtos.CambioEstadoReclamoDto;
import com.programacion_avanzada.mega_store.DTOs.ReclamoDtos.RegistrarReclamoDto;
import com.programacion_avanzada.mega_store.Modelos.Estado;
import com.programacion_avanzada.mega_store.Modelos.Reclamo;
import com.programacion_avanzada.mega_store.Repository.EstadoRepository;
import com.programacion_avanzada.mega_store.Repository.ReclamoRepository;
import com.programacion_avanzada.mega_store.Repository.TipoReclamoRepositoty;
import com.programacion_avanzada.mega_store.Service.Interfaces.IReclamoService;

@Service
public class ReclamoService implements IReclamoService{

    @Autowired
    private ReclamoRepository reclamoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private OrdenCompraService ordenCompraService;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private TipoReclamoRepositoty tipoReclamoRepositoty;



    @Override
    public Reclamo registrarReclamo(RegistrarReclamoDto dto) {
        validarTipoReclamo(dto.getIdTipoReclamo());
        validarUsuario(dto.getIdUsuario());
        validarOrdeCompra(dto.getIdOrdenCompra());

        Reclamo reclamo = new Reclamo();
        validarMorivo(dto.getMotivo());
        validarDescripcion(dto.getDescripcion());
        

        reclamo.setMotivo(dto.getMotivo().toLowerCase());
        reclamo.setDescripcion(dto.getDescripcion().toLowerCase());
        reclamo.setTipoReclamo(tipoReclamoRepositoty.findById(dto.getIdTipoReclamo()));
        reclamo.setUsuario(usuarioService.buscarPorId(dto.getIdUsuario()));
        reclamo.setOrdenCompra(ordenCompraService.obtenerOrdenPorId(dto.getIdOrdenCompra()));

        Estado estado = estadoRepository.findByNombre("Registrado")
        .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        validarEstado(estado);
        reclamo.setEstado(estado);

        reclamo.setFechaCreacion(LocalDateTime.now());

        return reclamoRepository.save(reclamo);
    }

    @Override
    public Reclamo buscarPorId(long id) {
        Reclamo reclamo = reclamoRepository.findById(id);
        if (reclamo == null) {
            throw new RuntimeException("reclamo no encontrado");
        }

        return reclamo;

    }

    @Override
    public List<Reclamo> listarReclamos() {
        return reclamoRepository.findAll();
    }

    @Override
    public void eliminarReclamo(long id) {
        Reclamo reclamo = buscarPorId(id);
        if(reclamo == null) {
            throw new RuntimeException("Reclamo no encontrado");

        }
        reclamo.setEstaActivo(false);

        reclamoRepository.save(reclamo);
    }

    @Override
    public void reactivarReclamo(long id) {
        Reclamo reclamo = buscarPorId(id);
        if(reclamo == null) {
            throw new RuntimeException("Reclamo no encontrado");
        }
        reclamo.setEstaActivo(true);

        reclamoRepository.save(reclamo);
    }
    
    private void validarEstado(Estado estado){

        if(estado.getId() < 1){
            throw new RuntimeException("El tipo de reclamo no puede ser menor a 1");
        }
        // if(estado.getAmbito() != "Reclamo"){
        //     throw new RuntimeException("El estado no pertenece a reclamo");
        // }
    }

    @Override
    public List<Reclamo> listarReclamoPorUsuario(long idUsuario) {
        return reclamoRepository.findByUsuarioId(idUsuario);
    }

    

    private void validarTipoReclamo(long idTipoReclamo) {
        if(!tipoReclamoRepositoty.existsById(idTipoReclamo)) {
            throw new RuntimeException("Tipo de reclamo no encontrado");
        }
        if (idTipoReclamo < 1) {
            throw new RuntimeException("El tipo de reclamo no puede ser menor a 1");
            
        }
    }

    private void validarUsuario(long idUsuario) {
        if(!usuarioService.validarIdUsuario(idUsuario)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        if (idUsuario < 1) {
            throw new RuntimeException("El id del usuario no puede ser menor a 1");
            
        }
    }

    private void validarOrdeCompra(long idOrdenCompra){
        if(!ordenCompraService.existeOrdenCompra(idOrdenCompra)) {
            throw new RuntimeException("Orden de compra no encontrada");
        }
        if (idOrdenCompra < 1) {
            throw new RuntimeException("El id de la orden de compra no puede ser menor a 1");
            
        }
    }

    private void validarMorivo(String motivo) {
        if(motivo == null || motivo.isEmpty()) {
            throw new RuntimeException("Motivo no puede estar vacio");
        }
        if(motivo.length() < 2 && motivo.length() > 50) {
            throw new RuntimeException("El motivo debe tener entre 2 y 50 caracteres");
        }
        if(!motivo.matches("[a-zA-Z ]+")) {
            throw new RuntimeException("El motivo solo debe tener letras y espacios");
        }
        
    }

    private void validarDescripcion(String descripcion) {
        if(descripcion == null || descripcion.isEmpty()) {
            throw new RuntimeException("Descripcion no puede estar vacia");
        }
        if(descripcion.length() < 2 && descripcion.length() > 255) {
            throw new RuntimeException("La descripcion debe tener entre 2 y 255 caracteres");
        }
    }

    

    

    //El reclamo solo deberia poder actualizarse si no esta "En revision"
    @Override
    public Reclamo actualizarReclamo(CambioEstadoReclamoDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarReclamo'");
    }

    @Override
    public Reclamo revisarReclamo(long idReclamo) {
        Reclamo reclamo = reclamoRepository.findById(idReclamo);
        if (reclamo == null) {
            throw new RuntimeException("Reclamo no encontrado");
        }
        if (!reclamo.getEstado().getNombre().equals("Registrado")) {
            throw new RuntimeException("Cambio de estado no valido");
        }

        Estado estadoEnRevision = estadoRepository.findByNombre("En Revision")
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        reclamo.setEstado(estadoEnRevision);
        reclamo.setFechaEnRevicion(LocalDateTime.now());

        return reclamoRepository.save(reclamo);
    }

    @Override
    public Reclamo aprobarReclamo(long idReclamo) {
        Reclamo reclamo = reclamoRepository.findById(idReclamo);
        if (reclamo == null) {
            throw new RuntimeException("Reclamo no encontrado");
        }
        if (!reclamo.getEstado().getNombre().equals("En Revision")) {
            throw new RuntimeException("Cambio de estado no valido");
        }

        Estado estadoAprobado = estadoRepository.findByNombre("Aprobado")
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        reclamo.setEstado(estadoAprobado);
        reclamo.setFechaAprobado(LocalDateTime.now());

        return reclamoRepository.save(reclamo);
    }

    @Override
    public Reclamo rechazarReclamo(long idReclamo) {
        Reclamo reclamo = buscarPorId(idReclamo);
        if (reclamo == null) {
            throw new RuntimeException("Reclamo no encontrado");
        }
        if (!reclamo.getEstado().getNombre().equals("En Revision")) {
            throw new RuntimeException("Cambio de estado no valido");
        }

        Estado estadoRechazado = estadoRepository.findByNombre("Rechazado")
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        reclamo.setEstado(estadoRechazado);
        reclamo.setFechaRechazado(LocalDateTime.now());

        return reclamoRepository.save(reclamo);
    }

    @Override
    public Reclamo resolverReclamo(long idReclamo) {
        Reclamo reclamo = buscarPorId(idReclamo);
        if (reclamo == null) {
            throw new RuntimeException("Reclamo no encontrado");
        }
        if (!reclamo.getEstado().getNombre().equals("Aprobado")) {
            throw new RuntimeException("Cambio de estado no valido");
        }

        Estado estadoResuelto = estadoRepository.findByNombre("Resuelto")
            .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        reclamo.setEstado(estadoResuelto);
        reclamo.setFechaResuelto(LocalDateTime.now());

        return reclamoRepository.save(reclamo);
    }

    

    

   
    


}
