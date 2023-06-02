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

		if (bindingResult.hasErrors()) { // Prevent the user from moving past form if any data is invalid  
			return "index";
		} else { 
			LocalDate checkInDate = reservation.getCheckInDate();
			LocalDate checkOutDate = reservation.getCheckOutDate();
			String viewType = reservation.getViewType();

			if (!reservation.validateDates(checkInDate, checkOutDate)) {
				// Dates are invalid, handle the error
				model.addAttribute("error", "Invalid dates");
				return "index";
			}

			double totalCost = reservation.CalculateTotalCost(checkInDate, checkOutDate, viewType);


			// Output details of reservation
			String output = "Welcome " + reservation.getFname() + "<br><br>" +
					"Name: " + reservation.getFname() + " " + reservation.getLname() + "<br>" +
					"Phone: " + reservation.getPhone() + "<br>" +
					"Email: " + reservation.getEmail() + "<br>" +
					"Address: " + reservation.getAddress() + "<br>" +
					"City: " + reservation.getCity() + "<br>" +
					"Postal Code: " + reservation.getPostal() + "<br>" +
					"Province: " + reservation.getProvince() + "<br><br>" +
					"Accommodations:<br>" +
					"Check-in Date: " + reservation.getCheckInDate() + "<br>" +
					"Check-out Date: " + reservation.getCheckOutDate() + "<br>" +
					"Number of Nights: " + reservation.CalculateNumNights(checkInDate, checkOutDate) + "<br>" +
					"Room Type: " + reservation.getViewType() + "<br>" +					 
					"Number of Adults: " + reservation.getNumAdults() + "<br>" +
					"Number of Children: " + reservation.getNumChildren() + "<br><br>" +
					"Payment Method: " + reservation.getPaymentType() + "<br><br><br>" +
					"Total Cost: " + totalCost;

			model.addAttribute("output", output);
			return "show-details";
		}
	}
}