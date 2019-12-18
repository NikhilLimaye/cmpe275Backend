package com.testproject.springsecurityjpamysql.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testproject.springsecurityjpamysql.model.Booking;
import com.testproject.springsecurityjpamysql.model.DashboardInfo;
import com.testproject.springsecurityjpamysql.model.Property;
import com.testproject.springsecurityjpamysql.model.UserProfile;
import com.testproject.springsecurityjpamysql.repository.BookingRepository;
import com.testproject.springsecurityjpamysql.repository.PostingsRepository;

@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
@RestController
public class DashboardResource {
	
	@Autowired
	PostingsRepository postRepo;
	
	@Autowired
	BookingRepository bookingRepo;
	
	
	@GetMapping("/owner/{ownerID}")
	public List<DashboardInfo> getOwnerDashboard(@PathVariable String ownerID) {
		
		ArrayList<DashboardInfo> result = new ArrayList<DashboardInfo>();
			
		
		//Find all user props
		Property p = new Property();
		UserProfile owner = new UserProfile();
		owner.setUserID(ownerID);
		p.setUser(owner);
		p.setBooked(true);
		Example<Property> propExample = Example.of(p);				
		List<Property> pList = postRepo.findAll(propExample);

		for(Property pTemp : pList) {
			
			int pID = pTemp.getPropertyID();
			Booking b = new Booking();
			b.setPropertyID(pID);
			Example<Booking> bEx = Example.of(b);
			List<Booking> bookingList = bookingRepo.findAll(bEx);
			if(bookingList.size() > 0) {				
				DashboardInfo dInfo = new DashboardInfo();
				dInfo.setBooking(bookingList);
				dInfo.setProp(pTemp);
				result.add(dInfo);
			}
			
		}
				
		return result;
		
	}

	
	@GetMapping("/user/{userID}")
	public ArrayList<DashboardInfo> getUserDashboard(@PathVariable String userID) {
		
		ArrayList<DashboardInfo> result = new ArrayList<DashboardInfo>();
		
		Booking b = new Booking();
		b.setUserID(userID);
		Example<Booking> bookings = Example.of(b);				
		List<Booking> bList = bookingRepo.findAll(bookings);
		
		System.out.println("Booking list size = "+bList.size());
		
		for(Booking bTemp : bList) {
			
			int propertyID = bTemp.getPropertyID();
			Property p = new Property();
			p.setPropertyID(propertyID);
			Property pObj = postRepo.findOne(Example.of(p)).get();
			DashboardInfo dInfo = new DashboardInfo();
			List<Booking> userList = new ArrayList<Booking>();
			userList.add(bTemp);
			dInfo.setBooking(userList);
			dInfo.setProp(pObj);
			result.add(dInfo);
			
		}
		
		
	
		
		return result;
		
		
	}

}
