package com.example.springboot.services;

import com.example.springboot.entity.Staff;
import java.util.List;

public interface StaffService {

    Staff findByNameAndPassword(String name, String password);
    List<Staff> findAll();
    int addStaff(Staff staff);

}

