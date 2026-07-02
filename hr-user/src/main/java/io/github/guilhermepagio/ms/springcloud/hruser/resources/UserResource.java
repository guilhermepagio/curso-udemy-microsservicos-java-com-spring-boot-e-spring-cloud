package io.github.guilhermepagio.ms.springcloud.hruser.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.guilhermepagio.ms.springcloud.hruser.entities.User;
import io.github.guilhermepagio.ms.springcloud.hruser.repositories.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    private final UserRepository userRepository;

    UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/configs")
    public ResponseEntity<Void> configs() {
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        final List<User> list = userRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable @NonNull Long id) {
        final User user = userRepository.findById(id).get();

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<User> findByEmail(@RequestParam @NonNull String email) {
        final User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }
}