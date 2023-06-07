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
			String output = "<div class=\"reservation-details\">" +
                    "<table>" +
                    "<tr>" +
                    "<th colspan=\"2\">Customer Details</th>" +
                    "<th colspan=\"2\">Accommodation Details</th>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>Name:</td>" +
                    "<td>" + reservation.getFname() + " " + reservation.getLname() + "</td>" +
                    "<td>Check-in Date:</td>" +
                    "<td>" + reservation.getCheckInDate() + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>Phone:</td>" +
                    "<td>" + reservation.getPhone() + "</td>" +
                    "<td>Check-out Date:</td>" +
                    "<td>" + reservation.getCheckOutDate() + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>Email:</td>" +
                    "<td>" + reservation.getEmail() + "</td>" +
                    "<td>Number of Nights:</td>" +
                    "<td>" + reservation.CalculateNumNights(checkInDate, checkOutDate) + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>Address:</td>" +
                    "<td>" + reservation.getAddress() + ", " +
                    reservation.getCity() + ", " +
                    reservation.getPostal() + ", " +
                    reservation.getProvince() + "</td>" +
                    "<td>Room Type:</td>" +
                    "<td>" + reservation.getViewType() + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td></td>" +
                    "<td></td>" +
                    "<td>Guests:</td>" +
                    "<td>" + reservation.getNumAdults() + " adults, " + reservation.getNumChildren() + " children</td>" +
                    "</tr>" +
                    "</table>" +
                    "<br><br>" +
                    "<p>Payment Method: " + reservation.getPaymentType() + "</p>" +
                    "<br><br><br>" +
                    "<p>Total Cost: " + totalCost + "</p>" +
                "</div>";

			model.addAttribute("output", output);
			return "show-details";
		}
	}
}