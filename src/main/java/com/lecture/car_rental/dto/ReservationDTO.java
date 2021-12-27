package com.lecture.car_rental.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lecture.car_rental.domain.Car;
import com.lecture.car_rental.domain.Reservation;
import com.lecture.car_rental.domain.User;
import com.lecture.car_rental.domain.enumeration.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long id;


    private CarDTO carId;

    private Long userId;

    private LocalDateTime pickUpTime;

    private LocalDateTime dropOffTime;

    private String pickUpLocation;

    private String dropOffLocation;

    private ReservationStatus status;

    private Double totalPrice;

    public ReservationDTO(Reservation reservation) {
        this.id = reservation.getId();
        this.carId = new CarDTO(reservation.getCarId());
        this.userId = reservation.getUserId().getId();
        this.pickUpTime = reservation.getPickUpTime();
        this.dropOffTime = reservation.getDropOffTime();
        this.pickUpLocation = reservation.getPickUpLocation();
        this.dropOffLocation = reservation.getDropOffLocation();
        this.status = reservation.getStatus();
        this.totalPrice = reservation.getTotalPrice();
    }
}
