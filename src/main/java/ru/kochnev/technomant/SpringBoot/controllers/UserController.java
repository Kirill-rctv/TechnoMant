package ru.kochnev.technomant.SpringBoot.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kochnev.technomant.SpringBoot.models.MyException;
import ru.kochnev.technomant.SpringBoot.models.Pojo;
import ru.kochnev.technomant.SpringBoot.models.User;
import ru.kochnev.technomant.SpringBoot.modelsDTO.UserDTO;
import ru.kochnev.technomant.SpringBoot.services.UserService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<Pojo> createNewUser(@RequestBody @Valid User user) {
        Pojo response;
        try {
            UUID uuid = userService.save(user).getId();
            return new ResponseEntity<>(new Pojo(true, uuid, "OK"), HttpStatus.OK);
        } catch (Error e) {
            return new ResponseEntity<>(new Pojo(false, null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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