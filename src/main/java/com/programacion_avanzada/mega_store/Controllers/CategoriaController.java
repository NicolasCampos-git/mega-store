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

import com.programacion_avanzada.mega_store.DTOs.CategoriaDto;
import com.programacion_avanzada.mega_store.Mapper.CategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import com.programacion_avanzada.mega_store.Service.ICategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("categoria")
public class CategoriaController {


    @Autowired
    private ICategoriaService categoriaService;
    @Autowired
    private CategoriaMapper categoriaMapper;

    
    @PostMapping("/registrar")
    public CategoriaDto registrar(@RequestBody @Valid CategoriaDto dto) {
        return categoriaService.registrarCategoria(dto);
    }

    
    @GetMapping("/listar")
    public List<CategoriaDto> listar() {
        return categoriaService.listar();
    }

    
    @GetMapping("/{id}")
    public CategoriaDto buscarPorId(@PathVariable long id) {
        
        Categoria categoria = categoriaService.buscarPorId(id);

        return categoriaMapper.toDto(categoria);
   
    }

    
    @PutMapping("/eliminar/{id}")
    public void eliminar(@PathVariable long id) {
        
        categoriaService.eliminar(id); 
    }
}
