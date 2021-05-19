package uz.pdp.online.m6l4apprestjwttask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.m6l4apprestjwttask.entity.Card;
import uz.pdp.online.m6l4apprestjwttask.payload.CardDto;
import uz.pdp.online.m6l4apprestjwttask.payload.Result;
import uz.pdp.online.m6l4apprestjwttask.service.CardService;
import uz.pdp.online.m6l4apprestjwttask.service.CutToken;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/card")
public class CardController {
    @Autowired
    CardService cardService;
    @Autowired
    CutToken cutToken;

    @PostMapping
    public HttpEntity<?> addCard(@Valid @RequestBody CardDto cardDto, HttpServletRequest request){
        String tokenWithBearer = request.getHeader("Authorization");
        String usernameWithoutBearerAndToken = cutToken.cutToken(tokenWithBearer);
        Result result = cardService.addCard(cardDto,usernameWithoutBearerAndToken);
        return ResponseEntity.status(result.getSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(result);
    }

 

    @GetMapping
    public HttpEntity<?>getPageCards(HttpServletRequest request){

        String tokenWithBearer = request.getHeader("Authorization");
        String usernameWithoutBearerAndToken = cutToken.cutToken(tokenWithBearer);
        List<Card> allCards = cardService.getAllCards(usernameWithoutBearerAndToken);
        return ResponseEntity.status(allCards!=null?200:409).body(allCards);


    }
    
    @GetMapping("/{id}")
    public HttpEntity<?> getOneCard(@PathVariable Integer id,HttpServletRequest request){
        String tokenWithBearer = request.getHeader("Authorization");
        String usernameWithoutBearerAndToken = cutToken.cutToken(tokenWithBearer);

        Card oneCard = cardService.getOneCard(id,usernameWithoutBearerAndToken);
        return  ResponseEntity.status(oneCard!=null?200:409).body(oneCard);
    }

    @PutMapping("/{id}")
    public HttpEntity<?>editCard(@Valid @RequestBody CardDto cardDto,@PathVariable Integer id,
                                 HttpServletRequest request){
        String tokenWithBearer = request.getHeader("Authorization");
        String usernameWithoutBearerAndToken = cutToken.cutToken(tokenWithBearer);
        Result result = cardService.editCard(cardDto, id, usernameWithoutBearerAndToken);
        return ResponseEntity.status(result.getSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?>delete(@PathVariable Integer id,HttpServletRequest request){
        String tokenWithBearer = request.getHeader("Authorization");
        String usernameWithoutBearerAndToken = cutToken.cutToken(tokenWithBearer);
        Result result = cardService.delete(id,usernameWithoutBearerAndToken);
        return ResponseEntity.status(result.getSuccess()?HttpStatus.FORBIDDEN:HttpStatus.NOT_FOUND).body(result);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }



}
