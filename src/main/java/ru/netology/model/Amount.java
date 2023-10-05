package ru.netoology.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class Amount {

    private int value;
    private Currency currency;




}
