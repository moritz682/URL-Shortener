package moritz.schmoetzer.URL_Shortener.repositories;

import moritz.schmoetzer.URL_Shortener.models.Url;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface UrlRepository extends CrudRepository<Url, Long> {

    @Query("SELECT * FROM urls WHERE short_code = :shortCode")
    Url findUrlByShortCode(String shortCode);

    @Modifying
    @Query("UPDATE urls SET access_count = access_count + 1 WHERE short_code = :shortCode")
    void incrementAccessCount(String shortCode);

    @Modifying
    @Query("UPDATE urls SET url = :url, updated_at = :timestamp  WHERE short_code = :shortCode")
    void updateUrlByShortCode(String shortCode, Timestamp timestamp, String url);

    @Modifying
    @Query("DELETE FROM urls WHERE short_code = :shortCode")
    void deleteUrlByShortCode(String shortCode);
}