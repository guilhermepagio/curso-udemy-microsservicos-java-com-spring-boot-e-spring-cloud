package io.github.guilhermepagio.ms.springcloud.hruser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.guilhermepagio.ms.springcloud.hruser.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
