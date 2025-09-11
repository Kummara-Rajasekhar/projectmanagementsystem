package com.Rajasekhar.controller;


import com.Rajasekhar.config.JwtProvider;
import com.Rajasekhar.model.User;
import com.Rajasekhar.repository.UserRepository;
import com.Rajasekhar.request.LoginRequest;
import com.Rajasekhar.response.AuthResponse;
import com.Rajasekhar.service.CustomUserDetailsImpl;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private CustomUserDetailsImpl customUserDetails;



    @PostMapping("/signup")
    public ResponseEntity<User> createUserHandler(@RequestBody User user) throws Exception {
        User isUserExist=userRepository.findByEmail(user.getEmail());
        if(isUserExist!=null){
            throw new Exception("email already exist with another account");

        }

        User createdUser=new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setFullName(user.getFullName());

        User savedUser=userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt= JwtProvider.generateToken(authentication);

        AuthResponse response=new AuthResponse();
        response.setMessage("signup successful");
        response.setJwt(jwt);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

    }


    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> signing(@RequestBody LoginRequest loginRequest ) throws Exception {

        String username=loginRequest.getEmail();
        String password=loginRequest.getPassword();
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt= JwtProvider.generateToken(authentication);

        AuthResponse response=new AuthResponse();
        response.setMessage("signup successful");
        response.setJwt(jwt);
        Object savedUser;
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private UsernamePasswordAuthenticationToken authenticate(String username, String password) throws Exception {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        if(userDetails==null){
            throw new Exception("Invalid UserName");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new Exception("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }


}
