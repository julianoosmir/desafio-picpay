package desafio.picpay.controller;

import desafio.picpay.domain.user.User;
import desafio.picpay.domain.user.UserDTO;
import desafio.picpay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody UserDTO user){
        return this.userService.save(user);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return this.userService.getAllUsers();
    }
}
