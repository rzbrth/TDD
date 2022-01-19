package com.xp.tdd;

import com.xp.tdd.domain.Car;
import com.xp.tdd.repository.CarRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IntegrationTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;


    @Test
    public void getCar_returnsCarDetails() throws Exception{
        //arrange
        carRepository.save(new Car("nano","hybrid"));
        //act
        ResponseEntity<Car> response = restTemplate
                .getForEntity("/cars/nano", Car.class);

        //assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("nano");
        assertThat(response.getBody().getType()).isEqualTo("hybrid");

        //clearing db
        carRepository.deleteAll();
    }

}
