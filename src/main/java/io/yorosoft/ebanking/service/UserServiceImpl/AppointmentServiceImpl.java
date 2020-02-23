package io.yorosoft.ebanking.service.UserServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.yorosoft.ebanking.dao.AppointmentDao;
import io.yorosoft.ebanking.model.Appointment;
import io.yorosoft.ebanking.service.AppointmentService;

/**
 * AppointmentServiceImpl
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private AppointmentDao appointmentDao;

    public AppointmentServiceImpl(AppointmentDao appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentDao.save(appointment);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentDao.findAll();
    }

    @Override
    public Optional<Appointment> findAppointment(Long id) {
        return appointmentDao.findById(id);
    }

    @Override
    public void confirmAppointment(Long id) {}

    
}