package com.englishcenter.userservice.controller;

//import java.time.Instant;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.englishcenter.userservice.entity.AuthenticationResponse;
import com.englishcenter.userservice.entity.LoginDto;
import com.englishcenter.userservice.entity.RegisterDto;
import com.englishcenter.userservice.entity.Users;
import com.englishcenter.userservice.repository.UserRepository;
import com.englishcenter.userservice.service.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

	@Value("${security.jwt.secret-key}")
	private String jwtSecretKey;

	@Value("${security.jwt.issuer}")
	private String jwtIssuer;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserServiceImpl userService;

//	@Autowired
//  private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

//	@CrossOrigin("http://localhost:3000")
	@GetMapping("/profile")
	public ResponseEntity<Object> profile(Authentication auth) {
		var response = new HashMap<String, Object>();
		response.put("Username", auth.getName());
		response.put("Authorities", auth.getAuthorities());

		var user = userRepository.findByUsername(auth.getName());
		response.put("User", user);

		return ResponseEntity.ok(response);
	}
	
//	@CrossOrigin("http://localhost:3000")
    @GetMapping("/username")
    public ResponseEntity<Users> getAccountByUsername(@RequestParam String username) {
        Users users = userService.findByUsername(username);
        if (users == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

//	@CrossOrigin("http://localhost:3000")
	@PostMapping("/register")
	public Users register(@Valid @RequestBody RegisterDto registerDto) {
		return userService.save(registerDto);
	}

//	public ResponseEntity<Object> register(@Valid @RequestBody RegisterDto registerDto, BindingResult result) {
//		if (result.hasErrors()) {
//			var errorsList = result.getAllErrors();
//			var errorMap = new HashMap<String, String>();
//			
//			for (int i=0; i<errorsList.size(); i++) {
//				var error = (FieldError) errorsList.get(i);
//				errorMap.put(error.getField(), error.getDefaultMessage());
//			}
//			return ResponseEntity.badRequest().body(errorMap);
//		}
//		
//		var bCryptEncoder = new BCryptPasswordEncoder();
//		Users user = new Users();
//		user.setId(registerDto.getId());
//		user.setUsername(registerDto.getUsername());
//		user.setPassword(bCryptEncoder.encode(registerDto.getPassword()));
//		user.setRole(registerDto.getRole());
//		
//		try {
//			var otherUser = userRepository.findByUsername(registerDto.getUsername());
//			if (otherUser != null) {
//				return ResponseEntity.badRequest().body("username already used");
//			}
//			userRepository.save(user);
//			
//			String jwtToken = createJwtToken(user);
//			
//			var response = new HashMap<String, Object>();
//			response.put("token", jwtToken);
//			response.put("user", user);
//			
//			return ResponseEntity.ok(response);
//		} 
//		catch (Exception e) {
//			System.out.println("There is an exception: ");
//			e.printStackTrace();
//		}
//		
//		return ResponseEntity.badRequest().body("Error");
//	}

//	@CrossOrigin("http://localhost:3000")
	@PostMapping("/login")
//	public ResponseEntity<Object> login(@Valid @RequestBody LoginDto loginDto, BindingResult result) {
//		if (result.hasErrors()) {
//			var errorsList = result.getAllErrors();
//			var errorMap = new HashMap<String, String>();
//
//			for (int i = 0; i < errorsList.size(); i++) {
//				var error = (FieldError) errorsList.get(i);
//				errorMap.put(error.getField(), error.getDefaultMessage());
//			}
//
//			return ResponseEntity.badRequest().body(errorMap);
//		}
//
//		try {
//			authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
//			Users users = userRepository.findByUsername(loginDto.getUsername());
//			String jwtToken = createJwtToken(users);
//			var response = new HashMap<String, Object>();
//			response.put("token", jwtToken);
//			response.put("user", users);
//
//			return ResponseEntity.ok(response);
//		}
//
//		catch (Exception e) {
//			System.out.println("There is an exception: ");
//			e.printStackTrace();
//		}
//
//		return ResponseEntity.badRequest().body("Bad username or password");
//	}

	public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
		        try {
		            Authentication authentication = authenticationManager.authenticate(
		                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
		            SecurityContextHolder.getContext().setAuthentication(authentication);
		            Users users = userService.findByUsername(loginDto.getUsername());
		            String role = users.getRole();
		            AuthenticationResponse response = new AuthenticationResponse();
		            response.setUsername(users.getUsername()); //lấy username từ tài khoản
		            response.setRole(role); //lấy role từ tài khoản
		            return ResponseEntity.ok(response);
		        } catch (Exception e) {
		            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		        }
  }

//	private String createJwtToken(Users users) {
//		Instant now = Instant.now();
//
//		JwtClaimsSet claims = JwtClaimsSet.builder().issuer(jwtIssuer).issuedAt(now)
//				.expiresAt(now.plusSeconds(24 * 3600)).subject(users.getUsername()).claim("role", users.getRole())
//				.build();
//
//		var encoder = new NimbusJwtEncoder(new ImmutableSecret<>(jwtSecretKey.getBytes()));
//		var params = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
//
//		return encoder.encode(params).getTokenValue();
//	}
	
}
