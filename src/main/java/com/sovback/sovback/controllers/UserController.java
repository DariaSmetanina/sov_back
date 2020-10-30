package com.sovback.sovback.controllers;

import com.sovback.sovback.model.*;
import com.sovback.sovback.payload.request.userSettingsRequest;
import com.sovback.sovback.payload.response.MessageResponse;
import com.sovback.sovback.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository usgRep;

    @PostMapping("/settings")
    public ResponseEntity<?> userSettings(@RequestBody final userSettingsRequest USRequest) {
        String message="";
        User userS=usgRep.findOneById(1);

        if(Optional.ofNullable(USRequest.getEmail()).isPresent()){
            userS.setEmail(USRequest.getEmail());
            message=message+"Email изменен;";
        }
        if(Optional.ofNullable(USRequest.getNewPassword()).isPresent()){
            if(Optional.ofNullable(USRequest.getNewPasswordCopy()).isPresent()) {
                if(Optional.ofNullable(USRequest.getOldPassword()).isPresent()) {
                    if(USRequest.getOldPassword()==userS.getPassword()){
                        userS.setPassword(USRequest.getOldPassword());
                    }
                    else{
                        message=message+" Пароль не изменен, старый пароль не верный";
                    }
                }
                else{
                    message=message+" Пароль не изменен, введите старый пароль";
                }
            }
            else{message=message+" Пароль не изменен, повторите новый пароль";}
        }

        usgRep.save(userS);
        return ResponseEntity.ok(new MessageResponse(message));
    }


}
