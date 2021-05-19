package uz.pdp.online.m6l4apprestjwttask.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.online.m6l4apprestjwttask.service.MyUserAuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    MyUserAuthService myUserAuthService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {


        //1) REQUESTDAN TOKEN NI OLISH.
        String keluvchiToken = httpServletRequest.getHeader("Authorization");//postman da headers dagi authorization key i

        //2) TOKEN BORLIGINI VA TOKEN NING BOSHLANISHI BEARER BO'LISHINI TEKSHIRYAPMIZ.
        if (keluvchiToken!=null && keluvchiToken.startsWith("Bearer")) {
            //3)BEARER NIK ESIB AYNAN TOKEN NI O'ZINI OLIDIK
            keluvchiToken=keluvchiToken.substring(7);

            //4) TOKEN NI VALIDATSIYADAN O'TKAZDIK (TOKENNI BUZILMAGANLIGINI MUDDATI O'TMAGANLIGINI VA HK LARNI TEKSHIRADI)
            boolean validateToken = jwtProvider.validateToken(keluvchiToken);
            if (validateToken){

                //5) TOKEN NI ICHIDAN USER NI OLDIK
                String username = jwtProvider.getUserNameFromToken(keluvchiToken);

                //6)USERNAME ORQALI USERDETAILS  NI OLDIK
                UserDetails userDetails = myUserAuthService.loadUserByUsername(username);

                //7)USERDETAILS ORQALI AUTHENTICATION YARATIB OLDIK
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());

                System.out.println(SecurityContextHolder.getContext().getAuthentication());

                //8)SYSTEMAGA KIM KIRGANLIGINI O'RNATDIK
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            }

        }

        //3)FILTER CHAIN boshqa filterlar spring ni oziniki
        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }






}
