package com.library.Controller;


import com.library.model.User;
import com.library.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Email already exists!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        userRepository.save(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully!");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User loginData) {
        Map<String, String> response = new HashMap<>();
        User user = userRepository.findByEmail(loginData.getEmail());

        if (user == null) {
            response.put("status", "User not found");
        } else {
            System.out.println("DB Password: [" + user.getPassword() + "]");
            System.out.println("Login Password: [" + loginData.getPassword() + "]");
            if (user.getPassword() != null && user.getPassword().trim().equals(loginData.getPassword().trim())) {
                response.put("status", "Login Successful");
                response.put("role", user.getRole());
            } else {
                response.put("status", "Incorrect Password");
            }
        }

        return response;
    }

	public BCryptPasswordEncoder getbCryptPasswordEncoder() {
		return bCryptPasswordEncoder;
	}

	public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

    

    }

