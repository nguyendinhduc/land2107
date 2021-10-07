package com.t3h.buoi12.service;

import com.t3h.buoi12.model.entity.Category;
import com.t3h.buoi12.model.response.CategoryResponse;
import com.t3h.buoi12.model.response.ContentCovid;
import com.t3h.buoi12.repository.CategoryRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CrawlCovidWebService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Object getAll(){
        List<CategoryResponse> res = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            if ( "Home".equals(category.getName())){
                res.add(crawlCategoryHome(category.getHtmlLink(), category.getName()));
            }else {
                res.add(crawlOther(category.getHtmlLink(), category.getName()));
            }
        }
        crawlOther("https://covid19.gov.vn/du-phong-dieu-tri.htm", "");
        return res;
    }

    public CategoryResponse crawlCategoryHome(String link, String name) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryName(name);
        categoryResponse.setContentCovids(new ArrayList<>());
        try {
            Document document = Jsoup.connect(link).get();
            Element main = document.getElementsByClass("main").get(0);
            Element container = main.getElementsByClass("container").get(1);
            Element eImg = container.select("img").get(0);
            String title = eImg.attr("alt");
            String imageLink = eImg.attr("src");
            String content = container.getElementsByClass("box-focus-sapo").text();
            categoryResponse.getContentCovids().add(
                    new ContentCovid(title, content, imageLink)
            );

            Elements es= main.getElementsByClass("home__sn-flex");
            for (Element el : es.get(0).getElementsByClass("box-stream-item")) {
                String titleL = el.select("img").attr("alt");
                String imageL = el.select("img").attr("src");
                String contentL = el.getElementsByClass("box-stream-sapo").text();
                categoryResponse.getContentCovids().add(
                        new ContentCovid(titleL, contentL, imageL)
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categoryResponse;
    }

    public CategoryResponse crawlOther(String link, String name){
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryName(name);
        categoryResponse.setContentCovids(new ArrayList<>());
        Document document = null;
        try {
            document = Jsoup.connect(link).get();
            String id = document.getElementById("hdZoneId").attr("value");
            int index = 1;
            while (true){
                String childLink = "https://covid19.gov.vn/timelinelist/"+id+"/"+index+".htm";
                document = Jsoup.connect(childLink).get();
                Elements els = document.body().getElementsByClass("box-stream-item");
                if ( els.size() == 0){
                    break;
                }
                for (Element el : els) {
                    String image = el.select("img").attr("src");
                    String title = el.select("img").attr("alt");
                    String content = el.getElementsByClass("box-stream-sapo").text();
                    categoryResponse.getContentCovids().add(
                            new ContentCovid(title, content, image)
                    );

                }
                index+=1;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return categoryResponse;

    }
}
