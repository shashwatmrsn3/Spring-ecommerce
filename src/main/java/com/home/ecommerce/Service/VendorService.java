package com.home.ecommerce.Service;

import com.home.ecommerce.Domain.User;
import com.home.ecommerce.Domain.Vendor;
import com.home.ecommerce.Repositroy.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public Vendor registerVendor(Vendor vendor){
        return vendorRepository.save(vendor);
    }

    public Vendor getVendorByUser(User user){
        return vendorRepository.getByVendorAdmin(user);
    }
}
