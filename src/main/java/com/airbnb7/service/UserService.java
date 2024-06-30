package com.airbnb7.service;

import com.airbnb7.dto.LoginDto;
import com.airbnb7.dto.PropertyUserDto;
import com.airbnb7.entity.PropertyUser;
import com.airbnb7.repository.PropertyUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private JWTService jwtService;
    private PropertyUserRepository propertyUserRepository;

    public UserService(PropertyUserRepository propertyUserRepository,JWTService jwtService) {
        this.propertyUserRepository = propertyUserRepository;
        this.jwtService=jwtService;
    }

    public PropertyUser addUser(PropertyUserDto propertyUserDto){

        PropertyUser user=new PropertyUser();
        user.setFirstName(propertyUserDto.getFirstName());
        user.setLastName(propertyUserDto.getLastName());
        user.setUsername(propertyUserDto.getUsername());
        user.setEmail(propertyUserDto.getEmail());
        user.setPassword(BCrypt.hashpw(propertyUserDto.getPassword(),BCrypt.gensalt(10)));
        user.setUserRole(propertyUserDto.getUserRole());

        PropertyUser savedUser = propertyUserRepository.save(user);
        return savedUser;

    }

    public String verifyLogin(LoginDto loginDto){
        Optional<PropertyUser> opUser = propertyUserRepository.findByUsername(loginDto.getUsername());

        if(opUser.isPresent()){
            PropertyUser propertyUser = opUser.get();

           if(BCrypt.checkpw(loginDto.getPassword(),propertyUser.getPassword())){
              return jwtService.generateToken(propertyUser);
           }

        }
        return null;

    }
}
