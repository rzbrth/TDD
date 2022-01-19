package com.xp.tdd.controller;

import com.xp.tdd.domain.Car;
import com.xp.tdd.exception.CarNotFoundException;
import com.xp.tdd.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    // @MockBean means (@Mock + @InjectMocks)
    private CarService carService;

    public CarControllerTest() {
    }


    @Test
    public void getCar_returnsCarDetails() throws Exception{

        given(carService.getCar(anyString())).willReturn(new Car("nano", "hybrid"));

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/nano"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("nano"))
                .andExpect(jsonPath("type").value("hybrid"));
        verify(carService,times(1)).getCar(anyString());
    }

    @Test
    public void getCar_notFound() throws Exception{

        given(carService.getCar(anyString())).willThrow(new CarNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/nano"))
                .andExpect(status().isNotFound());
        verify(carService,times(1)).getCar(anyString());

    }

}
