package com.xp.tdd.service;

import com.xp.tdd.domain.Car;
import com.xp.tdd.exception.CarNotFoundException;
import com.xp.tdd.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    public void getCar_returnsCarDetails() {

        //arrange
        when(carRepository.findByName(anyString())).thenReturn(new Car("nano","hybrid"));

        //act
        Car car = carService.getCar("nano");

        //assert
        assertEquals(car.getName(), "nano");
        assertEquals(car.getType(),"hybrid");
    }

    @Test
    public void getCar_whenCarNotFound() {

        //arrange
        given(carRepository.findByName(anyString())).willReturn(null);

        //assert
        assertThrows(CarNotFoundException.class, () -> carService.getCar("nano"));
    }
}