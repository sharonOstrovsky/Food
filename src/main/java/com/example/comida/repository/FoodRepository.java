package com.example.comida.repository;

import com.example.comida.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    boolean existsById(Long id);

    boolean existsByName(String name);

    Food findByName(String nombre);


}
