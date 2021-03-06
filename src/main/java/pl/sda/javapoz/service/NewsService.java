package pl.sda.javapoz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.sda.javapoz.model.Link;
import pl.sda.javapoz.model.News;
import pl.sda.javapoz.model.Pagination;
import pl.sda.javapoz.model.Product;
import pl.sda.javapoz.repository.NewsRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by RENT on 2017-03-22.
 */
@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public List<News> findAllNews(){
        List<News> newsList = new ArrayList<>();
        newsRepository.findAll().forEach(x->newsList.add(x));
        return newsList;
    }

    public News findNewsById(Long id){
        News news = newsRepository.findOne(id);
        return news;
    }

    public List<News> findFiveNews(Integer page) {
        List<News> listOfFiveNews = newsRepository.findByNews(page);
        return listOfFiveNews;
    }

    public Pagination getPaginationForPage(Integer page) {
       long pages= ((newsRepository.count()-1)/5)+1;
        Pagination pagination=new Pagination();
        pagination.setNextPage(page<pages);
        pagination.setPreviousPage(page!=1);
        pagination.setPage(page);
        return pagination;
    }

    public Set<Link> findAllTag(){

        List<News> newses = findAllNews();
        Set<Link> collect = newses.stream()
                .map(e -> e.getTag())
                .map(e -> new Link(StringUtils.capitalize(e), "/?tag=" + e))
                .collect(Collectors.toSet());

        return collect;
    }

    public List<News> findNewsByTag(String tag){

        List<News> newsByTag = newsRepository.findNewsByTag(tag);


        return newsByTag;


    }



}
