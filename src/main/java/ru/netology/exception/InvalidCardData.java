package ru.netoology.exception;

import lombok.extern.slf4j.Slf4j;


public class InvalidCardData extends RuntimeException {

    public InvalidCardData(String message) {
        super(message);

    }


}
