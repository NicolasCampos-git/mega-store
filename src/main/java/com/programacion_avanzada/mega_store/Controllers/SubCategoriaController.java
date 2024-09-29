package com.programacion_avanzada.mega_store.Controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDto;
import com.programacion_avanzada.mega_store.Mapper.SubCategoriaMapper;

import com.programacion_avanzada.mega_store.Repository.SubCategoriaRepository;
import com.programacion_avanzada.mega_store.Service.ISubCategoriaService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("subcategoria")
public class SubCategoriaController {

    @Autowired
    private ISubCategoriaService subCategoriaService;
    @Autowired
    private SubCategoriaMapper subCategoriaMapper;

    
    @PostMapping("/registrar/{id}")
    public SubCategoriaDto registrar(@PathVariable long id ,@RequestBody @Valid SubCategoriaDto dto) {
        return subCategoriaService.registrarSubCategoria(id, dto);
    }

    
    @GetMapping("/listar")
    public List<SubCategoriaDto> listar() {
        return subCategoriaService.listar();
    }

    
    @GetMapping("buscar/{id}")
    public SubCategoriaDto buscarPorId(@PathVariable long id) {
        return subCategoriaMapper.toDto(subCategoriaService.buscarPorId(id));
    }

    
    @PutMapping("/eliminar/{id}")
    public void desactivar(@PathVariable long id) {
         
        subCategoriaService.eliminar(id); 
    }
}

