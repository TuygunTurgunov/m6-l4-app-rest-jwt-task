package uz.pdp.online.m6l4apprestjwttask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.m6l4apprestjwttask.entity.Income;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income,Integer> {

    List<Income> findAllByToCardId_Username(String toCardId_username);


}
