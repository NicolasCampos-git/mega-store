package com.programacion_avanzada.mega_store.Controllers;



import com.programacion_avanzada.mega_store.DTOs.MarcaDto;
import com.programacion_avanzada.mega_store.Modelos.Marca;
import com.programacion_avanzada.mega_store.Service.IMarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marcas")
public class MarcaController {

    @Autowired
    private IMarcaService marcaService;

    
    @PostMapping("/registrar")
    public MarcaDto registrarMarca(@RequestBody MarcaDto dto) {
        return marcaService.registrarMarca(dto);
    }

    
    @GetMapping("/listar")
    public List<MarcaDto> listarMarcas() {
        return marcaService.listar();
    }

    
    @GetMapping("/buscar/{id}")
    public Marca buscarPorId(@PathVariable long id) {
        return marcaService.buscarPorId(id);
    }

    
    @DeleteMapping("/eliminar/{id}")
    public void eliminarMarca(@PathVariable long id) {
        marcaService.eliminar(id);
    }
}
