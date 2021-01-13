package com.sovback.sovback.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class StorageService {

    private static final String FILEPATH="C:\\Users\\Dasha\\Desktop\\sovback\\files\\";

    private static String getUserPath(String inn){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return FILEPATH+"user_files\\"+calendar.get(Calendar.YEAR)+"\\"+((calendar.get(Calendar.MONTH)/3)+1)+"\\"+inn;
    }

    private static String getNewsPath(String date){
        return FILEPATH+"news\\"+date;
    }

    public static void saveUserFile(String inn, MultipartFile file){
        saveFile(getUserPath(inn),file);
    }

    public static void saveNewsFile(String date, MultipartFile file){
        saveFile(getNewsPath(date),file);
    }


    private static void saveFile(String path, MultipartFile file){

        try {
            byte[] bytes = file.getBytes();

            String rootPath = path;
            File dir = new File(rootPath);
            if (!dir.exists()) dir.mkdirs();

            File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();

        } catch (Exception e) {}
    }

    public static List<File> getListFiles(String inn){

        File dir = new File(getUserPath(inn));
        File[] arrFiles = dir.listFiles();
        return Arrays.asList(arrFiles);

    }

    public static File getFile(String inn, String filename){
        return new File(getUserPath(inn) +"\\"+filename);
    }

}
