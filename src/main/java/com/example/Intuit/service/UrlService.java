package com.example.Intuit.service;


import com.example.Intuit.model.Url;
import com.example.Intuit.model.UrlDto;
import org.springframework.stereotype.Service;

@Service
public interface UrlService {
    public Url generateTinyUrl(UrlDto urlDto);

    public Url persistTinyUrl(Url url);

    public Url encodedTinyUrl(String url);

    public void deleteTinyUrl(Url url);

    public void deleteUrlById(long id);
}
