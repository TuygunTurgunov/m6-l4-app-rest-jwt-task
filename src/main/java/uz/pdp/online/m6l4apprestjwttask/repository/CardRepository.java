package uz.pdp.online.m6l4apprestjwttask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.m6l4apprestjwttask.entity.Card;

public interface CardRepository extends JpaRepository<Card,Integer> {

    boolean existsByNumber(Long number);

    boolean existsByNumberAndIdNot(Long number, Integer id);



}
