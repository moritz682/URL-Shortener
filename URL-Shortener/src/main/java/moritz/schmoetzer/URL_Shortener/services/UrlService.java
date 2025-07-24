package moritz.schmoetzer.URL_Shortener.services;

import moritz.schmoetzer.URL_Shortener.models.Url;
import moritz.schmoetzer.URL_Shortener.repositories.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(@Autowired UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public void createNewShortURL(String url){
        String shortCode;
        while (true){
            shortCode = UUID.randomUUID().toString().substring(0,6); // shortCode is limited to 6 chars
            if(urlRepository.findUrlByShortCode(shortCode).isEmpty()){ // Check if shortCode is unique
                break;
            }
        }

        Url urlEntity = new Url(null,
                url,
                shortCode,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                0);

        urlRepository.save(urlEntity);
    }
}