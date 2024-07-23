package com.englishcenter.userservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermissionController {
	@CrossOrigin("http://localhost:3000") //call to front-end
    @GetMapping("/check-permission")
    public ResponseEntity<Map<String, Boolean>> checkPermission(Authentication authentication) {
        boolean hasPermission = false;

        // Kiểm tra quyền hạn của người dùng
        if (authentication != null) {
            hasPermission = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
        }

        Map<String, Boolean> response = new HashMap<>();
        response.put("hasPermission", hasPermission);
        return ResponseEntity.ok(response);
    }
}
