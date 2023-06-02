package com.jeffreysy.assignment1;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.time.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@Validated
public class ReservationController {

	@RequestMapping("/")
	public String showForm() {
		return "index";
	}

	@PostMapping("/show-details")
	public String processForm(@Valid Reservation reservation, BindingResult bindingResult, Model model) {
		LocalDate checkInDate = reservation.getCheckInDate();
	    LocalDate checkOutDate = reservation.getCheckOutDate();
	    LocalDate currentDate = LocalDate.now();

	    if (bindingResult.hasErrors() || checkInDate.isBefore(currentDate) || checkOutDate.isBefore(currentDate) || checkOutDate.isBefore(checkInDate)) {
	        if (checkInDate.isBefore(currentDate) || checkOutDate.isBefore(currentDate) || checkOutDate.isBefore(checkInDate)) {
	            model.addAttribute("message", "Invalid dates. Please ensure the dates are valid");
	        } else {
	            model.addAttribute("message", "Please correct the errors in the form.");
	        }
	        model.addAttribute("reservation", reservation);
	        return "error";
		} else { 
			double totalCost = reservation.CalculateTotalCost(checkInDate, checkOutDate, reservation.getViewType());

			// Output details of reservation
			String output = "Welcome " + reservation.getFname() + "<br><br>" +
					"Name: " + reservation.getFname() + " " + reservation.getLname() + "<br>" +
					"Phone: " + reservation.getPhone() + "<br>" +
					"Email: " + reservation.getEmail() + "<br>" +
					"Address: " + reservation.getAddress() + ", " +					
					reservation.getCity() + ", " +
					reservation.getPostal() + ", " +
					reservation.getProvince() + "<br><br>" +
					"Accommodations:<br>" +
					"Check-in Date: " + reservation.getCheckInDate() + "<br>" +
					"Check-out Date: " + reservation.getCheckOutDate() + "<br>" +
					"Number of Nights: " + reservation.CalculateNumNights(checkInDate, checkOutDate) + "<br>" +
					"Room Type: " + reservation.getViewType() + "<br>" +					 
					"Guests: " + reservation.getNumAdults() + " adults, "+ reservation.getNumChildren() + " children" + "<br><br>" +
					"Payment Method: " + reservation.getPaymentType() + "<br><br><br>" +
					"Total Cost: " + totalCost;

			model.addAttribute("output", output);
			return "show-details";
		}
	}
}