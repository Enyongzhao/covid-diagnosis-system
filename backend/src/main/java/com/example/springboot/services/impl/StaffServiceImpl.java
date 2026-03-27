package com.example.springboot.services.impl;

import com.example.springboot.entity.Staff;
import com.example.springboot.mapper.StaffMapper;
import com.example.springboot.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffMapper staffMapper;

    @Override
    public Staff findByNameAndPassword(String name, String password) {
        return staffMapper.findByNameAndPassword(name, password);
    }

    @Override
    public List<Staff> findAll() {
        return staffMapper.findAll();
    }

    @Override
    public int addStaff(Staff staff) {
        return staffMapper.addStaff(staff);
    }

}

