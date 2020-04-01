package com.microservicedemo.moviecatalogservice.resources;

import com.microservicedemo.moviecatalogservice.models.Rating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface RatingsClient {


    @GetMapping("/ratingsdata/movies/{movieId}")
    Rating getRating(@PathVariable("movieId") String movieId);


}
