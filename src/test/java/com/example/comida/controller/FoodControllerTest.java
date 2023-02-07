package com.example.comida.controller;

import com.example.comida.entity.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FoodControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void findAll() {

        ResponseEntity<Food[]> response  =
                testRestTemplate.getForEntity("/api/food", Food[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Food> food = Arrays.asList(response.getBody());

        for (Food aux: food) {
            System.out.println(aux.toString());
        }
        System.out.println(food.size());
    }

    @Test
    void findOneById() {

        ResponseEntity<Food> response  =
                testRestTemplate.getForEntity("/api/food/1", Food.class);

        System.out.println(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void create() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "name": "Papas Fritas desde Spring Test"
                }
                """;

        HttpEntity<String> request = new HttpEntity<>(json,headers);

        ResponseEntity<Food> response = testRestTemplate.exchange("/api/food", HttpMethod.POST, request, Food.class);

        Food result = response.getBody();

        assertEquals("Papas Fritas desde Spring Test", result.getName());
        findAll();
    }

    @Test
    void update() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
               {
               "id": 52,
               "name": "Papas Fritas modificado desde Spring Test"
               }
               """;
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Food> response = testRestTemplate.exchange("/api/food", HttpMethod.PUT, request, Food.class);
        Food resultado = response.getBody();
        assertEquals(52l, resultado.getId());
        assertEquals("Papas Fritas modificado desde Spring Test", resultado.getName());
        findAll();
    }

    @Test
    void delete() {

        ResponseEntity<Food> response = testRestTemplate.getForEntity("/api/food/52", Food.class);
        Food food = response.getBody();
        testRestTemplate.delete("/api/food/52",food,"/api/food/{id}");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        findAll();
    }

    @Test
    void deleteAll() {
    }


    @Test
    void getName(){
        Food food = new Food(null,"iceCream");
        assertNotNull(food.getName());
        assertEquals("iceCream", food.getName());

    }

    @Test
    void notGetName(){
        Food food = new Food();
        assertNull(food.getName());
        assertNotEquals("iceCream", food.getName());
    }


}