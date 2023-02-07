package com.example.comida.service;

import com.example.comida.entity.Food;
import com.example.comida.repository.FoodRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FoodServiceImpTest {

    @Autowired
    private FoodRepository foodRepository;

    @Test
    void listarComida() {

        List<Food> food = new ArrayList<>(foodRepository.findAll());
        System.out.println(food.size());

        assertNotEquals(null, food);

        for (Food aux: food) {
            System.out.println(aux.toString());
        }

    }

    @Test
    void guardarComida() {

        //Creo objeto comida
        Food comida = new Food(null, "tostadas");

        FoodService foodService = new FoodServiceImp(foodRepository);
        //Guardo objeto en BD
        foodService.guardarComida(comida);

        //Compruebo
        assertEquals("tostadas", comida.getName());
    }

    @Test
    void obtenerComidaPorId() {

        FoodService foodService = new FoodServiceImp(foodRepository);

        Optional<Food> food = foodService.obtenerComidaPorId(302l);

        assertEquals(302L,food.get().getId());
        assertEquals("tortilla", food.get().getName());


    }

    @Test
    void actualizarComida() {

        Food comida = new Food(102L, "tostada modificado desde test");
        FoodService foodService = new FoodServiceImp(foodRepository);
        foodService.actualizarComida(comida);

        assertEquals("tostada modificado desde test", comida.getName());

    }

    @Test
    void eliminarComida() {
        FoodService foodService = new FoodServiceImp(foodRepository);
        foodService.eliminarComida(202L);

       assertFalse(foodRepository.existsById(202L));

    }

    @Test
    void findByName() {

        FoodService foodService = new FoodServiceImp(foodRepository);

        Food food = foodService.findByName("Milanesa");

        assertEquals(1L,food.getId());
        assertEquals("Milanesa", food.getName());
    }

    @Test
    void existByName() {

        FoodService foodService = new FoodServiceImp(foodRepository);

        assertTrue(foodService.existById(1L));
    }

    @Test
    void existById(){
        FoodService foodService = new FoodServiceImp(foodRepository);

        assertTrue(foodService.existByName("Milanesa"));
    }
}