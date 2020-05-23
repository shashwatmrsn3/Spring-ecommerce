package com.home.ecommerce.Service;

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

}
