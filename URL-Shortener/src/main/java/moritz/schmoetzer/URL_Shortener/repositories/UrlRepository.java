package moritz.schmoetzer.URL_Shortener.repositories;

import moritz.schmoetzer.URL_Shortener.models.Url;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlRepository extends CrudRepository<Url, Long> {

    @Query("SELECT * FROM urls WHERE short_code = :shortCode")
    List<Url> findUrlByShortCode(String shortCode);
}