package com.example.comida.service;

import com.example.comida.entity.Food;

import java.util.List;
import java.util.Optional;

public interface FoodService {

    List<Food> listarComida();
    Food guardarComida(Food comida);
    Optional<Food> obtenerComidaPorId(Long id);
    Food actualizarComida(Food comida);
    void eliminarComida(Long id);
    Food findByName(String nombre);

    boolean existById(Long id);
    boolean existByName(String nombre);
}
