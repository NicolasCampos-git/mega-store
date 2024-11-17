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

        if(marcaRepository.existsByNombre(marca.getNombre().trim()) == false){


            marca.setNombre(StringUtil.capitalizeFirstLetter(dto.getNombre().toLowerCase().trim()));
            marca.setDescripcion(dto.getDescripcion().toLowerCase().trim());
            marca.setEstaActivo(true);
            return registrarMarcaMapper.toDto(marcaRepository.save(marca));

        }else{
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
