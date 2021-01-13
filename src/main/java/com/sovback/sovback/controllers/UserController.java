package com.sovback.sovback.controllers;

import com.sovback.sovback.common.CommonMethods;
import com.sovback.sovback.common.StorageService;
import com.sovback.sovback.model.*;
import com.sovback.sovback.payload.request.AddOrg;
import com.sovback.sovback.payload.request.AddOrgToUser;
import com.sovback.sovback.payload.request.userSettingsRequest;
import com.sovback.sovback.payload.response.MessageResponse;
import com.sovback.sovback.repositories.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/user")
//@CrossOrigin("*")
public class UserController {

    private final String FILEPATH = "C:\\Users\\Dasha\\Desktop\\sovback\\files\\";

    @Autowired
    UserRepository usgRep;

    @Autowired
    AccessRepository acsRep;

    @Autowired
    OrganizationRepository orgRep;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/test")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> posttest(@RequestBody byte[] us){
        return ResponseEntity.ok(new MessageResponse("test ok"));
    }

    @PostMapping("/settings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> userSettings(@RequestBody final userSettingsRequest USRequest) throws NullPointerException {
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

    @PostMapping("/addFile")
    public @ResponseBody
    String addFile(@RequestParam("inn") String inn,
                      @RequestParam(value = "file") MultipartFile file) {

        if (!file.isEmpty()) { StorageService.saveUserFile(inn, file);}
        return "<b>Файл успешно добавлен</b> <a href=\"/cloud/"+inn+"\">Вернуться назад</a>";
    }

    @GetMapping("/files")
    @PreAuthorize("isAuthenticated()")
    public List<Map<String, String>> getFiles(String inn){

        List<Map<String, String>> flList=new ArrayList<>();

        List<File> lst = StorageService.getListFiles(inn);

        for (File f : lst) {
            flList.add(new HashMap<String, String>() {{
                put("name", (f.getName()));
            }});
        }
        return flList;
    }

    @GetMapping("/download2")
    public @ResponseBody byte[] getFile(@RequestParam("fileName") String fileName, @RequestParam("inn") String inn, HttpServletResponse response) throws IOException {

        File file = StorageService.getFile(inn,fileName);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setContentLength((int) file.length());
        return FileUtils.readFileToByteArray(file);
    }

    @DeleteMapping("/settings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteOrgForUser(String inn) throws UsernameNotFoundException{

        Access as = acsRep.findByOrganizationAndAndUser(orgRep.findOneByInn(inn).getId(), CommonMethods.getCurrentUserId()).orElseThrow(() -> new UsernameNotFoundException("Такой организации нет " + inn));;
        acsRep.delete(as);
        return ResponseEntity.ok(new MessageResponse("Вы больше не имеете доступа к этой организации"));
    }


}
