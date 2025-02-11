package com.programacion_avanzada.mega_store.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.programacion_avanzada.mega_store.DTOs.ProductoDtos.ProductoDto;
import com.programacion_avanzada.mega_store.DTOs.ProductoDtos.RegistrarProductoDto;
import com.programacion_avanzada.mega_store.Modelos.Producto;
import com.programacion_avanzada.mega_store.Service.Interfaces.IProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;





@RestController
@RequestMapping("api/productos")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Producto", description = "API de productos")
public class ProductoController {
    @Autowired
    IProductoService productoService;


    @Operation(summary = "Registrar producto", description = "Permite registrar un producto en la base de datos")
    @PostMapping("/registrar")
    public Producto registrarProducto(@RequestBody @Valid RegistrarProductoDto dto) {
        
        
        return productoService.registrarProducto(dto);
    }

    @Operation(summary = "Listar productos", description = "Permite listar todos los productos registrados en la base de datos")
    @GetMapping("/listar")
    public List<Producto> listar(){
        return productoService.listar();
    }

    @Operation(summary = "Editar producto", description = "Permite editar un producto por su ID")
    @PutMapping("/actualizar/{id}")
    public Producto editarProducto(@PathVariable long id, @RequestBody RegistrarProductoDto dto) {
        
        
        return productoService.editarProducto(id, dto);
    }
    
    @Operation(summary = "Eliminar producto", description = "Permite eliminar un producto por su ID de forma logica")
    @DeleteMapping("/eliminar/{id}")
    public void eliminarProducto(@PathVariable("id") long id){

         productoService.eliminar(id);
    }

    @Operation(summary = "Reactivar producto", description = "Permite reactivar un producto por su ID")
    @PutMapping("/reactivar/{id}")
    public void reactivarProducto(@PathVariable("id") long id){
        productoService.reactivar(id);
    }

    @Operation(summary = "Buscar producto por ID", description = "Permite buscar un producto por su ID")
    @GetMapping("/buscar/{id}")
    public Producto buscarPorId(@PathVariable("id") long id){
        return productoService.buscarPorId(id);
    }

    

}
