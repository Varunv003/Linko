package com.linko.linko.controller;

import com.linko.linko.dto.ClickEventDTO;
import com.linko.linko.dto.UrlMappingDTO;
import com.linko.linko.models.User;
import com.linko.linko.service.UrlMappingService;
import com.linko.linko.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
public class UrlMappingController {

    private UserService userService;
    private UrlMappingService urlMappingService;

    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createShortUrl(@RequestBody Map<String, String> request, Principal principal){

        String originalUrl = request.get("original_url");

        User user = userService.findByUsername(principal.getName());
        UrlMappingDTO urlMappingDTO = urlMappingService.createShortUrl(originalUrl, user);
        return ResponseEntity.ok(urlMappingDTO);


    }

    @GetMapping("/myurls")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UrlMappingDTO>> getUserUrls(Principal principal){
        User user = userService.findByUsername(principal.getName());
        List<UrlMappingDTO> urls = urlMappingService.getUrlsByUser(user);
        return ResponseEntity.ok(urls);
    }

    @PutMapping("/shorten/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateShortUrl(@PathVariable String shortUrl, @RequestBody Map<String, String> request, Principal principal){

        String newOriginalUrl = request.get("original_url");
        User user = userService.findByUsername(principal.getName());
        UrlMappingDTO urlMappingDTO = urlMappingService.updateOriginalUrl(shortUrl, newOriginalUrl, user);

        if(urlMappingDTO != null){
            return ResponseEntity.ok(urlMappingDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteShortUrl(@PathVariable String shortUrl, Principal principal){
        User user = userService.findByUsername(principal.getName());
        boolean deleted = urlMappingService.deleteShortUrl(shortUrl, user);

        if(deleted){
            return ResponseEntity.ok("Short Url is deleted from the database");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClickEventDTO>> getUrlAnalytics(@PathVariable String shortUrl,
                                                               @RequestParam("startDate") String startDate,
                                                               @RequestParam("endDate") String endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
        List<ClickEventDTO> clickEventDTOS = urlMappingService.getClickEventsByDate(shortUrl, start, end);
        return ResponseEntity.ok(clickEventDTOS);
    }

    @GetMapping("/analytics/total_clicks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<LocalDate, Long>> getTotalClicksByDate(Principal principal,
                                                                     @RequestParam("startDate") String startDate,
                                                                     @RequestParam("endDate") String endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        User user = userService.findByUsername(principal.getName());
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        Map<LocalDate, Long> totalClicks = urlMappingService.getTotalClickByUserAndDate(user, start, end);

        return ResponseEntity.ok(totalClicks);
    }




}
