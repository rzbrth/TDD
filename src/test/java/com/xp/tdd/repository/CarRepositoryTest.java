package com.xp.tdd.repository;

import com.xp.tdd.domain.Car;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TestEntityManager testEntityManager;


//    @AfterAll
//    public void tearDownAfter(){
//        carRepository.deleteAll();
//    }
//
//    @BeforeAll
//    public void tearDownBefore(){
//        carRepository.deleteAll();
//    }
//    @BeforeAll
//    @Disabled
//    public void createCar(){
//        List<Car> cars = new ArrayList<>();
//        cars.add(new Car("nano","hybrid"));
//        cars.add(new Car("tiago","electric"));
//        cars.add(new Car("verena","petrol"));
//
//        carRepository.saveAll(cars);
//    }
    /*
     When we do repository.save then data get saved to first level cache only .
     TestEntityManager takes the first level cache and flush it to the db
     i.e. when we test using findBy after doing save we are actually testing the cache not the db
      */
    @Test
    public void getCar_returnCardDetails(){

          Car savedCar = testEntityManager.persistFlushFind(new Car("nano","hybrid"));
          Car car = carRepository.findByName("nano");
          assertNotNull(car);
          assertEquals(car.getName(), savedCar.getName());
          assertEquals(car.getType(), savedCar.getType());
    }

    @Test
    public void getCar_returnNullWhenCarNotPresent(){
        Car car = carRepository.findByName("nano");
        assertNull(car);
    }

}
