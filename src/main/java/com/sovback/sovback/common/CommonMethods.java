package com.sovback.sovback.common;

import com.sovback.sovback.model.Access;
import com.sovback.sovback.model.Organization;
import com.sovback.sovback.repositories.AccessRepository;
import com.sovback.sovback.repositories.OrganizationRepository;
import com.sovback.sovback.security.services.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

    public static long getCurrentUserId() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
        return user.getId();
    }

    public static void saveFile(String path, MultipartFile file){

            try {
                byte[] bytes = file.getBytes();

                String rootPath = path;
                File dir = new File(rootPath);
                if (!dir.exists())
                    dir.mkdirs();

                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();


            } catch (Exception e) {}
    }


}
