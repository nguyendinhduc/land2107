package com.t3h.buoi12.controller;

import com.t3h.buoi12.service.CrawlCovidWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrawlWebController {
    @Autowired
    private CrawlCovidWebService service;
    @GetMapping("/crawl/covid")
    public Object crawlCovid(){
        return service.getAll();
    }
}
