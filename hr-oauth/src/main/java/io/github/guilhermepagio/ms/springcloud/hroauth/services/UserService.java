package io.github.guilhermepagio.ms.springcloud.hroauth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.guilhermepagio.ms.springcloud.hroauth.entities.User;
import io.github.guilhermepagio.ms.springcloud.hroauth.feignclients.UserFeignClient;

@Service
public class UserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserFeignClient userFeignClient;

    public UserService(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    public User findByEmail(String email) {
        final User user = userFeignClient.findByEmail(email).getBody();

        if (user == null) {
            logger.error("Email not found: {}", email);
            throw new IllegalArgumentException("Email not found");
        }

        logger.info("User found by email: {}", email);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userFeignClient.findByEmail(email).getBody();

        if (user == null) {
            logger.error("Email not found: {}", email);
            throw new UsernameNotFoundException("Email not found");
        }

        logger.info("User found by email: {}", email);
        return user;
    }

}
