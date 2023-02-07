package com.example.comida.service;

import com.example.comida.entity.Food;
import com.example.comida.repository.FoodRepository;

import java.util.List;
import java.util.Optional;



public class FoodServiceImp implements FoodService{

    private FoodRepository foodRepository;

    public FoodServiceImp(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public List<Food> listarComida() {
        return foodRepository.findAll();
    }

    @Override
    public Food guardarComida(Food comida) {
        return foodRepository.save(comida);
    }

    @Override
    public Optional<Food> obtenerComidaPorId(Long id) {
        return foodRepository.findById(id);
    }

    @Override
    public Food actualizarComida(Food comida) {
        return foodRepository.save(comida);
    }

    @Override
    public void eliminarComida(Long id) {
        foodRepository.deleteById(id);
    }

    @Override
    public Food findByName(String nombre) {
        return foodRepository.findByName(nombre);
    }

    @Override
    public boolean existById(Long id) {
        return foodRepository.existsById(id);
    }


    @Override
    public boolean existByName(String nombre) {
        return foodRepository.existsByName(nombre);
    }
}
