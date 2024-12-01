
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

import com.programacion_avanzada.mega_store.DTOs.RegistrarSubCategoriaDto;
import com.programacion_avanzada.mega_store.DTOs.SubCategoriaDTO;
import com.programacion_avanzada.mega_store.Mapper.SubCategoriaMapper;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import com.programacion_avanzada.mega_store.Service.ISubCategoriaService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("api/subcategorias")
@CrossOrigin(origins = "http://localhost:5173") 
public class SubCategoriaController {

    @Autowired
    private ISubCategoriaService subCategoriaService;


    
    @PostMapping("/registrar")
    public RegistrarSubCategoriaDto registrar(@RequestBody @Valid RegistrarSubCategoriaDto dto) {
        return subCategoriaService.registrarSubCategoria( dto);
    }

    
    @GetMapping("/listar")
    public List<SubCategoriaDTO> listar() {
        return subCategoriaService.listar();
    }

    
    @GetMapping("buscar/{id}")
    public SubCategoria buscarPorId(@PathVariable long id) {
        return subCategoriaService.buscarPorId(id);
    }

    @PutMapping("/actualizar/{id}")
    public SubCategoriaDTO actualizar(@PathVariable long id, @RequestBody @Valid RegistrarSubCategoriaDto dto) {
        return subCategoriaService.actualizar(id, dto);
    }
    
    @DeleteMapping("/eliminar/{id}")
    public void desactivar(@PathVariable long id) {
         
        subCategoriaService.eliminar(id); 
    }

    @PutMapping("/reactivar/{id}")
    public void reactivar(@PathVariable long id){
        subCategoriaService.reactivar(id);
    }
}

