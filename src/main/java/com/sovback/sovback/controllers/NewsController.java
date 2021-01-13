package com.sovback.sovback.controllers;
import com.sovback.sovback.common.StorageService;
import com.sovback.sovback.model.News;
import com.sovback.sovback.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
@ComponentScan("com.sovback.sovback.repositories.NewsRepository")
@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    NewsRepository newRep;

    @GetMapping("/one")
    public News getNew(final long ID) {
        News news = newRep.findOneById(ID);
        return news;
    }

    @GetMapping("/")
    @ResponseBody
    public List<Map<String, String>> getAll() {
        return newsList_to_map(newRep.findFirst30ByOrderByDateDesc());
    }

    @GetMapping("/three")
    @ResponseBody
    public List<Map<String, String>> getThreeNews() {

        return newsList_to_map(newRep.findFirst3ByOrderByDateDesc());
    }

    private List<Map<String, String>> newsList_to_map(List<News> news) {
        List<Map<String, String>> newsList = new ArrayList();
        if(!news.isEmpty()) {
            for (News n : news) {
                newsList.add(new HashMap<String, String>() {{
                    put("id", Long.toString(n.getId()));
                    put("date", n.getDate());
                    put("importance", n.getImportance());
                    put("title", n.getTitle());
                    put("mainPart", n.getMain_part());
                }});
            }
        }
        else{
            newsList.add(new HashMap<String, String>() {{
                put("title", ":с");
                put("mainPart", "Здесь пока ничего нет");
            }});
        }
        return newsList;
    }

    @PostMapping("/addNews")
    public @ResponseBody
    String createNews(@RequestParam("title") String title, @RequestParam("urgency") String urgency,
                      @RequestParam("mainPart") String mainPart, @RequestParam("longPart") String longPart,
                      @RequestParam("file") MultipartFile file) {

        News ns = new News();

        String d = new java.sql.Date(new java.util.Date().getTime()).toString();
        ns.setTitle(title);
        ns.setDate(d);
        ns.setImportance(urgency);
        ns.setMain_part(mainPart);
        ns.setText(longPart);
        ns.setFiles(file.getOriginalFilename());

        newRep.save(ns);

        if (!file.isEmpty()) {
            StorageService.saveNewsFile(d, file);
        }
        return "<b>Новость успешно добавлена</b> <a href=\"/\">Вернуться назад</a>";
    }


}
