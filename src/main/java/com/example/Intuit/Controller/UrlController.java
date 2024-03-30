package com.example.Intuit.Controller;

import com.example.Intuit.model.ErrorDto;
import com.example.Intuit.model.ResponseDto;
import com.example.Intuit.model.Url;
import com.example.Intuit.model.UrlDto;
import com.example.Intuit.service.UrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/generated/TinyURL")
    public ResponseEntity<?> generateTinyUrl(@RequestBody UrlDto urlDto) {
        Url repoUrl = urlService.generateTinyUrl(urlDto);

        if (repoUrl != null) {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setLongUrl(repoUrl.getLongUrl());
            responseDto.setExpirationDate(repoUrl.getExpirationDate());
            responseDto.setTinyUrl(repoUrl.getTinyUrl());
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }

        ErrorDto urlErrorResponseDto = new ErrorDto();
        urlErrorResponseDto.setStatus("404");
        urlErrorResponseDto.setError("please try again later!!");
        return new ResponseEntity<ErrorDto>(urlErrorResponseDto, HttpStatus.OK);

    }

    @GetMapping("/{tinyUrl}")
    public ResponseEntity<?> redirection(@PathVariable String tinyUrl, HttpServletResponse response) throws IOException {

        if (StringUtils.isEmpty(tinyUrl)) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setError("Invalid Url");
            errorDto.setStatus("400");
            return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.OK);
        }
        Url repoUrl = urlService.encodedTinyUrl(tinyUrl);

        if (repoUrl == null) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setError("URL is not found in DB");
            errorDto.setStatus("400");
            return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.OK);
        }

        if (repoUrl.getExpirationDate().isBefore(LocalDateTime.now())) {
            urlService.deleteTinyUrl(repoUrl);
            ErrorDto errorDto = new ErrorDto();
            errorDto.setError("Trying to fetch expired URL");
            errorDto.setStatus("200");
            return new ResponseEntity<ErrorDto>(errorDto, HttpStatus.OK);
        }

        response.sendRedirect(repoUrl.getLongUrl());
        return null;
    }

    @DeleteMapping("/deleteUrl/{id}")
    public ResponseEntity<?> deleteUrlById(@PathVariable Long id) {
        try {
            urlService.deleteUrlById(id);
            return new ResponseEntity<>("URL with ID " + id + " deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setStatus("500");
            errorDto.setError("Internal Server Error: " + e.getMessage());
            return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
