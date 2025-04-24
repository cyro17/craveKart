package com.cyro.craveKart.controller;

import com.cyro.craveKart.config.JwtProvider;
import com.cyro.craveKart.exception.UserException;
import com.cyro.craveKart.model.Cart;
import com.cyro.craveKart.model.USER_ROLE;
import com.cyro.craveKart.model.User;
import com.cyro.craveKart.repository.UserRepository;
import com.cyro.craveKart.request.LoginRequest;
import com.cyro.craveKart.response.AuthResponse;
import com.cyro.craveKart.service.CartRepository;
import com.cyro.craveKart.service.CustomUserDetailsService;
import com.cyro.craveKart.service.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtProvider jwtProvider;
    private CustomUserDetailsService customUserDetailsService;
    private CartRepository cartRepository;
    private UserService userService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtProvider jwtProvider,
                          CustomUserDetailsService customUserDetails,
                          CartRepository cartRepository,
                          UserService userService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.customUserDetailsService = customUserDetails;
        this.cartRepository=cartRepository;
        this.userService=userService;

    }

    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck(){
        return  new ResponseEntity<>("Health check ok !! ", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();
        USER_ROLE role = user.getRole();

        User isEmailExist = userRepository.findByEmail(email);
        if(isEmailExist != null)
            throw new UserException("Email is already user with another account !! ");

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setFullName(fullName);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setRole(role);

        User savedUser = userRepository.save(createdUser);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        Cart savedCart = cartRepository.save(cart);


        List<GrantedAuthority> authorities = new ArrayList<>();
        Authentication authentication = new UsernamePasswordAuthenticationToken(email,
                password, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Registration success! ");
        authResponse.setRole(savedUser.getRole());

        return  new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> siginIn(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login Success !! ");
        authResponse.setJwt(token);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String rolename = authorities.isEmpty() ?
                null : authorities.iterator().next().getAuthority();

        authResponse.setRole(USER_ROLE.valueOf(rolename));
        return  new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password){
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if(userDetails == null) {
            throw new BadCredentialsException("Invalid Username or Password !! ");
        }

        if( !passwordEncoder.matches(password, userDetails.getPassword())){
            throw  new BadCredentialsException("Invalid Username or Password !! ");
        }

        return  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
