package com.example.Intuit.service;

import com.example.Intuit.model.Url;
import com.example.Intuit.model.UrlDto;
import com.example.Intuit.repository.UrlRepository;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class ServiceImplementation implements UrlService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceImplementation.class);
    @Autowired
    private UrlRepository urlRepository;

    @Override
    public Url generateTinyUrl(UrlDto urlDto) {

        if (StringUtils.isNotEmpty(urlDto.getUrl())) {
            String encodedUrl = encodeUrl(urlDto.getUrl());
            Url persistUrl = new Url();
            persistUrl.setCreationDate(LocalDateTime.now());
            persistUrl.setLongUrl(urlDto.getUrl());
            persistUrl.setTinyUrl(encodedUrl);
            persistUrl.setExpirationDate(getExpirationDate(urlDto.getExpirationDate(), persistUrl.getCreationDate()));
            Url repoUrl = persistTinyUrl(persistUrl);
            if(repoUrl != null)
                return repoUrl;

            return null;
        }
        return null;
    }

    private String encodeUrl(String url) {
        String encodedUrl = "";
        LocalDateTime time = LocalDateTime.now();
        encodedUrl = Hashing.murmur3_32()
                .hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
                .toString();
        return encodedUrl;
    }


    private LocalDateTime getExpirationDate(String expirationDate, LocalDateTime creationDate) {
        if (StringUtils.isBlank(expirationDate)) {
            return creationDate.plusSeconds(180);
        }
        LocalDateTime repoExpirationDate = LocalDateTime.parse(expirationDate);
        return repoExpirationDate;
    }


    @Override
    public Url persistTinyUrl(Url url) {
        Url repoUrl = urlRepository.save(url);
        return repoUrl;
    }

    @Override
    public Url encodedTinyUrl(String url) {
        Url repoUrl = urlRepository.findByTinyUrl(url);
        return repoUrl;
    }

    @Override
    public void deleteTinyUrl(Url url) {
        urlRepository.deleteById(url.getId());

    }

    @Override
    public void deleteUrlById(long id) {

        urlRepository.deleteById(id);
    }


}
