package com.example.PiBackEnd.controller;

import com.example.PiBackEnd.domain.Categoria;
import com.example.PiBackEnd.exceptions.ResourceBadRequestException;
import com.example.PiBackEnd.repository.CategoriaRepository;
import com.example.PiBackEnd.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    public ResponseEntity<Categoria> guardarCategoria(@RequestBody Categoria categoria) throws ResourceBadRequestException {
        return ResponseEntity.ok(categoriaService.guardarCategoria(categoria));
    }

    @GetMapping
    public List<Categoria> categorias(){
        return categoriaRepository.findAll();
    }
}
