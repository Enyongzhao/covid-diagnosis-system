package com.example.springboot.controller;

import com.example.springboot.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
public class FileController {

    private static final String filePath = System.getProperty("user.dir") + "/file/";

    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please select a file to upload.";
        }

        try {
            // 获取当前时间戳
            String flag = System.currentTimeMillis() + "";
            // 创建文件名，格式为时间戳-原始文件名
            String fileName = flag + ".png";

            // 确保目录存在
            File directory = new File(filePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 保存文件
            Path path = Paths.get(filePath + fileName);
            Files.copy(file.getInputStream(), path);

            return flag;
        } catch (IOException e) {
            e.printStackTrace();
            return "Could not upload the file: " + file.getOriginalFilename() + "!";
        }
    }




    @GetMapping("/{flag}")
    public void avatarPath(@PathVariable String flag, HttpServletResponse response) {
        File directory = new File(filePath);
        File[] files = directory.listFiles((dir, name) -> name.startsWith(flag));

        if (files != null && files.length > 0) {
            File file = files[0]; // Assuming the first matched file is the desired file
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

            try (OutputStream os = response.getOutputStream();
                 FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            try {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("File not found with flag: " + flag);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 新增的送检API
    @PostMapping("/send-for-inspection/{flag}")
    public ResponseEntity<?> sendForInspection(@PathVariable String flag) {
        try {
            // 构造文件路径
            String filePath = "C:\\Users\\zeyzey\\IdeaProjects\\springboot\\file\\" + flag + ".png";
            FileSystemResource fileResource = new FileSystemResource(filePath);

            // 创建 MultiValueMap 用来封装文件参数
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileResource);

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // 创建 HttpEntity 对象
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // 发送 POST 请求到推理服务
            ResponseEntity<String> response = restTemplate.postForEntity("http://127.0.0.1:5000/predict", requestEntity, String.class);

            // 检查响应状态并处理结果
            if (response.getStatusCode() == HttpStatus.OK) {
                String status = response.getBody();

                String change = response.getBody();
                String targetContent = "\\u9634\\u6027";

                if (change.contains(targetContent)) {
                    change = "阴性";
                } else {
                    change = "疑似阳性";
                }

                // 这里可以添加更新数据库状态的代码

                userService.updateStatusInDatabase(flag,change);
                return ResponseEntity.ok().body(status);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process image");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

}


