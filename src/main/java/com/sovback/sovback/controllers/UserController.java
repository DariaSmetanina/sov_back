package com.sovback.sovback.controllers;

import com.sovback.sovback.common.CommonMethods;
import com.sovback.sovback.model.*;
import com.sovback.sovback.payload.request.AddOrg;
import com.sovback.sovback.payload.request.AddOrgToUser;
import com.sovback.sovback.payload.request.userSettingsRequest;
import com.sovback.sovback.payload.response.MessageResponse;
import com.sovback.sovback.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository usgRep;

    @Autowired
    AccessRepository acsRep;

    @Autowired
    OrganizationRepository orgRep;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/settings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> userSettings(@RequestBody final userSettingsRequest USRequest) {
        String message="";
        User userS=usgRep.findOneById(CommonMethods.getCurrentUserId());

        if(USRequest.getEmail()!=""){
            userS.setEmail(USRequest.getEmail());
            message=message+"Email изменен;";
        }
        if(Optional.ofNullable(USRequest.getNewPassword()).isPresent()){
            if(Optional.ofNullable(USRequest.getNewPasswordCopy()).isPresent()) {
                if(Optional.ofNullable(USRequest.getOldPassword()).isPresent()) {
                      if(!encoder.matches(encoder.encode(USRequest.getOldPassword()), userS.getPassword())){
                        userS.setPassword(encoder.encode(USRequest.getNewPassword()));
                          message=message+" Пароль изменен;";
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

    @DeleteMapping("/settings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteOrgForUser(String inn){

        Access as=acsRep.findByOrganizationAndAndUser(orgRep.findOneByInn(inn).getId(), CommonMethods.getCurrentUserId());
        acsRep.delete(as);
        return ResponseEntity.ok(new MessageResponse("Вы больше не имеете доступа к этой организации"));
    }

    @PostMapping("/addOrgToUser")
    @PreAuthorize("hasAuthority('admin')")

    public ResponseEntity<?> addOrgToUser(@RequestBody final AddOrgToUser AddOrg) throws UsernameNotFoundException  {

        User user = usgRep.findByUsername(AddOrg.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким именем не найден"));

        Access as=new Access();
        as.setOrganization(orgRep.findOneByInn(AddOrg.getInn()).getId());
        as.setUser(user.getId());

        acsRep.save(as);
        return ResponseEntity.ok(new MessageResponse("Пользователю дан доступ"));
    }

    @PostMapping("/addOrg")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> addOrg(@RequestBody final AddOrg AddOrg) {

        Organization or=new Organization();

        or.setName(AddOrg.getName());
        or.setInn(AddOrg.getInn());
        or.setDirector(AddOrg.getDirector());
        orgRep.save(or);
        return ResponseEntity.ok(new MessageResponse("Организация добавлена"));
    }


    @GetMapping("/organization")
    @PreAuthorize("isAuthenticated()")
    public Organization getOrg(String inn){
        return orgRep.findOneByInn(inn);
    }



}
