package com.rest_api.fs14backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public User addOne(@RequestBody User user) {
        return userService.addOne(user);
    }

    @GetMapping("/")
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{id}")
    public Optional<User> getUserById(@PathVariable("id") UUID id) {
        return Optional.ofNullable(userService.findById(id));
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUserById(@PathVariable("id") UUID id) {
        userService.deleteById(id);
    }
}
