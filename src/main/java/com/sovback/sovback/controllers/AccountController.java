package com.sovback.sovback.controllers;

import com.sovback.sovback.mail.mSender;
import com.sovback.sovback.model.Access;
import com.sovback.sovback.model.Account;
import com.sovback.sovback.model.Organization;
import com.sovback.sovback.repositories.AccessRepository;
import com.sovback.sovback.repositories.AccountRepository;
import com.sovback.sovback.repositories.NotificationRepository;
import com.sovback.sovback.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    OrganizationRepository orgRep;

    @Autowired
    AccountRepository accRep;

    @Autowired
    AccessRepository acsRep;

    @Autowired
    NotificationRepository notRep;

    @Autowired
    JavaMailSender mailSender;


    @GetMapping("/account")
    @PreAuthorize("hasAuthority('accountant')")
    public List<Map<String, String>> getAccount() {
        List<Map<String, String>> accList = new ArrayList();

        List<Account> acc = accRep.findAllByOrganizationInOrderByDateDesc(CommonMethods.get_users_org(acsRep,orgRep,CommonMethods.getCurrentUserId()));
        for (Account a : acc){
            accList.add(new HashMap<String, String>(){{
                put("id", Long.toString(a.getId()));
                put("number", a.getNumber());
                put("date", a.getDate());
                put("amount", String.valueOf(a.getAmount()));
                put("status", a.getStatus());
                put("comment", a.getComment());
                put("organization", String.valueOf(orgRep.findOneById(a.getOrganization()).getName()));
            }});}
        return accList;
    }

    @GetMapping("/personal")
    @PreAuthorize("isAuthenticated()")
    public List<Map<String, String>> getFourAccount(final String inn) {
        List<Map<String, String>> accList = new ArrayList();

        List<Account> acc = accRep.findFirst4ByOrganizationOrderByDateDesc(CommonMethods.inn_to_idOrg(orgRep,inn));
        for (Account a : acc){
            accList.add(new HashMap<String, String>(){{
                put("number", a.getNumber());
                put("date", a.getDate());
                put("amount", String.valueOf(a.getAmount()));
                put("status", a.getStatus());

                }});}

//        List<String> lst=new ArrayList();
//        lst.add("smetanina.03@mail.ru");
//        lst.add("8881143@rambler.ru");
//        lst.add("amrasambarusa@gmail.com");
//        mSender.sendEmailToReference(mailSender,lst);

        return accList;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/organizations")
    public List<Map<String, String>> getOrganizations() {
        List<Map<String, String>> orgList = new ArrayList();

        List<Long> list=new ArrayList<>();
        List<Access> acs = acsRep.findAllByUser(CommonMethods.getCurrentUserId());
        for(Access a:acs){
            list.add(a.getOrganization());
        }
        List<Organization> org = orgRep.findAllById(list);

        for (Organization o : org){
            orgList.add(new HashMap<String, String>(){{
                put("name", o.getName());
                put("inn", o.getInn());
                put("director", o.getDirector());
            }});}

        return orgList;
    }

}
