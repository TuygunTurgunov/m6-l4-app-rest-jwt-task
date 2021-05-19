package uz.pdp.online.m6l4apprestjwttask.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.online.m6l4apprestjwttask.entity.Income;
import uz.pdp.online.m6l4apprestjwttask.entity.Outcome;
import uz.pdp.online.m6l4apprestjwttask.payload.History;
import uz.pdp.online.m6l4apprestjwttask.service.CutToken;
import uz.pdp.online.m6l4apprestjwttask.service.HistoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @Autowired
    CutToken cutToken;

    @GetMapping()
    public HttpEntity<?> getOutIn(HttpServletRequest request) {
        String tokenWithBearer = request.getHeader("Authorization");
        String usernameWithoutBearerAndToken = cutToken.cutToken(tokenWithBearer);

        List<History> historyList = historyService.getOutIn(usernameWithoutBearerAndToken);

        return ResponseEntity.ok(historyList);

    }

    @GetMapping("/income")
    public HttpEntity<?>getAllIncome(HttpServletRequest request){
        String tokenWithBearer = request.getHeader("Authorization");
        String usernameWithoutBearerAndToken = cutToken.cutToken(tokenWithBearer);
        List<Income> allIncome = historyService.getAllIncome(usernameWithoutBearerAndToken);
        return ResponseEntity.status(allIncome!=null?200:404).body(allIncome);
    }

    @GetMapping("/outcome")
    public HttpEntity<?>getAllOutcome(HttpServletRequest request){
        String tokenWithBearer = request.getHeader("Authorization");
        String usernameWithoutBearerAndToken = cutToken.cutToken(tokenWithBearer);
        List<Outcome> allOutcome = historyService.getAllOutcome(usernameWithoutBearerAndToken);
        return ResponseEntity.status(allOutcome!=null?200:404).body(allOutcome);
    }


    @GetMapping("/income/{id}")
    public HttpEntity<?>getOneIncome(@PathVariable Integer id,HttpServletRequest request){
        String tokenWithBearer = request.getHeader("Authorization");
        String usernameWithoutBearerAndToken = cutToken.cutToken(tokenWithBearer);
        Income oneIncome = historyService.getOneIncome(id, usernameWithoutBearerAndToken);
        return ResponseEntity.status(oneIncome!=null?200:404).body(oneIncome);
    }

    @GetMapping("/outcome/{id}")
    public HttpEntity<?>getOneOutcome(@PathVariable Integer id,HttpServletRequest request){
        String tokenWithBearer = request.getHeader("Authorization");
        String usernameWithoutBearerAndToken = cutToken.cutToken(tokenWithBearer);

        Outcome oneOutcome = historyService.getOneOutcome(id, usernameWithoutBearerAndToken);
        return ResponseEntity.status(oneOutcome!=null?200:404).body(oneOutcome);
    }




}
