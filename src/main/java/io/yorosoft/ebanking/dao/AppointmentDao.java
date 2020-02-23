package io.yorosoft.ebanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import io.yorosoft.ebanking.model.Appointment;

/**
 * AppointmentDao
 */
@Repository
public interface AppointmentDao extends JpaRepository<Appointment, Long> {
    List<Appointment> findAll();
    Optional<Appointment> findById(Long id);
}