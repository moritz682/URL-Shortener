package moritz.schmoetzer.URL_Shortener.controller;

import moritz.schmoetzer.URL_Shortener.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(@Autowired UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public void shortenURL(@RequestBody String url){
        urlService.createNewShortURL(url);
    }
}