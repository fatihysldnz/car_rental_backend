package com.lecture.car_rental.service;


import com.lecture.car_rental.domain.Car;
import com.lecture.car_rental.domain.Reservation;
import com.lecture.car_rental.domain.User;
import com.lecture.car_rental.helper.ExcelHelper;
import com.lecture.car_rental.repository.CarRepository;
import com.lecture.car_rental.repository.ReservationRepository;
import com.lecture.car_rental.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@AllArgsConstructor
@Service
public class ExcelService {

    UserRepository userRepository;
    CarRepository carRepository;
    ReservationRepository reservationRepository;

    public ByteArrayInputStream loadUser() {
        List<User> users = userRepository.findAll();

        return ExcelHelper.usersExcel(users);
    }

    public ByteArrayInputStream loadCar() {
        List<Car> cars = carRepository.findAll();

        return ExcelHelper.carsExcel(cars);
    }

    public ByteArrayInputStream loadReservation() {
        List<Reservation> reservations = reservationRepository.findAll();

        return ExcelHelper.reservationsExcel(reservations);
    }
}
