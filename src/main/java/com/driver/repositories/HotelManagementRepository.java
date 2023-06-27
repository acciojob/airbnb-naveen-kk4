package com.driver.repositories;

import com.driver.Exceptions.DuplicateAdditionException;
import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository

public class HotelManagementRepository {
    HashMap<String,Hotel> hotelMap = new HashMap<>();
    HashMap<User,Integer> userMap = new HashMap<>();

    public void addHotel(Hotel hotel) {
        if(hotelMap.containsKey(hotel.getHotelName()))throw new DuplicateAdditionException("hotel already added");
        hotelMap.put(hotel.getHotelName(), hotel);
    }

    public void addUser(User user) {
        userMap.put(user,0);
    }

    public List<Hotel> findAllHotels() {
        return new ArrayList<>(hotelMap.values());
    }

    public Hotel findByHotelName(String hotelName) {
        return hotelMap.get(hotelName);
    }



    public List<User> findAllUsers() {
        return new ArrayList<>(userMap.keySet());
    }
    public void incrementBooking(User user,int num){
        int val = userMap.get(user);
        userMap.put(user,val+num);
    }

    public int getBookings(User bookedUser) {
        return userMap.get(bookedUser);
    }
}
