package com.sovback.sovback.controllers;

import com.sovback.sovback.model.Access;
import com.sovback.sovback.model.Organization;
import com.sovback.sovback.repositories.AccessRepository;
import com.sovback.sovback.repositories.OrganizationRepository;

import java.util.ArrayList;
import java.util.List;

public class CommonMethods {

    public static List<Long> get_users_org(AccessRepository acsRep, OrganizationRepository orgRep, long user_id){
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

    public static Long inn_to_idOrg(OrganizationRepository orgRep, String inn){
        Organization o=orgRep.findOneByInn(inn);
        return o.getId();
    }


}
