package com.programacion_avanzada.mega_store.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacion_avanzada.mega_store.DTOs.ProductoDto;
import com.programacion_avanzada.mega_store.Service.IProductoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("producto")
public class ProductoController {
    @Autowired
    IProductoService productoService;


    @PostMapping("/registrar")
    public ProductoDto registrarProducto(@RequestBody @Valid ProductoDto dto) {
        
        
        return productoService.registrarProducto(dto);
    }
    

}
