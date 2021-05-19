package uz.pdp.online.m6l4apprestjwttask.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.online.m6l4apprestjwttask.entity.Income;
import uz.pdp.online.m6l4apprestjwttask.entity.Outcome;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {
    private List<Outcome> outcome;
    private List<Income> income;

}
