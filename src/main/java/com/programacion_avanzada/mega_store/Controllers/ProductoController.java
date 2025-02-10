package com.programacion_avanzada.mega_store.Controllers;

import com.programacion_avanzada.mega_store.DTOs.ProductoDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.programacion_avanzada.mega_store.DTOs.RegistrarProductoDto;
import com.programacion_avanzada.mega_store.Modelos.Producto;
import com.programacion_avanzada.mega_store.Service.IProductoService;

import jakarta.validation.Valid;

import java.util.List;





@RestController
@RequestMapping("api/productos")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductoController {
    @Autowired
    IProductoService productoService;


    @PostMapping("/registrar")
    public Producto registrarProducto(@RequestBody @Valid RegistrarProductoDto dto) {
        
        
        return productoService.registrarProducto(dto);
    }

    @GetMapping("/listar")
    public List<Producto> listar(){
        return productoService.listar();
    }

    @PutMapping("/actualizar/{id}")
    public Producto editarProducto(@PathVariable long id, @RequestBody RegistrarProductoDto dto) {
        
        
        return productoService.editarProducto(id, dto);
    }
    
    @DeleteMapping("/eliminar/{id}")
    public void eliminarProducto(@PathVariable("id") long id){

         productoService.eliminar(id);
    }

    @PutMapping("/reactivar/{id}")
    public void reactivarProducto(@PathVariable("id") long id){
        productoService.reactivar(id);
    }

    @GetMapping("/buscar/{id}")
    public Producto buscarPorId(@PathVariable("id") long id){
        return productoService.buscarPorId(id);
    }

    

}
