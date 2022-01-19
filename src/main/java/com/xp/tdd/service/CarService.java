package com.xp.tdd.service;

import com.xp.tdd.domain.Car;
import com.xp.tdd.exception.CarNotFoundException;
import com.xp.tdd.repository.CarRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    @Cacheable("car")
    public Car getCar(String name) {
        Car car = carRepository.findByName(name);
        if(car == null){
            throw new CarNotFoundException();
        }
        return car;
    }
}
