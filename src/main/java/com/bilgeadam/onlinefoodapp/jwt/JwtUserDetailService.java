package com.bilgeadam.onlinefoodapp.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetailService implements UserDetailsService {

    private static List<JwtUserDetails> userList = new ArrayList<>();

    static {
        userList.add(new JwtUserDetails(1L,"mesutcan",
                "$2b$10$zL5pz4.zAhDej36/yMJggeZ07UeU8eOuPbjq2J2I.nJsyWWM6K7KS","RESTAURANT_OWNER"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<JwtUserDetails> firstUser = userList.stream().filter(user->user.getUsername().equals(username)).findFirst();
        if(!firstUser.isPresent()) {
            throw new UsernameNotFoundException(String.format("USERNAME NOT FOUND '%s',", username));
        }
            return firstUser.get();
    }
}
