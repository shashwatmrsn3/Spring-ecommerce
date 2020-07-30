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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public ResponseEntity<?> updateProduct(@RequestBody Product product,BindingResult result){
        ResponseEntity<?> errorMap = errorService.validationErrorService(result);
        if(errorMap!=null) return errorMap;
        Product product1 = productService.findProductById(product.getId());
        if(product1==null) throw new ProductNotFoundException("The required product was not found");
        if(product1.getVendor().getVendorAdmin()!=principalService.getCurrentPrincipal()) {
            throw new UnauthorizedException("You are not permitted to preform this operation");
        }
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
    public ResponseEntity<?> getAllProducts(){
        User user = principalService.getCurrentPrincipal();
        Vendor vendor = vendorService.getVendorByUser(user);
        List<Product> products = productService.getAllProductsByVendor(vendor);
        
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
