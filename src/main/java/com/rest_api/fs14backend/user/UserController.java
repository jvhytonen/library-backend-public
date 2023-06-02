package com.rest_api.fs14backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://127.0.0.1:5173/")
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<User> addOne(@RequestBody User user) {
        User newUser = userService.addOne(user);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/")
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{id}")
    public User getUserById(@PathVariable("id") UUID id) {
        return userService.findById(id);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable("id") UUID id) {
        User userToBeDeleted = userService.delete(id);
        return ResponseEntity.ok(userToBeDeleted);
    }
}
