package com.home.ecommerce.Controller;

import com.home.ecommerce.Domain.User;
import com.home.ecommerce.Domain.Vendor;
import com.home.ecommerce.Exception.MultipleVendorPerUserException;
import com.home.ecommerce.Service.PrincipalService;
import com.home.ecommerce.Service.UserService;
import com.home.ecommerce.Service.ValidationErrorService;
import com.home.ecommerce.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/vendor")
public class VendorController {

    @Autowired
    private ValidationErrorService validationErrorService;
    @Autowired
    private VendorService vendorService;
    @Autowired
    private UserService userService;
    @Autowired
    private PrincipalService principalService;

    @PostMapping("/register")
    public ResponseEntity<?> registerVendor(@RequestBody Vendor vendor, BindingResult result){
        ResponseEntity<?> errorMap =  validationErrorService.validationErrorService(result);
        if(errorMap!=null) return errorMap;
        String username = principalService.getCurrentPrincipaUsername();
        User user = userService.loadUserByUsername(username);
        if(user.getVendor()!=null) throw new MultipleVendorPerUserException("Vendor for this user already exists");
        vendor.setVendorAdmin(user);
        user.setRole("ROLE_VENDOR");
        Vendor savedVendor = vendorService.registerVendor(vendor);
        return new ResponseEntity<Vendor>(savedVendor, HttpStatus.CREATED);
    }
}
