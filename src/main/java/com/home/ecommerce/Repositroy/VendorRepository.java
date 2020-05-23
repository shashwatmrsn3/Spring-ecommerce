package com.home.ecommerce.Repositroy;

import com.home.ecommerce.Domain.Vendor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends CrudRepository<Vendor,Integer> {


}
