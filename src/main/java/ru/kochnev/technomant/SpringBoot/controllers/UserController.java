package ru.kochnev.technomant.SpringBoot.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kochnev.technomant.SpringBoot.models.MyException;
import ru.kochnev.technomant.SpringBoot.models.User;
import ru.kochnev.technomant.SpringBoot.modelsDTO.UserDTO;
import ru.kochnev.technomant.SpringBoot.services.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public void createNewUser(@RequestBody @Valid User user) {
        userService.save(user);
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable("id") String id) throws MyException {
        return userService.getById(id);
    }

    @PutMapping
    public void update(@RequestBody @Valid User user) {
        userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String id) {
        userService.deleteById(id);
    }
}