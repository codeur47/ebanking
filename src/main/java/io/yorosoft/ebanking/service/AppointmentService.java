package io.yorosoft.ebanking.service;

import java.util.List;
import java.util.Optional;

import io.yorosoft.ebanking.model.Appointment;

/**
 * appointmentService
 */
public interface AppointmentService {

    Appointment createAppointment(Appointment appointment);
    List<Appointment> findAll();
    Optional<Appointment> findAppointment(Long id);
    void confirmAppointment(Long id);
    
}