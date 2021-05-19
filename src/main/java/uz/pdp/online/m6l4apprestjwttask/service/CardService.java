package uz.pdp.online.m6l4apprestjwttask.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.online.m6l4apprestjwttask.entity.Card;
import uz.pdp.online.m6l4apprestjwttask.payload.CardDto;
import uz.pdp.online.m6l4apprestjwttask.payload.Result;
import uz.pdp.online.m6l4apprestjwttask.repository.CardRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;

    //POST
    public Result addCard(CardDto cardDto, String username) {
        boolean existsByNumber = cardRepository.existsByNumber(cardDto.getNumber());
        if (existsByNumber)
            return new Result("card number already exists", false);
        Card card = new Card();
        card.setBalance(cardDto.getBalance());
        card.setNumber(cardDto.getNumber());
        card.setUsername(username);
        Timestamp date = new Timestamp(System.currentTimeMillis()+cardDto.getExpireDate());
        card.setExpireDate(date);
        cardRepository.save(card);
        return new Result("Card saved ", true);
    }


    //GET ALL
    public List<Card> getAllCards(String username) {

        List<Card> all = cardRepository.findAll();
        all.removeIf(card -> !card.getUsername().equals(username));
        return all;



    }

    //GET ONE
    public Card getOneCard(Integer id,String username) {

        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isPresent()&&optionalCard.get().getUsername().equals(username))
            return optionalCard.get();
        return null;




    }


    //EDIT
    public Result editCard(CardDto cardDto, Integer id, String username) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (!optionalCard.isPresent())
            return new Result("card not found by id", false);

        if (!optionalCard.get().getUsername().equals(username))
            return new Result("token username not equals with id username.",false);

        boolean existsByNumberAndIdNot = cardRepository.existsByNumberAndIdNot(cardDto.getNumber(), id);
        if (existsByNumberAndIdNot)
            return new Result("such kind of card number already exists", false);


        Card card = optionalCard.get();
        card.setBalance(cardDto.getBalance());
        card.setNumber(cardDto.getNumber());
        card.setUsername(username);
        Timestamp date = new Timestamp(System.currentTimeMillis()+cardDto.getExpireDate());
        card.setExpireDate(date);
        cardRepository.save(card);
        return new Result("Card edited", true);

    }


    //DELETE
    public Result delete(Integer id,String username) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if(!optionalCard.isPresent())
            return new Result("card not found by id",false);
        if (!optionalCard.get().getUsername().equals(username))
            return new Result("token username not equals with id username.",false);
        try {
            cardRepository.deleteById(id);
            return new Result("card deleted", true);

        } catch (Exception e) {
            return new Result("not found by id", false);
        }
    }
}
