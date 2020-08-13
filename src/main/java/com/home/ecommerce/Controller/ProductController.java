package com.home.ecommerce.Controller;

import com.home.ecommerce.Domain.*;
import com.home.ecommerce.Exception.ProductNotFoundException;
import com.home.ecommerce.Exception.UnauthorizedException;
import com.home.ecommerce.Service.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ValidationErrorService errorService;
    @Autowired
    private PrincipalService principalService;
    @Autowired
    private VendorService vendorService;
    @Autowired
    private CommentService commentService;
    private static String imageDirectory = System.getProperty("user.dir") + "/images/";

    @PostMapping(value = "/addProduct")
    public ResponseEntity<?> addProduct(@RequestParam("image") MultipartFile image, @RequestParam("description") String description,@RequestParam("name") String name,@RequestParam("price") float price,@RequestParam("stock") int stock) throws Exception{
       // ResponseEntity<?> errorMap = errorService.validationErrorService(result);
      //  if(errorMap != null) return errorMap;
        Path fileNamePath = Paths.get(imageDirectory, UUID.randomUUID()+"."+ FilenameUtils.getExtension(image.getOriginalFilename()));
        try{
            Files.write(fileNamePath,image.getBytes());
        }catch(Exception e){
            System.out.println(e);
        }
        Product product = new Product();
        product.setImagePath(fileNamePath.toString());
        product.setStock(stock);
        product.setDescription(description);
        product.setPrice(price);
        product.setName(name);
        CustomFile customFile = new CustomFile();
        customFile.setBytes(image.getBytes());
        customFile.setFileType(FilenameUtils.getExtension(image.getOriginalFilename()));
        product.setCustomImageFile(customFile);

        User user = principalService.getCurrentPrincipal();
        Product product1 = productService.saveProduct(product,user);
        product1.setImagePath(null);
        return  new ResponseEntity<Product>(product1, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestParam("id") @NotNull int id,@RequestParam(value = "image",required = false) MultipartFile image, @RequestParam("name") @NotBlank  String name, @RequestParam("description") @NotBlank String description, @RequestParam("price") @NotNull float price,@RequestParam("stock") @NotNull int stock){
        Product product = new Product();
        Product product1 = productService.findProductById(id);
        if(product1==null) throw new ProductNotFoundException("The required product was not found");
        if(product1.getVendor().getVendorAdmin()!=principalService.getCurrentPrincipal()) {
            throw new UnauthorizedException("You are not permitted to preform this operation");
        }
        product.setName(name);
        product.setId(id);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        if(image==null){
            product.setCustomImageFile(product1.getCustomImageFile());
            product.setImagePath(product1.getImagePath());
        }else if(image != null) {
            Path fileNamePath = Paths.get(imageDirectory, UUID.randomUUID()+"."+ FilenameUtils.getExtension(image.getOriginalFilename()));
            try{
                Files.write(fileNamePath,image.getBytes());
            }catch(Exception e){
                System.out.println(e);
            }
            product.setImagePath(fileNamePath.toString());
        }
        product.setComments(product1.getComments());
        product.setRating(product1.getRating());
        Product updatedProduct = productService.saveProduct(product,principalService.getCurrentPrincipal());
        return new ResponseEntity<Product>(updatedProduct,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id){
        Product product = productService.findProductById(id);
        if(product==null) throw new ProductNotFoundException("The given product was not found");
        if(product.getVendor().getVendorAdmin()!=principalService.getCurrentPrincipal()) {
            throw new UnauthorizedException("You are not authorized to preform this operation");
        }

        productService.deleteById(id);
        return new ResponseEntity<>(id,HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() throws Exception{
        User user = principalService.getCurrentPrincipal();
        Vendor vendor = vendorService.getVendorByUser(user);
        List<Product> products = productService.getAllProductsByVendor(vendor);
        for(int i=0;i<products.size();i++){
            Product product = products.get(i);
            System.out.println("1");
            String path = product.getImagePath();
            System.out.println("2");
            CustomFile customFile = new CustomFile();
            System.out.println("3");
            File check = null;
            if(path!=null) check = new File(path);
            if(path!=null && check.exists()) {
                customFile.setBytes(Files.readAllBytes(Paths.get(path)));

                System.out.println("4");

                customFile.setFileType(FilenameUtils.getExtension(path));
                System.out.println(5);
                product.setCustomImageFile(customFile);
            }

        }
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PostMapping("/answerquestion/{pid}/{cid}")
    public ResponseEntity<?> answerquestion(@RequestBody Comment comment,@PathVariable("pid") int productId,@PathVariable("cid") int commentId){
        Product product = productService.findProductById(productId);
        if(product==null) throw new ProductNotFoundException("Requested product was not found");
        if(product.getVendor().getVendorAdmin()!=principalService.getCurrentPrincipal()) {
            throw new UnauthorizedException("You are not permitted to preform this operation");
        }
        Comment comment1 = commentService.findById(commentId);
        if(comment1 == null) throw new ProductNotFoundException("The requested comment was not found");
        comment1.setAnswer(comment.getAnswer());
        Comment savedComment = commentService.saveComment(comment1);
        return new ResponseEntity<Comment>(savedComment,HttpStatus.OK);

    }

    @GetMapping("/details/{pid}")
    public ResponseEntity<?> getProductDetails(@PathVariable("pid") int id){
        Product product = productService.findProductById(id);
        if(product==null) throw new ProductNotFoundException("Requested product was not found");
        return new ResponseEntity<Product>(product,HttpStatus.OK);
    }
}
