package com.lecture.car_rental.service;

import com.lecture.car_rental.domain.Car;
import com.lecture.car_rental.domain.FileDB;
import com.lecture.car_rental.dto.CarDTO;
import com.lecture.car_rental.exception.BadRequestException;
import com.lecture.car_rental.exception.ResourceNotFoundException;
import com.lecture.car_rental.repository.CarRepository;
import com.lecture.car_rental.repository.FileDBRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;
    private final FileDBRepository fileDBRepository;
    private final static String IMAGE_NOT_FOUND_MSG = "image with id %s not found";
    private final static String CAR_NOT_FOUND_MSG = "car with id %d not found";

    public List<CarDTO> fetchAllCars() {
        return carRepository.findAllCar();
    }

    public CarDTO findById(Long id) throws ResourceNotFoundException {
        return carRepository.findCarByIdx(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(CAR_NOT_FOUND_MSG, id)));
    }

    public void add(Car car, String imageId) throws BadRequestException {
        FileDB fileDB = fileDBRepository.findById(imageId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(IMAGE_NOT_FOUND_MSG, imageId)));

        Set<FileDB> fileDBs = new HashSet<>();
        fileDBs.add(fileDB);

        car.setImage(fileDBs);
        car.setBuiltIn(false);
        carRepository.save(car);
    }

    public void updateCar(Long id, Car car, String imageId) throws BadRequestException {
        car.setId(id);
        FileDB fileDB = fileDBRepository.findById(imageId).get();

        Car car1 = carRepository.getById(id);

        if (car1.getBuiltIn()) {
            throw new BadRequestException("You dont have permission to update car!");
        }

        car.setBuiltIn(false);

        Set<FileDB> fileDBS = new HashSet<>();
        fileDBS.add(fileDB);

        car.setImage(fileDBS);

        carRepository.save(car);
    }

    public void removeById(Long id) throws ResourceNotFoundException {
        Car car = carRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(CAR_NOT_FOUND_MSG, id)));

        if (car.getBuiltIn())
            throw new BadRequestException("You dont have permission to delete car!");

        carRepository.deleteById(id);
    }
}
