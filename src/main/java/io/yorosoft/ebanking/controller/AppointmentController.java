package io.yorosoft.ebanking.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import io.yorosoft.ebanking.model.Appointment;
import io.yorosoft.ebanking.service.AppointmentService;
import io.yorosoft.ebanking.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



/**
 * AppointmentController
 */
@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    private AppointmentService appointmentService;

    private UserService userService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String getMethodName(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("dateString", "");
        return "appointment";
    }

    @PostMapping(value="/create")
    public String createAppointmentPost(@ModelAttribute("appointment") Appointment appointment,
                                        @ModelAttribute("dateString") String date,
                                        Model model, Principal principal) throws ParseException {
        Date appointmentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(date);
        appointment.setDate(appointmentDate);
        appointment.setUser(userService.findByUsername(principal.getName()));
        appointmentService.createAppointment(appointment);
        return "redirect:/userFront";
    }
    
}