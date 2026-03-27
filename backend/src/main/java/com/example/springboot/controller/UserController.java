package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.User;
import com.example.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {



    @Autowired
    private UserService userService;

    //新增和更改 Mybatis-Plus
    @PostMapping
    public boolean save(@RequestBody User user){
        return userService.saveUser(user);
    }

    //查询所有数据 用mybatis-plus
    @GetMapping
    public List<User> findAll(){
        return userService.list();
    }

    //删除数据 用mybatis-plus
    @DeleteMapping("/{id}")
    public  boolean delete(@PathVariable Integer id){
        return userService.removeById(id);
    }

    //分页查询 用mybatis-plus
    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                        @RequestParam(defaultValue = "") Integer pageSize,
                                        @RequestParam(defaultValue = "") String name){
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return userService.page(page,queryWrapper);
    }
}
