
package com.programacion_avanzada.mega_store.Controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacion_avanzada.mega_store.DTOs.SubcategoriaDtos.RegistrarSubCategoriaDto;
import com.programacion_avanzada.mega_store.Modelos.SubCategoria;
import com.programacion_avanzada.mega_store.Service.Interfaces.ISubCategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/subcategorias")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "SubCategoria", description = "API de SubCategoria")
public class SubCategoriaController {

    @Autowired
    private ISubCategoriaService subCategoriaService;

    @Operation(summary = "Registrar una subcategoria")
    @PostMapping("/registrar")
    public SubCategoria registrar(@RequestBody @Valid RegistrarSubCategoriaDto dto) {
        return subCategoriaService.registrarSubCategoria( dto);
    }

    @Operation(summary = "Listar todas las subcategorias")
    @GetMapping("/listar")
    public List<SubCategoria> listar() {
        return subCategoriaService.listar();
    }

    @Operation(summary = "Buscar una subcategoria por id")
    @GetMapping("buscar/{id}")
    public SubCategoria buscarPorId(@PathVariable long id) {
        return subCategoriaService.buscarPorId(id);
    }

    @Operation(summary = "Actualizar una subcategoria por id")
    @PutMapping("/actualizar/{id}")
    public SubCategoria actualizar(@PathVariable long id, @RequestBody @Valid RegistrarSubCategoriaDto dto) {
        return subCategoriaService.actualizar(id, dto);
    }
    
    @Operation(summary = "Eliminar una subcategoria por id de forma lógica (soft delete)")
    @DeleteMapping("/eliminar/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "SubCategoria eliminada correctamente"),
        @ApiResponse(responseCode = "400", description = "Error al eliminar la SubCategoria")
    })
    public ResponseEntity<Void> desactivar(@PathVariable long id) {
        try {
            subCategoriaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/reactivar/{id}")
    @Operation(summary = "Reactivar una subcategoria por id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "SubCategoria reactivada correctamente"),
        @ApiResponse(responseCode = "400", description = "Error al reactivar la SubCategoria")
    })
    public void reactivar(@PathVariable long id){
        subCategoriaService.reactivar(id);
    }
}

