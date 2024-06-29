package com.upc.bikefastgo.controller;
import com.upc.bikefastgo.dto.AuthenticationResponse;
import com.upc.bikefastgo.dto.LoginRequest;
import com.upc.bikefastgo.dto.RegisterRequest;
import com.upc.bikefastgo.model.Bicycle;
import com.upc.bikefastgo.repository.UserRepository;
import com.upc.bikefastgo.service.AuthService;
import com.upc.bikefastgo.service.BicycleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "AuthentificationController", description = "the authentification API")
@RestController
@RequestMapping("/api/bikefastgo/v1/auth")
public class AuthentificationController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BicycleService bicycleService;


    // URL: http://localhost:8080/api/bikefastgo/v1/auth/register
    // Method: POST
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerStudent(@RequestBody RegisterRequest request) {
        authService.existsUserByEmail(request);
        authService.validateRegisterRequest(request);
        AuthenticationResponse registeredUser = authService.register(request);
        return new ResponseEntity<AuthenticationResponse>(registeredUser, HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/bikefastgo/v1/auth/login
    // Method: POST
    @Transactional(readOnly = true)
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {

        AuthenticationResponse loggedUser = authService.login(request);
        // Agrega el ID del usuario a la respuesta


        return new ResponseEntity<AuthenticationResponse>(loggedUser, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }

    @Transactional(readOnly = true)
    @GetMapping("/bicycle")
    public ResponseEntity<List<Bicycle>> getAllBicycles() {

        System.out.println("getAllBicycles");
        return new ResponseEntity<List<Bicycle>>(bicycleService.getAllBicycles(), HttpStatus.OK);
    }








}
