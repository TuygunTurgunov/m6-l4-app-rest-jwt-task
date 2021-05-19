package uz.pdp.online.m6l4apprestjwttask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.m6l4apprestjwttask.entity.Outcome;

import java.util.List;

public interface OutcomeRepository extends JpaRepository<Outcome,Integer> {
    List<Outcome>findAllByFromCardId_Username(String fromCardId_username);

}
