package com.techlabs.hibernate.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.techlabs.hibernate.entity.FileItem;
import com.techlabs.hibernate.service.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
    private FileService fileService;

    private final String FOLDER_PATH = "C:/Users/ACER/Documents/MonoJava/Spring/ExampleFiles/";

    @PostMapping("/upload")
    public ResponseEntity<FileItem> fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH + file.getOriginalFilename();
        FileItem item = FileItem.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .location(filePath)
                .build();

        FileItem responseItem = fileService.saveFile(item);
        file.transferTo(new File(filePath).toPath());

        return new ResponseEntity<>(responseItem, HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Object> fileDownload(@PathVariable(name = "fileName") String fileName) throws IOException {
        Optional<FileItem> fileItem = fileService.getFileByName(fileName);
        if (fileItem.isPresent()) {
            FileItem item = fileItem.get();
            String path = item.getLocation();
            byte[] content = Files.readAllBytes(Path.of(path));
            return ResponseEntity.status(HttpStatus.FOUND).contentType(MediaType.valueOf("image/png")).body(content);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found for File name : " + fileName);
        }
    }
}
