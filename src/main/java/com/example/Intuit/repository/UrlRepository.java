package com.example.Intuit.repository;


import com.example.Intuit.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    public Url findByTinyUrl(String tinyUrl);


}