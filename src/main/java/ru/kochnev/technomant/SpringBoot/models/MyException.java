package ru.kochnev.technomant.SpringBoot.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException {
    private String message = "Error";
}
