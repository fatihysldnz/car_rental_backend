package com.lecture.car_rental.service;

import com.lecture.car_rental.domain.Car;
import com.lecture.car_rental.domain.Reservation;
import com.lecture.car_rental.domain.User;
import com.lecture.car_rental.domain.enumeration.ReservationStatus;
import com.lecture.car_rental.exception.BadRequestException;
import com.lecture.car_rental.exception.ResourceNotFoundException;
import com.lecture.car_rental.repository.CarRepository;
import com.lecture.car_rental.repository.ReservationRepository;
import com.lecture.car_rental.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private static final String USER_NOT_FOUND_MSG = "user with id %d not found";
    private static final String CAR_NOT_FOUND_MSG = "car with id %d not found";

    public void addReservation(Reservation reservation, Long userId, Car carId) throws BadRequestException {
        boolean checkStatus = carAvailability(carId.getId(), reservation.getPickUpTime(), reservation.getDropOffTime());

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        if (!checkStatus)
            reservation.setStatus(ReservationStatus.CREATED);
        else
            throw new BadRequestException("Car is already reserved! Please choose another");

        reservation.setCarId(carId);
        reservation.setUserId(user);

        Double totalPrice = totalPrice(reservation.getPickUpTime(), reservation.getDropOffTime(), carId.getId());
        reservation.setTotalPrice(totalPrice);

        reservationRepository.save(reservation);
    }

    public boolean carAvailability(Long carId, LocalDateTime pickUpTime, LocalDateTime dropOffTime) {
        List<Reservation> checkStatus = reservationRepository.checkStatus(carId, pickUpTime, dropOffTime,
                ReservationStatus.DONE, ReservationStatus.CANCELED);

        return checkStatus.size() > 0;
    }

    public Double totalPrice(LocalDateTime pickUpTime, LocalDateTime dropOffTime, Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(CAR_NOT_FOUND_MSG, carId)));

        Long hours = (new Reservation()).getTotalHours(pickUpTime, dropOffTime);
        return car.getPricePerHour() * hours;
    }
}
