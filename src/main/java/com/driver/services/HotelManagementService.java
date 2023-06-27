package com.driver.services;

import com.driver.Exceptions.DuplicateAdditionException;
import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repositories.HotelManagementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service

public class HotelManagementService {
    HotelManagementRepository repository = new HotelManagementRepository();
   /* @Autowired
    BookingRepositiry bookingRepositiry;*/

    public String addHotel(Hotel hotel) {
        if(Objects.isNull(hotel))return "FAILURE";
        if(hotel.getHotelName()==null)return "FAILURE";
        try{
            repository.addHotel(hotel);
            return "SUCCESS";
        }
        catch(DuplicateAdditionException e){
            return "FAILURE";
        }
    }

    public Integer addUser(User user) {
        repository.addUser(user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        List<Hotel> hotelList = repository.findAllHotels();
        int facilities = 0;
        String name = null;
        for(Hotel hotel:hotelList){
            if(hotel.getFacilities().size()>facilities){
                name = hotel.getHotelName();
                facilities=hotel.getFacilities().size();
            }
            else if(facilities>0 && hotel.getFacilities().size()==facilities){
                if(hotel.getHotelName().compareTo(name)<0){
                    name = hotel.getHotelName();
                }
            }
        }
        if(facilities==0)return "";
        return name;

    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = repository.findByHotelName(hotelName);
        List<Facility> facilities = hotel.getFacilities();
        for(Facility facility:newFacilities){
            if(!facilities.contains(facility))facilities.add(facility);
        }
        hotel.setFacilities(facilities);
        return hotel;
    }

    public int bookARoom(Booking booking) {


        String hotelName = booking.getHotelName();
        int bookRooms = booking.getNoOfRooms();
        Hotel hotel = repository.findByHotelName(hotelName);

        if(bookRooms> hotel.getAvailableRooms())return -1;
        int price = bookRooms*hotel.getPricePerNight();
        hotel.setAvailableRooms(hotel.getAvailableRooms()-bookRooms);
        List<User> users = repository.findAllUsers();
        for(User user : users){
            if(user.getaadharCardNo()==booking.getBookingAadharCard()){
                repository.incrementBooking(user,1);
                break;

            }
        }

        return price;

    }

    public int getBookings(Integer aadharCard) {
        List<User> users = repository.findAllUsers();

        for(User user : users){
            if(user.getaadharCardNo()==aadharCard){
                return repository.getBookings(user);

            }
        }

        return -1;
    }
}
