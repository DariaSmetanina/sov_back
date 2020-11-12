package com.sovback.sovback.controllers;

import com.sovback.sovback.model.Access;
import com.sovback.sovback.model.Account;
import com.sovback.sovback.model.Notification;
import com.sovback.sovback.model.Organization;
import com.sovback.sovback.payload.request.LoginRequest;
import com.sovback.sovback.payload.response.JwtResponse;
import com.sovback.sovback.repositories.AccessRepository;
import com.sovback.sovback.repositories.AccountRepository;
import com.sovback.sovback.repositories.NotificationRepository;
import com.sovback.sovback.repositories.OrganizationRepository;
import com.sovback.sovback.security.jwt.JwtUtils;
import com.sovback.sovback.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {

    @Autowired
    AccessRepository acsRep;

    @Autowired
    NotificationRepository notRep;

    @Autowired
    OrganizationRepository orgRep;

    @GetMapping("/personal")
    @PreAuthorize("isAuthenticated()")
    public List<Map<String, String>> getTwoNotifications(final String inn) {
        List<Map<String, String>> notList = new ArrayList();

        List<Notification> not = notRep.findFirst2ByOrganizationOrderByDateDesc(CommonMethods.inn_to_idOrg(orgRep,inn));
        for (Notification n : not){
            notList.add(new HashMap<String, String>(){{
                put("date", n.getDate());
                put("importance", n.getImportance());
                put("text", n.getText());
            }});}
        return notList;
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public List<Map<String, String>> getNotifications() {
        List<Map<String, String>> notList = new ArrayList();

        List<Notification> not = notRep.findAllByOrganizationInOrderByDateDesc(CommonMethods.get_users_org(acsRep,orgRep,CommonMethods.getCurrentUserId()));
        for (Notification n : not){
            notList.add(new HashMap<String, String>(){{
                put("id", Long.toString(n.getId()));
                put("date", n.getDate());
                put("importance", n.getImportance());
                put("text", n.getText());
                put("organization", String.valueOf(orgRep.findOneById(n.getOrganization()).getName()));
            }});}

        return notList;
    }

}
