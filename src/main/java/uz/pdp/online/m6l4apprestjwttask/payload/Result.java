package uz.pdp.online.m6l4apprestjwttask.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Result {
    private String message;
    private Boolean success;
}