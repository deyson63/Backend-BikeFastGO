package com.upc.bikefastgo.service;


import com.upc.bikefastgo.dto.AuthenticationResponse;
import com.upc.bikefastgo.dto.LoginRequest;
import com.upc.bikefastgo.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthService {
    public abstract AuthenticationResponse register(RegisterRequest registerRequest);

    public abstract AuthenticationResponse login(LoginRequest loginRequest);

    public void validateRegisterRequest(RegisterRequest registerRequest);
    public void existsUserByEmail(RegisterRequest registerRequest);
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
