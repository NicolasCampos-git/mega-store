package com.programacion_avanzada.mega_store.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacion_avanzada.mega_store.DTOs.InventarioDtos.stockDTO;
import com.programacion_avanzada.mega_store.Modelos.Producto;
import com.programacion_avanzada.mega_store.Service.InventarioService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("api/inventario")
public class InventarioController {
    
    @Autowired
    InventarioService inventarioService;

    @PutMapping("/agregarStock/{id}")
    public Producto agregarStock(@PathVariable("id") long idProducto, @RequestBody stockDTO stock) {
        return inventarioService.agregarStock(idProducto, stock);
    
    }

    @PutMapping("/quitarStock/{id}")
    public Producto quitarStock(@PathVariable("id") long idProducto, @RequestBody stockDTO stock) {
        return inventarioService.quitarStock(idProducto, stock);
    } 

}
