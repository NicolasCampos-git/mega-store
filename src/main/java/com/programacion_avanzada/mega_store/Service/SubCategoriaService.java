package com.programacion_avanzada.mega_store.Service;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion_avanzada.mega_store.DTOs.RegistrarSubCategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDto;
import com.programacion_avanzada.mega_store.Mapper.RegistrarSubCategoriaMapper;
import com.programacion_avanzada.mega_store.Mapper.SubCategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import com.programacion_avanzada.mega_store.Repository.CategoriaRepository;
import com.programacion_avanzada.mega_store.Repository.SubCategoriaRepository;

import ch.qos.logback.core.util.StringUtil;
import jakarta.persistence.EntityExistsException;



@Service
public class SubCategoriaService implements ISubCategoriaService {

    @Autowired
    private SubCategoriaRepository subCategoriaRepository;

    @Autowired
    private SubCategoriaMapper subCategoriaMapper;

    @Autowired
    private RegistrarSubCategoriaMapper registrarSubCategoriaMapper;

    @Autowired
    private CategoriaRepository categoriaRepository;

    /*
     * Metodo encargado de registrar las subcategorias, 
     * verificando que no haya 2 con el mismo nombre y normalizando los datos.
     */
    @Override
    public RegistrarSubCategoriaDto registrarSubCategoria(RegistrarSubCategoriaDto dto) {
        // Primero, verifica si la categoría existe
        if (!categoriaRepository.existsById(dto.getCategoriaId())) {
            throw new EntityNotFoundException("La categoría no existe");
        }

        // Verifica si la subcategoría ya existe
        if (subCategoriaRepository.existsByNombre(dto.getNombre())) {
            throw new EntityExistsException("La subcategoria ya existe");
        }

        // Convertir el DTO a entidad
        SubCategoria subCategoria = registrarSubCategoriaMapper.toEntity(dto);

        // Normalizar los datos
        subCategoria.setNombre(StringUtil.capitalizeFirstLetter(dto.getNombre().toLowerCase().trim()));
        subCategoria.setDescripcion(dto.getDescripcion().toLowerCase().trim());

        // Obtener la categoría y asignarla a la subcategoría
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new EntityNotFoundException("La categoría no existe")); // Lanza excepción si no se encuentra la categoría
        subCategoria.setCategoria(categoria);
        subCategoria.setEstaActivo(true);

        // Guardar la subcategoría en el repositorio
        return registrarSubCategoriaMapper.toDto(subCategoriaRepository.save(subCategoria));
    }

    @Override
    public List<SubCategoriaDto> listar() {
        List<SubCategoria> subCategorias = subCategoriaRepository.findAll();
        return subCategorias.stream().map(subCategoriaMapper::toDto).toList();
    }

    @Override
    public SubCategoria buscarPorId(long id) {
        return subCategoriaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(long id) {
        SubCategoria subCategoria = subCategoriaRepository.findById(id).filter(SubCategoria::isEstaActivo).orElse(null);
        subCategoria.setEstaActivo(false);
        subCategoriaRepository.save(subCategoria);
    }

    @Override
    public void reactivar(long id){
        SubCategoria subCategoria = subCategoriaRepository.findById(id).orElse(null);
        if(subCategoria != null && subCategoria.isEstaActivo() == false){
            subCategoria.setEstaActivo(true);
            subCategoriaRepository.save(subCategoria);
        }
    }

    @Override
    public SubCategoriaDto actualizar(long id, SubCategoriaDto dto) {
        SubCategoria subcategoria = subCategoriaRepository.findById(id).orElse(null);
        
        // Aquí actualizamos los campos de la subcategoría
        subcategoria.setNombre(dto.getNombre());
        //subcategoria.setCategoria(dto.getCategoria());

        subCategoriaRepository.save(subcategoria);
        return subCategoriaMapper.toDto(subcategoria);
    }
}
