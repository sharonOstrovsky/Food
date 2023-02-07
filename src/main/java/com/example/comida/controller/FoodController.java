package com.example.comida.controller;

import com.example.comida.entity.Food;
import com.example.comida.repository.FoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class FoodController {

    private final Logger log =  LoggerFactory.getLogger(FoodController.class);
    private final FoodRepository foodRepository;

    public FoodController(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    /**
     * Buscar todas las comidas que hay en la base de datos
     * http://localhost:8080/api/food
     * @return ArrayList<Food>
     */
    @GetMapping("/api/food")
    public List<Food> findAll(){
        return foodRepository.findAll();
    }


    /**
     * Buscar una comida mediante su id
     * http://localhost:8080/api/food/1
     * @param id
     * @return
     */
    @GetMapping("/api/food/{id}")
    public ResponseEntity<Food> findOneById(@PathVariable Long id){

        Optional<Food> foodOpt = foodRepository.findById(id);
        if(foodOpt.isPresent()){
            return ResponseEntity.ok(foodOpt.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Crear una nueva comida en la base de datos
     * http://localhost:8080/api/food
     */
    @PostMapping("/api/food")
    public ResponseEntity<Food> create(@RequestBody Food food, @RequestHeader HttpHeaders headers){
        System.out.println(headers.get("User-Agent"));
        if(food.getId() != null){
            log.warn("trying to create a food with id");
            return ResponseEntity.badRequest().build();
        }
        Food result = foodRepository.save(food);
        return  ResponseEntity.ok(result);
    }


    /**
     * Actualizar una comida existente en la base de datos
     * http://localhost:8080/api/food
     */
    @PutMapping("/api/food")
    public ResponseEntity<Food> update(@RequestBody Food food){
        if(food.getId() == null){
            log.warn("Trying to update a non existent food");
            return ResponseEntity.badRequest().build();
        }
        if(!foodRepository.existsById(food.getId())){
            log.warn("Trying to update a non existent food");
            return ResponseEntity.notFound().build();
        }

        Food result = foodRepository.save(food);
        return ResponseEntity.ok(result);
    }


    /**
     * Eliminar una comida de la base de datos mediante su id
     * http://localhost:8080/api/food/1
     */
    @DeleteMapping("/api/food/{id}")
    public ResponseEntity<Food> delete(@PathVariable Long id){

        if(!foodRepository.existsById(id)){
            log.warn("Trying to delete a non existent food");
            return ResponseEntity.notFound().build();
        }
        foodRepository.deleteById(id);
        return  ResponseEntity.noContent().build();
    }


    /**
     * Eliminar todas las comidas de la base de datos
     * http://localhost:8080/api/food
     */
    @DeleteMapping("/api/food")
    public ResponseEntity<Food> deleteAll(){
        log.info("REST Request for delete all the food");
        foodRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
