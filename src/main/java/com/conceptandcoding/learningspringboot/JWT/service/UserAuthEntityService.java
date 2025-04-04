package com.conceptandcoding.learningspringboot.JWT.service;

import com.conceptandcoding.learningspringboot.JWT.entity.UserAuthEntity;
import com.conceptandcoding.learningspringboot.JWT.repository.UserAuthEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthEntityService implements UserDetailsService {

    @Autowired
    private UserAuthEntityRepository userAuthEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthEntityRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public UserDetails save(UserAuthEntity userAuthEntity) {
        return userAuthEntityRepository.save(userAuthEntity);
    }

}
