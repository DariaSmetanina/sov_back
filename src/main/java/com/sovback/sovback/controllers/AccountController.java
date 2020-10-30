package com.sovback.sovback.controllers;

import com.sovback.sovback.model.Access;
import com.sovback.sovback.model.Account;
import com.sovback.sovback.model.Notification;
import com.sovback.sovback.model.Organization;
import com.sovback.sovback.repositories.AccessRepository;
import com.sovback.sovback.repositories.AccountRepository;
import com.sovback.sovback.repositories.NotificationRepository;
import com.sovback.sovback.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/acc")
public class AccountController {

    @Autowired
    OrganizationRepository orgRep;

    @Autowired
    AccountRepository accRep;

    @Autowired
    AccessRepository acsRep;

    @Autowired
    NotificationRepository notRep;


    private List<Long> user_to_list_organization_id(long user_id){
        List<Long> list=new ArrayList<>();
        List<Access> acs = acsRep.findAllByUser(user_id);
        for(Access a:acs){
            list.add(a.getOrganization());
        }
        List<Organization> org = orgRep.findAllById(list);
        List<Long> list2=new ArrayList<>();
        for(Organization o:org){
            list2.add(o.getId());
        }
        return list2;
    }

    private Long inn_to_idOrg(String inn){
        Organization o=orgRep.findOneByInn(inn);
        return o.getId();
    }

    @GetMapping("/account")
    public List<Map<String, String>> getAccount() {
        List<Map<String, String>> accList = new ArrayList();

        List<Account> acc = accRep.findAllByOrganizationInOrderByDateDesc(user_to_list_organization_id(2));
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

    @GetMapping("/personalA")
    public List<Map<String, String>> getFourAccount(final String inn) {
        List<Map<String, String>> accList = new ArrayList();

        List<Account> acc = accRep.findFirst4ByOrganizationOrderByDateDesc(inn_to_idOrg(inn));
        for (Account a : acc){
            accList.add(new HashMap<String, String>(){{
                put("number", a.getNumber());
                put("date", a.getDate());
                put("amount", String.valueOf(a.getAmount()));
                put("status", a.getStatus());
                }});}
        return accList;
    }

    @GetMapping("/personalN")
    public List<Map<String, String>> getTwoNotifications(final String inn) {
        List<Map<String, String>> notList = new ArrayList();

        List<Notification> not = notRep.findFirst2ByOrganizationOrderByDateDesc(inn_to_idOrg(inn));
        for (Notification n : not){
            notList.add(new HashMap<String, String>(){{
                put("date", n.getDate());
                put("importance", n.getImportance());
                put("text", n.getText());
            }});}
        return notList;
    }

    @GetMapping("/notification")
    public List<Map<String, String>> getNotifications() {
        List<Map<String, String>> notList = new ArrayList();

        List<Notification> not = notRep.findAllByOrganizationInOrderByDateDesc(user_to_list_organization_id(1));
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

    @GetMapping("/organizations")
    public List<Map<String, String>> getOrganizations() {
        List<Map<String, String>> orgList = new ArrayList();

        List<Long> list=new ArrayList<>();
        List<Access> acs = acsRep.findAllByUser(1);
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
