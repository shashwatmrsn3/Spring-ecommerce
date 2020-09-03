package com.home.ecommerce.Controller;

import com.home.ecommerce.Domain.Comment;
import com.home.ecommerce.Domain.Product;
import com.home.ecommerce.Domain.Question;
import com.home.ecommerce.Domain.User;
import com.home.ecommerce.Exception.ProductNotFoundException;
import com.home.ecommerce.Exception.UnauthorizedException;
import com.home.ecommerce.Repositroy.CommentRepository;
import com.home.ecommerce.Security.LoginRequest;
import com.home.ecommerce.Security.LoginSuccessResponse;
import com.home.ecommerce.Security.TokenProvider;
import com.home.ecommerce.Service.*;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ValidationErrorService validationErrorService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private PrincipalService principalService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CommentService commentService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody  User user, BindingResult result){
        ResponseEntity<?> errorMap = validationErrorService.validationErrorService(result);
        if (errorMap != null) return  errorMap;
        User user1 = userService.registerUser(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request,BindingResult result) throws Exception{

        ResponseEntity<?> errorMap = validationErrorService.validationErrorService(result);

        if(errorMap != null) return errorMap;
        String email = request.getEmail();
        String password = request.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );

        User user  = userService.loadUserByUsername(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return new ResponseEntity<LoginSuccessResponse>(new LoginSuccessResponse(true,jwt,user.getRole(),user.getId()),HttpStatus.OK);
    }

    @PostMapping("/askquestion/{pid}")
    public ResponseEntity<?> comment(@RequestBody Question question,BindingResult result,@PathVariable("pid") int id){
        System.out.println("Product id"+id);
        ResponseEntity<?> errorMap = validationErrorService.validationErrorService(result);
        if(errorMap!=null) return errorMap;
        User user = principalService.getCurrentPrincipal();
        Product product = productService.findProductById(id);
        if(product==null) throw new ProductNotFoundException("Requested product is not found");
        if(product.getVendor().getVendorAdmin()==user) throw new UnauthorizedException("You are not allowed to ask question on your own product as a vendor");
        Comment comment = new Comment();
        comment.setQuestion(question.getQuestion());
        comment.setProduct(product);
        Comment  comment1 = commentService.saveComment(comment);
        return new ResponseEntity<Comment>(comment1,HttpStatus.CREATED);

    }

    @GetMapping("/getDetails/{id}")
    public ResponseEntity<?> getUserDetails(@PathVariable Long id){
        String  name = principalService.getCurrentPrincipaUsername();
        User user = userService.loadUserByUsername(name);
        if(id != user.getId()) throw new ProductNotFoundException("You are not allowed to preform this action");
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }


}
