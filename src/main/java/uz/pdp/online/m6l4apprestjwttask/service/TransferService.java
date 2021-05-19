package uz.pdp.online.m6l4apprestjwttask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.online.m6l4apprestjwttask.entity.Card;
import uz.pdp.online.m6l4apprestjwttask.entity.Income;
import uz.pdp.online.m6l4apprestjwttask.entity.Outcome;
import uz.pdp.online.m6l4apprestjwttask.payload.TransferDto;
import uz.pdp.online.m6l4apprestjwttask.payload.Result;
import uz.pdp.online.m6l4apprestjwttask.repository.CardRepository;
import uz.pdp.online.m6l4apprestjwttask.repository.IncomeRepository;
import uz.pdp.online.m6l4apprestjwttask.repository.OutcomeRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class TransferService {
  @Autowired
  OutcomeRepository outcomeRepository  ;

  @Autowired
  IncomeRepository incomeRepository;

  @Autowired
  CardRepository cardRepository;


  public Result add(TransferDto transferDto, String token){

    Optional<Card> optionalCard = cardRepository.findById(transferDto.getFromCardId());
    if (!optionalCard.isPresent())
      return new Result("from card not found",false);

    if (!optionalCard.get().getUsername().equals(token))
      return new Result("token username and from card username not equals",false);

    Optional<Card> optionalCard2 = cardRepository.findById(transferDto.getToCardId());
    if (!optionalCard2.isPresent())
      return new Result("to card not found",false);


    Date date=new Date(System.currentTimeMillis());
    if (optionalCard.get().getExpireDate().before(date))
      return new Result("problem in from card expression date",false);
    if (optionalCard2.get().getExpireDate().before(date))
      return new Result("problem in to card expression date ",false);


    Double commissionAmount= transferDto.getAmount()*0.1;
    Double totalTransferAmount= transferDto.getAmount()+commissionAmount;
   if (totalTransferAmount<=optionalCard.get().getBalance()){

     Outcome outcome=new Outcome();
     outcome.setFromCardId(optionalCard.get());
     outcome.setToCardId(optionalCard2.get());
     outcome.setAmount(transferDto.getAmount());
     outcome.setCommissionAmount(commissionAmount);
     outcomeRepository.save(outcome);

     Card card = optionalCard.get();
     Double balance = card.getBalance()-totalTransferAmount;
     card.setBalance(balance);
     cardRepository.save(card);

     Card card2 = optionalCard2.get();
     Double balance2 = card2.getBalance()+ transferDto.getAmount();
     card2.setBalance(balance2);
     cardRepository.save(card2);

     Income income=new Income();
     income.setAmount(transferDto.getAmount());
     income.setFromCardId(card);
     income.setToCardId(card2);
     incomeRepository.save(income);
     return new Result("transfer successfully ended",true);
   }
   return new Result("transfer error",false);
  }












}
