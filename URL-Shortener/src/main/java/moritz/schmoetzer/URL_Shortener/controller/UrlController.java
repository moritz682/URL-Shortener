package moritz.schmoetzer.URL_Shortener.controller;

import jakarta.servlet.http.HttpServletResponse;
import moritz.schmoetzer.URL_Shortener.dto.UrlInputDTO;
import moritz.schmoetzer.URL_Shortener.models.Url;
import moritz.schmoetzer.URL_Shortener.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(@Autowired UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten") // CREATE
    public ResponseEntity shortenURL(@RequestBody UrlInputDTO url){
        try {
            Url shortUrl = urlService.createNewShortURL(url.getUrl());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(shortUrl);
        } catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("400 - Bad request\nAn error occurred whilst validating the input data!");
        }
    }

    @GetMapping("/shorten/{shortCode}") // READ
    public ResponseEntity getURL(@PathVariable String shortCode){
        Url url = urlService.getUrl(shortCode, true);
        if (url != null){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(url);
        } else{
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("404 - Not found\nThe requested URL does not exist!");
        }
    }

    @PutMapping("/shorten/{shortCode}") // UPDATE
    public ResponseEntity updateURL(@PathVariable String shortCode, @RequestBody UrlInputDTO url){
        if (urlService.getUrl(shortCode, false) != null){ // Check if the URL with the short-code exists in the DB
            if (url.getUrl() != null){ // Check if the URL-parameter is correct
                urlService.updateUrl(shortCode, url.getUrl());
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(urlService.getUrl(shortCode, true));
            } else{
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("400 - Bad request\nAn error occurred whilst validating the input data!");
            }
        } else{
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("404 - Not found\nThe requested URL does not exist!");
        }
    }

    @DeleteMapping("/shorten/{shortCode}") // DELETE
    public void DeleteURL(@PathVariable String shortCode, HttpServletResponse response){
        if (urlService.getUrl(shortCode, true) != null){
            urlService.deleteUrl(shortCode);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else{
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @GetMapping("/shorten/{shortCode}/stats")
    public ResponseEntity getURLStats(@PathVariable String shortCode){
        Url url = urlService.getUrl(shortCode, true);
        if (url != null){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(url);
        } else{
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("404 - Not found\nThe requested URL does not exist!");
        }
    }
}