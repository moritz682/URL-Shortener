package moritz.schmoetzer.URL_Shortener.controller;

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
            return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
        } catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("400 - Bad request\nAn error occurred whilst validating the input data!");
        }
    }

    @GetMapping("/shorten/{shortCode}") // READ
    public Url getURL(@PathVariable String shortCode){
        return urlService.getUrl(shortCode);
    }
}