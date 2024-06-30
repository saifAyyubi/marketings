package com.airbnb7.controller;

import com.airbnb7.dto.LoginDto;
import com.airbnb7.dto.PropertyUserDto;
import com.airbnb7.dto.TokenResponse;
import com.airbnb7.entity.PropertyUser;
import com.airbnb7.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody PropertyUserDto propertyUserDto){
        PropertyUser opUser = userService.addUser(propertyUserDto);
        if(opUser!=null){
            return new ResponseEntity<>("Registration is Successful", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto){
        String token = userService.verifyLogin(loginDto);
        if(token!=null){
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setToken(token);
            return new ResponseEntity<>(tokenResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Credentials",HttpStatus.UNAUTHORIZED);


    }

    @GetMapping("/profile")
    public PropertyUser getCurrentUserProfile(@AuthenticationPrincipal PropertyUser user){
        return user;
    }
}
