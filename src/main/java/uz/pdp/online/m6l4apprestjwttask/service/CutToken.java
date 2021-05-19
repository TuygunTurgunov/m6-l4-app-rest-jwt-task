package uz.pdp.online.m6l4apprestjwttask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uz.pdp.online.m6l4apprestjwttask.security.JwtProvider;

@Component
public class CutToken {
    @Autowired
    JwtProvider jwtProvider;

    public String cutToken(String token){
      if (token!=null&&token.startsWith("Bearer")){
          token=token.substring(7);
          boolean validateToken = jwtProvider.validateToken(token);
          if (validateToken){
              return jwtProvider.getUserNameFromToken(token);
          }

      }
      return null;



    }
}
