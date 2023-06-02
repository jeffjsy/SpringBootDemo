package com.jeffreysy.assignment1;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class Reservation {
    private String fname;
    private String lname;
    private String phone;
    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format") //Checks for valid email format
    private String email;
    private String address;
    private String city;
    private String postal;
    private String province;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String viewType;    
    private int numAdults;
    private int numChildren;
    private String paymentType;

    public Reservation() {
    }
    
    public Reservation(String fname, String lname, String phone, String email, String address, String city,
                       String postal, String province, LocalDate checkInDate, LocalDate checkOutDate, String viewType,
                       int numAdults, int numChildren, String paymentType) {
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.postal = postal;
        this.province = province;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.viewType = viewType;        
        this.numAdults = numAdults;
        this.numChildren = numChildren;
        this.paymentType = paymentType;
    }

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public int getNumAdults() {
		return numAdults;
	}

	public void setNumAdults(int numAdults) {
		this.numAdults = numAdults;
	}

	public int getNumChildren() {
		return numChildren;
	}

	public void setNumChildren(int numChildren) {
		this.numChildren = numChildren;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	
	public boolean validateDates(LocalDate checkInDate, LocalDate checkOutDate) {
	    LocalDate currentDate = LocalDate.now();
	    
	    // Check if check-in date is in the past
	    if (checkInDate.isBefore(currentDate)) {
	        return false;
	    }
	    
	    // Check if check-out date is before or same as check-in date
	    if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
	        return false;
	    }
	    
	    // All checks passed, dates are valid
	    return true;
	}
	
	// Validates the dates selected and calculates the number of nights
	public long CalculateNumNights(LocalDate checkInDate, LocalDate checkOutDate) {
	    if (!validateDates(checkInDate, checkOutDate)) {
	        // If dates are invalid
	        throw new IllegalArgumentException("Invalid dates");
	    }
	    
	    return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
	}
	
	// Get type of room and calculate total cost.
	public double CalculateTotalCost(LocalDate checkInDate, LocalDate checkOutDate, String viewType) {
	    long numNights = CalculateNumNights(checkInDate, checkOutDate);
	    double ratePerNight = 0;
	    
	    if (!validateDates(checkInDate, checkOutDate)) {
	        // Dates are invalid, handle the error
	        // You can display an error message or throw an exception
	        // For example:
	        throw new IllegalArgumentException("Invalid dates");
	    }
	    
	    if ("Mountain View".equals(viewType)) {
	        ratePerNight = 100.0;
	    } else if ("Ocean View".equals(viewType)) {
	        ratePerNight = 120.0;
	    } else if ("City View".equals(viewType)) {
	        ratePerNight = 140.0;
	    }
	    
	    return ratePerNight * numNights;
	}
   
    
}

