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
        // Validar el nombre de la marca
        if (dto.getNombre() == null || dto.getNombre().trim().length() < 2) {
            throw new EntityExistsException("El nombre de la marca debe tener al menos dos caracteres");
        }

        // Validar que el nombre de la marca no exceda los 64 caracteres
        if (dto.getNombre().length() > 64) {
            throw new IllegalArgumentException("El nombre de la marca no puede tener más de 64 caracteres");
        }

        if (dto.getNombre().contains(" ")) {
            throw new IllegalArgumentException("El nombre no puede contener espacios en blanco");
        }

        if (dto.getNombre().matches(".*\\d.*")) {
            throw new IllegalArgumentException("El nombre no puede contener caracteres numéricos");
        }

        if (dto.getDescripcion() != null) {
            if (dto.getDescripcion().length() > 100) {
                throw new IllegalArgumentException("La descripción no puede superar los 100 caracteres");
            }
            if (dto.getDescripcion().matches(".*\\d.*")) {
                throw new IllegalArgumentException("La descripción no puede contener caracteres numéricos");
            }
        }

        // Convertir el DTO a entidad
        Marca marca = registrarMarcaMapper.toEntity(dto);

        // Verificar si la marca ya existe
        if (!marcaRepository.existsByNombre(marca.getNombre().trim())) {
            // Configurar la marca
            marca.setNombre(StringUtil.capitalizeFirstLetter(dto.getNombre().toLowerCase().trim()));
            // Establecer descripción solo si está presente; de lo contrario, asignar null
            marca.setDescripcion(dto.getDescripcion() != null ? dto.getDescripcion().toLowerCase().trim() : null);
            marca.setEstaActivo(true);

            // Guardar la marca y devolver el DTO
            return registrarMarcaMapper.toDto(marcaRepository.save(marca));
        } else {
            throw new EntityExistsException("La marca ya existe");
        }

    }


    /*
     * Metodo encargado de listar las marcas,
     * verificanto si estan activas.
     */
    public List<MarcaDto> listar() {

        List<Marca> marcas = marcaRepository.findAllByEstaActivoIsTrue();

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
        Marca marca = marcaRepository.findById(id).filter(Marca::isEstaActivo).orElse(null);
        
        if(marca != null){
            marca.setEstaActivo(false);
            marcaRepository.save(marca);
        }
    }

    @Override
    public MarcaDto actualizar(long id, MarcaDto dto) {
        Marca marca = marcaRepository.findById(id).orElse(null);
        
        // Aquí actualizamos los campos de la marca
        marca.setNombre(dto.getNombre());
        marca.setDescripcion(dto.getDescripcion());

        marcaRepository.save(marca);
        return marcaMapper.toDto(marca);
    }

    @Override
    public MarcaDto reactivar(long id){
        Marca marca = marcaRepository.findById(id).orElse(null);
        if(marca.isEstaActivo() == false){
            marca.setEstaActivo(true);
        }
        //Aca deberia haber una excepcion si la marca esta activa.

        return marcaMapper.toDto(marcaRepository.save(marca));
    }
    
}
