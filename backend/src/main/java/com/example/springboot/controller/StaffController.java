package com.example.springboot.controller;

import com.example.springboot.entity.Staff;
import com.example.springboot.services.StaffService;
import com.example.springboot.util.JwtUtil;
import com.example.springboot.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest loginRequest) {
        Staff staff = staffService.findByNameAndPassword(loginRequest.getName(), loginRequest.getPassword());
        Map<String, Object> response = new HashMap<>();

        if (staff != null) {
            String token = JwtUtil.generateToken(staff);
            response.put("token", token);
            response.put("role", staff.getRole());
            response.put("name", staff.getName());
            response.put("age", staff.getAge());
            response.put("sex", staff.getSex());
            response.put("phone", staff.getPhone());
            response.put("address", staff.getAddress());
            response.put("email", staff.getEmail());
        } else {
            response.put("message", "Invalid username or password");
        }

        return response;
    }

    @GetMapping("/staff")
    public List<Staff> index() {
        return staffService.findAll();
    }


    @PostMapping("/")
    public Integer save(@RequestBody Staff staff) {
        return staffService.addStaff(staff);
    }



}

