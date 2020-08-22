package com.home.ecommerce.Controller;

import com.home.ecommerce.Repositroy.ProductRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;
@RestController
public class TestClass  {

    @Autowired
    private ProductRepository productRepository;
    public static void main(String[] args){
        System.out.println(Paths.get("D:\\programming\\java\\ecommerceimages\\c173a256-725e-406a-83e5-cbd75cb1dcaf.jpg"));
    }

    @ResponseBody
    @GetMapping("/getImage/{id}")
    public ResponseEntity<?> getImage(@PathVariable int id ) throws Exception{
        String path = productRepository.findById(id).getImagePath();
        File f = new File(path);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(FileUtils.readFileToByteArray(f));
    }
}


