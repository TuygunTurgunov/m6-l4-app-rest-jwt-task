package uz.pdp.online.m6l4apprestjwttask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.online.m6l4apprestjwttask.entity.Card;
import uz.pdp.online.m6l4apprestjwttask.entity.Income;
import uz.pdp.online.m6l4apprestjwttask.entity.Outcome;
import uz.pdp.online.m6l4apprestjwttask.payload.History;
import uz.pdp.online.m6l4apprestjwttask.repository.CardRepository;
import uz.pdp.online.m6l4apprestjwttask.repository.IncomeRepository;
import uz.pdp.online.m6l4apprestjwttask.repository.OutcomeRepository;

import java.util.*;

@Service
public class HistoryService {
    @Autowired
    IncomeRepository incomeRepository;
    @Autowired
    OutcomeRepository outcomeRepository;
    @Autowired
    CardRepository cardRepository;

    public List<History> getOutIn(String username) {
        List<Outcome> allByFromCardId_username = outcomeRepository.findAllByFromCardId_Username(username);
        List<Income> allByFromCardId_username1 = incomeRepository.findAllByToCardId_Username(username);

        List<History> histories;
        histories = new ArrayList<>(
                Collections.singletonList(
                        new History(allByFromCardId_username, allByFromCardId_username1)
                )
        );
        return histories;

    }

    public List<Income> getAllIncome(String username) {
        if (username!=null){
            return incomeRepository.findAllByToCardId_Username(username);}
        return null;
    }

    public List<Outcome>getAllOutcome(String username){
        if (username!=null){
            return outcomeRepository.findAllByFromCardId_Username(username);}
        return null;

    }

    public Income getOneIncome(Integer id,String username){
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if (optionalIncome.isPresent()){
            Income income = optionalIncome.get();
            Card toCardId = income.getToCardId();
            if (toCardId.getUsername().equals(username))
            return income;
        }
        return null;

    }



    public Outcome getOneOutcome(Integer id,String username){
        Optional<Outcome> optionalOutcome = outcomeRepository.findById(id);
        if (optionalOutcome.isPresent()){
            Outcome outcome = optionalOutcome.get();
            Card fromCardId = outcome.getFromCardId();
            if (fromCardId.getUsername().equals(username))
                return outcome;
        }
        return null;

    }



}
