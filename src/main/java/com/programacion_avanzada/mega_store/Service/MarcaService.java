package com.programacion_avanzada.mega_store.Service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.MarcaDto;
import com.programacion_avanzada.mega_store.DTOs.RegistrarMarcaDto;
import com.programacion_avanzada.mega_store.Mapper.MarcaMapper;
import com.programacion_avanzada.mega_store.Mapper.RegistrarMarcaMapper;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Repository.MarcaRepository;

import ch.qos.logback.core.util.StringUtil;
import jakarta.persistence.EntityExistsException;

@Service
public class MarcaService implements IMarcaService{

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private RegistrarMarcaMapper registrarMarcaMapper;

    @Autowired
    private MarcaMapper marcaMapper;
    

    /*
     * Metodo encargad de cargar una nueva marca,
     * verificando que la marca no este registrada previamente.
     */
    @Override
    public RegistrarMarcaDto registrarMarca(RegistrarMarcaDto dto) {

        Marca marca = registrarMarcaMapper.toEntity(dto);

        
        if(marcaRepository.existsByNombre(marca.getNombre().trim()) == true){

            throw new EntityExistsException("La marca ya existe");
        }
        validarNombre(marca.getNombre());
        validarDescripcion(marca.getDescripcion());

        normalizarDatos(marca);
        marca.setEstaActivo(true);
            
        
        return registrarMarcaMapper.toDto(marcaRepository.save(marca));
    }


    /*
     * Metodo encargado de listar las marcas,
     * verificanto si estan activas.
     */
    public List<MarcaDto> listar() {

        List<Marca> marcas = marcaRepository.findAll();

        return marcas.stream().map(marcaMapper::toDto).toList();
    }


    /*
     * Metodo encargado de buscar marca por id,
     * verificando que este acitva.
     */
    @Override
    public Marca buscarPorId(long id) {
        return marcaRepository.findById(id).filter(Marca::isEstaActivo).orElse(null);
    }

    /*
     * Metodo encargado de eliminar la marca por id, 
     * verificando que no este desactivada previamente.
     */
    @Override
    public void eliminar(long id) {
        Marca marca = marcaRepository.findById(id).orElse(null);
        if(marca != null){
            marca.setEstaActivo(false);
            marcaRepository.save(marca);
        }
    }

    @Override
    public MarcaDto actualizar(long id, MarcaDto dto) {
        Marca marca = marcaRepository.findById(id).orElse(null);
        
        validarNombre(dto.getNombre());
        validarDescripcion(dto.getDescripcion());

        marca.setNombre(dto.getNombre());
        marca.setDescripcion(dto.getDescripcion());

        normalizarDatos(marca);

        marcaRepository.save(marca);
        return marcaMapper.toDto(marca);
    }

    @Override
    public MarcaDto reactivar(long id){
        Marca marca = marcaRepository.findById(id).orElse(null);
        if(marca.isEstaActivo() != false){
            throw new IllegalArgumentException("La marca ya esta activa.");
        }
        marca.setEstaActivo(true);
        //Aca deberia haber una excepcion si la marca esta activa.

        return marcaMapper.toDto(marcaRepository.save(marca));
    }

    public void validarNombre(String nombre){
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la marca no puede estar vacío.");
            
        }
        if (nombre.length() < 2 || nombre.length() > 64) {
            throw new IllegalArgumentException("El nombre de la marca debe tener entre 2 y 64 caracteres.");
        }
        if (nombre.contains(" ")) {
            throw new IllegalArgumentException("El nombre de la marca no debe contener espacios.");
            
        }
        if (nombre.matches(".*\\d.*")) {
            throw new IllegalArgumentException("El nombre no debe contener números.");
        }
    }

    public void validarDescripcion(String descripcion){
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("La descripcion de la marca no puede estar vacía.");
            
        }
        if (descripcion.length() < 2 || descripcion.length() > 64) {
            throw new IllegalArgumentException("La descripcion de la marca debe tener entre 2 y 64 caracteres.");
        }
        if (descripcion.matches(".*\\d.*")) {
            throw new IllegalArgumentException("La descripcion no debe contener números.");
        }
        
    }

    public Marca normalizarDatos(Marca marca){
        marca.setNombre(StringUtil.capitalizeFirstLetter(marca.getNombre().toLowerCase().trim()));
        marca.setDescripcion(marca.getDescripcion().toLowerCase().trim());
        return marca;
    }
    
}
