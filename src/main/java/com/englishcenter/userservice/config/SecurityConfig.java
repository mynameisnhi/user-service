package com.englishcenter.userservice.config;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//import com.englishcenter.userservice.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Value("${security.jwt.secret-key}")
	private String jwtSecretKey;
	
//	@Autowired
//	private JwtAuthFilter jwtAuthFilter;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/login").permitAll()
						.requestMatchers("/profile").permitAll()
						.requestMatchers("/register").permitAll()
						.requestMatchers("/check-permission").permitAll()
						.requestMatchers("/username").permitAll()
//						.requestMatchers("/teacher/**").hasAnyRole("EMPLOYEE", "ADMIN")
//						.requestMatchers("/student/**").hasAnyRole("EMPLOYEE", "ADMIN")
//						.requestMatchers("/fee/**").hasAnyRole("EMPLOYEE", "ADMIN")
//						.requestMatchers("/session/**").hasAnyRole("EMPLOYEE", "ADMIN")
//						.requestMatchers("/class/**").hasAnyRole("EMPLOYEE", "ADMIN")
//						.requestMatchers("/mark/**").hasAnyRole("EMPLOYEE", "ADMIN")
//						.requestMatchers("/profile").authenticated()
						.anyRequest().authenticated()
						)
//				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
//				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
//				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
//	@Bean
//	public JwtDecoder jwtDecoder() {
//		var secretKey = new SecretKeySpec(jwtSecretKey.getBytes(), "");
//		return NimbusJwtDecoder.withSecretKey(secretKey)
//				.macAlgorithm(MacAlgorithm.HS256).build();
//	}
	
//	@Bean
//	public AuthenticationManager authenticationManager() {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setUserDetailsService(userDetailsService());
//		provider.setPasswordEncoder(new BCryptPasswordEncoder());
//		
//		return new ProviderManager(provider);
//	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(passwordEncoder());
		
		return provider;
	}
	
	@Bean
	public AuthenticationManager authentication(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
