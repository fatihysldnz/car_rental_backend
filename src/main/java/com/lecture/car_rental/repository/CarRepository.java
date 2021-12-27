package com.lecture.car_rental.repository;

import com.lecture.car_rental.domain.Car;
import com.lecture.car_rental.dto.CarDTO;
import com.lecture.car_rental.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT new com.lecture.car_rental.dto.CarDTO(c) FROM Car c")
    List<CarDTO> findAllCar();

    @Query("SELECT new com.lecture.car_rental.dto.CarDTO(c) FROM Car c WHERE c.id = ?1")
    Optional<CarDTO> findCarByIdx(Long id) throws ResourceNotFoundException;
}
