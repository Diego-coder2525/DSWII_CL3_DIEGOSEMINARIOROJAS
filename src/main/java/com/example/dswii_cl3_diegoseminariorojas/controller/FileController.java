package com.example.dswii_cl3_diegoseminariorojas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
@RestController
@CrossOrigin(origins = "https://www.cibertec.edu.pe")
public class FileController {
    private static final String IMAGES_DIRECTORY = "images";
    private static final String DOCUMENTS_DIRECTORY = "documentos";
    private static final long MAX_FILE_SIZE = 1_500_000; // 1.5MB

    @PostMapping("/filesimages")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        validateFileExtension(file, "png");
        return saveFile(file, IMAGES_DIRECTORY);
    }

    @PostMapping("/filesexcel")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) throws IOException {
        validateFileExtension(file, "xlsx");
        validateFileSize(file, MAX_FILE_SIZE);
        return saveFile(file, DOCUMENTS_DIRECTORY);
    }

    private ResponseEntity<String> saveFile(MultipartFile file, String directory) throws IOException {


        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(file.getOriginalFilename())
                .toUriString();

        return ResponseEntity.ok("Archivo subido correctamente. URL de descarga: " + fileDownloadUri);
    }

    private void validateFileExtension(MultipartFile file, String allowedExtension) {
        if (!file.getOriginalFilename().toLowerCase().endsWith(allowedExtension)) {
            throw new IllegalArgumentException("Extensión de archivo no permitida");
        }
    }

    private void validateFileSize(MultipartFile file, long maxSize) {
        if (file.getSize() > maxSize) {
            throw new IllegalArgumentException("Tamaño de archivo excede el límite permitido");
        }
    }
}
