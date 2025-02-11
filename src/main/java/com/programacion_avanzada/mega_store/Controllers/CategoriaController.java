package com.programacion_avanzada.mega_store.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacion_avanzada.mega_store.DTOs.CategoriaDtos.CategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.CategoriaDtos.RegistrarCategoriaDto;
import com.programacion_avanzada.mega_store.Mapper.CategoriaMappers.CategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.Categoria;
import com.programacion_avanzada.mega_store.Service.Interfaces.ICategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/categorias")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Categoria", description = "API de categorias")
public class CategoriaController {


    @Autowired
    private ICategoriaService categoriaService;
    @Autowired
    private CategoriaMapper categoriaMapper;

    @Operation(summary = "Registrar una categoria", description = "Permite registrar una categoria en la base de datos")
    @PostMapping("/registrar")
    public RegistrarCategoriaDto registrar(@RequestBody @Valid RegistrarCategoriaDto dto) {
        return categoriaService.registrarCategoria(dto);
    }

    @Operation(summary = "Listar categorias", description = "Permite listar todas las categorias registradas en la base de datos")
    @GetMapping("/listar")
    public List<CategoriaDto> listar() {
        return categoriaService.listar();
    }

    @Operation(summary = "Buscar categoria por ID", description = "Permite buscar una categoria por su ID")
    @GetMapping("/{id}")
    public CategoriaDto buscarPorId(@PathVariable long id) {
        
        Categoria categoria = categoriaService.buscarPorId(id);

        return categoriaMapper.toDto(categoria);
   
    }

    @Operation(summary = "Eliminar categoria por ID", description = "Permite eliminar una categoria por su ID de forma logica")
    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable("id") long id) {
        
        categoriaService.eliminar(id); 
    }

    @Operation(summary = "Actualizar categoria", description = "Permite actualizar una categoria por su ID")
    @PutMapping("/actualizar/{id}")
    public CategoriaDto actualizar(@PathVariable long id, @RequestBody @Valid CategoriaDto dto) {
        return categoriaService.actualizar(id, dto);
    }

    @Operation(summary = "Reactivar categoria", description = "Permite reactivar una categoria por su ID")
    @PutMapping("/reactivar/{id}")
    public void reactivar(@PathVariable("id") long id){
        categoriaService.reactivar(id);
    }
}
