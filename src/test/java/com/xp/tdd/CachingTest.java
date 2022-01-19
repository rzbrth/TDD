package com.xp.tdd;

import com.xp.tdd.domain.Car;
import com.xp.tdd.repository.CarRepository;
import com.xp.tdd.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
@AutoConfigureTestDatabase
public class CachingTest {

    @Autowired
    private CarService carService;

    @MockBean
    private CarRepository carRepository;


    @Test
    public void cachingTest(){
        given(carRepository.findByName(anyString())).willReturn(new Car("nano","hybrid"));

        carService.getCar("nano");
        carService.getCar("nano");

        verify(carRepository, times(1)).findByName("nano");
    }

}
