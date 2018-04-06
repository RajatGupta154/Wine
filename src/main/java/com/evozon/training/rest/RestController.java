package com.evozon.training.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reviews")
public class RestController {

    @Autowired
    private RestService restService;

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Review> getReview(@PathVariable Integer productId) {
        List<Review> reviews = restService.getReviews(productId);
        return reviews;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        restService.addReview(review);
        return new ResponseEntity<Review>(review, HttpStatus.OK);
    }

    @RequestMapping(value = "/{reviewId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteReview(@PathVariable Integer reviewId) {
        restService.deleteReview(reviewId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> updateReview(@RequestBody Review review) {
        restService.updateReview(review);
        return new ResponseEntity<Review>(review, HttpStatus.OK);
    }
}
