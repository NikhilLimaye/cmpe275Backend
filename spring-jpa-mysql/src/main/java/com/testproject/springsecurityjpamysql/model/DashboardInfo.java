package com.testproject.springsecurityjpamysql.model;

import java.util.List;

public class DashboardInfo {

	Property prop;
	List<Booking> booking;
	
	
	public DashboardInfo() {
		
	}
		
	public Property getProp() {
		return prop;
	}
	public void setProp(Property prop) {
		this.prop = prop;
	}
	public List<Booking> getBooking() {
		return booking;
	}
	public void setBooking(List<Booking> booking) {
		this.booking = booking;
	}
		
}
