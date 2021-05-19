package uz.pdp.online.m6l4apprestjwttask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MyUserAuthService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> userList=new ArrayList<>(
                Arrays.asList(
                        new User("pdp",passwordEncoder.encode("pdpUz"),new ArrayList<>()),
                        new User("abad",passwordEncoder.encode("abadUz"),new ArrayList<>()),
                        new User("olcha",passwordEncoder.encode("olchaUz"),new ArrayList<>()),
                        new User("olx",passwordEncoder.encode("olxUz"),new ArrayList<>())
                )
        );


        for (User user : userList) {
            if (user.getUsername().equals(username))
                return user;
        }
        throw new UsernameNotFoundException("Username listdan topilmadi");

    }
}
