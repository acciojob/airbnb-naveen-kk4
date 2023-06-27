package com.driver.Exceptions;

public class DuplicateAdditionException extends RuntimeException {
    public DuplicateAdditionException(String hotelAlreadyAdded) {
        super(hotelAlreadyAdded);
    }
}
