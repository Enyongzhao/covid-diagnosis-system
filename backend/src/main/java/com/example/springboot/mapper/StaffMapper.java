package com.example.springboot.mapper;

import com.example.springboot.entity.Staff;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StaffMapper {

    @Select("SELECT * FROM staff WHERE name = #{name} AND password = #{password}")
    Staff findByNameAndPassword(String name, String password);

    //获取所有职工信息
    @Select("SELECT * FROM staff")
    List<Staff> findAll();

    //更新信息，编辑按钮的实现
    @Update("UPDATE staff SET name = #{name}, role = #{role},password=#{password}, age = #{age}, sex = #{sex}, phone = #{phone}, address = #{address}, email = #{email} WHERE staffId = #{staffId}")
    int updateStaff(Staff staff);

    //新增职工信息
    @Insert("INSERT INTO staff ( name, role, password, age, sex, phone, address, email) VALUES (#{name}, #{role}, #{password}, #{age}, #{sex}, #{phone}, #{address}, #{email})")
    int addStaff(Staff staff);

    //根据职工id删除职工信息
    @Delete("DELETE FROM staff WHERE staffId = #{staffId}")
    void deleteStaffById(Long staffId);

    //查询信息
    @Select("SELECT * FROM staff WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Staff> searchStaffByName(String name);
}

