package com.sovback.sovback.controllers;

    import com.sovback.sovback.model.News;
    import com.sovback.sovback.repositories.NewsRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/api/news")
    public class NewsController {

        @Autowired
        NewsRepository newRep;

        @GetMapping("/all")
        public News getNew(final long ID) {
           // long i=Long.getLong(ID);

            News news=newRep.findOneById(ID);
            return news;
        }


    }
