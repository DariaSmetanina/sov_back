package com.sovback.sovback.controllers;
import com.sovback.sovback.model.News;
import com.sovback.sovback.payload.request.AddNewsRequest;
import com.sovback.sovback.payload.response.MessageResponse;
import com.sovback.sovback.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

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
    public List<Map<String, String>> getAll() {
        return newsList_to_map(newRep.findFirst30ByOrderByDateDesc());
    }

    @GetMapping("/three")
    public List<Map<String, String>> getThreeNews() {
        return newsList_to_map(newRep.findFirst3ByOrderByDateDesc());
    }

    private List<Map<String, String>> newsList_to_map(List<News> news){
        List<Map<String, String>> newsList = new ArrayList();
        for (News n : news) {
            newsList.add(new HashMap<String, String>() {{
                put("id", Long.toString(n.getId()));
                put("date", n.getDate());
                put("importance", n.getImportance());
                put("title", n.getTitle());
                put("mainPart", n.getMain_part());
            }});
        }
        return newsList;
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('accountant')")
    public ResponseEntity<?> addNews(@RequestBody final AddNewsRequest AddNewsRequest) {

        News ns = new News();

        ns.setTitle(AddNewsRequest.getTitle());
        ns.setDate(new java.sql.Date(new java.util.Date().getTime()).toString());
        ns.setImportance(AddNewsRequest.getImportance());
        ns.setMain_part(AddNewsRequest.getMainPart());
        ns.setText(AddNewsRequest.getText());
        //newS.setFiles();

        newRep.save(ns);

        return ResponseEntity.ok(new MessageResponse("Новость успешно создана"));
    }
}
