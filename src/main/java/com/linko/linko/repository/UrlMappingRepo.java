package com.linko.linko.repository;

import com.linko.linko.models.UrlMapping;
import com.linko.linko.models.User;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UrlMappingRepo extends JpaRepository<UrlMapping, Long> {


    UrlMapping findByShortUrl(String shortUrl);
    @Transactional
    @Modifying
    @Query("UPDATE UrlMapping u SET u.clickCount = u.clickCount + 1 WHERE u.shortUrl = :shortUrl")
    int incrementClickCount(String shortUrl);

    List<UrlMapping> findByUser(User user);
}
