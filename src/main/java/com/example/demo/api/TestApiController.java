package com.example.demo.api;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class TestApiController {

    @GetMapping("/normal")
    public ResponseEntity<?> normal(){
        return new ResponseEntity<>("Im normal normal", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<?> admin(){
        return new ResponseEntity<>("Im normal admin", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<?> pu(){
        return new ResponseEntity<>("Im normal user", HttpStatus.OK);
    }

}
