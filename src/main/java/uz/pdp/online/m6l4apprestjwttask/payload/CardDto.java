package uz.pdp.online.m6l4apprestjwttask.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CardDto {

    @NotNull(message = "card unique number not be null")
    private Long number;
    @NotNull(message = "card expire date no be null")
    private Long expireDate;

    private Double balance;





}
