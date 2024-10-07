package com.programacion_avanzada.mega_store.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.MarcaDto;
import com.programacion_avanzada.mega_store.Mapper.MarcaMapper;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Repository.MarcaRepository;

import ch.qos.logback.core.util.StringUtil;
import jakarta.persistence.EntityExistsException;

@Service
public class MarcaService implements IMarcaService{

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private MarcaMapper marcaMapper;

    /*
     * Metodo encargad de cargar una nueva marca,
     * verificando que la marca no este registrada previamente.
     */
    @Override
    public MarcaDto registrarMarca(MarcaDto dto) {
        
        Marca marca = marcaMapper.toEntity(dto);
        
        if(marcaRepository.existsByNombre(marca.getNombre()) == false){

            
            marca.setNombre(StringUtil.capitalizeFirstLetter(dto.getNombre().toLowerCase()));
            marca.setDescripcion(dto.getDescripcion().toLowerCase());
            marca.setEstaActivo(true);
            return marcaMapper.toDto(marcaRepository.save(marca));

        }else{
            throw new EntityExistsException("La marca ya existe");
        }
    }

    /*
     * Metodo encargado de listar las marcas,
     * verificanto si estan activas.
     */
    public List<MarcaDto> listar() {

        return marcaRepository.findAll().stream().filter(Marca::isEstaActivo).map(marca -> {
            MarcaDto dto = new MarcaDto();
            dto.setId(marca.getId());
            dto.setNombre(marca.getNombre());
            dto.setDescripcion(marca.getDescripcion());
            dto.setEstaActivo(true);
            return dto;
        }).collect(Collectors.toList());
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
    
}
