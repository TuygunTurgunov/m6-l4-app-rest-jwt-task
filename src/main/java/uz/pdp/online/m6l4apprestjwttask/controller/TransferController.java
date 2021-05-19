package uz.pdp.online.m6l4apprestjwttask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.online.m6l4apprestjwttask.payload.TransferDto;
import uz.pdp.online.m6l4apprestjwttask.payload.Result;
import uz.pdp.online.m6l4apprestjwttask.service.CutToken;
import uz.pdp.online.m6l4apprestjwttask.service.TransferService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/card/transfer")
public class TransferController {
  @Autowired
  TransferService transferService;

  @Autowired
  CutToken cutToken;

  @PostMapping
  public HttpEntity<?>add(@RequestBody TransferDto transferDto, HttpServletRequest request){
    String tokenWithBearer = request.getHeader("Authorization");
    String usernameWithoutBearerAndToken = cutToken.cutToken(tokenWithBearer);
    Result result = transferService.add(transferDto, usernameWithoutBearerAndToken);
    return ResponseEntity.status(result.getSuccess()? 200:409).body(result);
  }




}
