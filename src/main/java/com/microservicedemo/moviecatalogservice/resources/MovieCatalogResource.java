package com.microservicedemo.moviecatalogservice.resources;

import com.microservicedemo.moviecatalogservice.models.CatalogItem;
import com.microservicedemo.moviecatalogservice.models.Movie;
import com.microservicedemo.moviecatalogservice.models.Rating;
import com.microservicedemo.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {


        //RestTemplate restTemplate = new RestTemplate();

        //get all rated movie ids
        /*List<Rating> ratings = Arrays.asList(
          new Rating("1234", 4),
          new Rating("4567", 3)
        );*/
        //RatingsClient.get
        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);

        return ratings.getUserRating().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
            /*Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();*/
            return new CatalogItem( movie.getName(),  "desc", rating.getRating());

        })
         .collect(Collectors.toList());

        // fir each movie id, call movie info service and get details

        //put all them together
        /*return Collections.singletonList(
                new CatalogItem( "Transformers",  "Test", 4)
        );*/
    }
}
