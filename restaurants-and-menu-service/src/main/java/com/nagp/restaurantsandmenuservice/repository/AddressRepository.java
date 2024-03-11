package com.nagp.restaurantsandmenuservice.repository;

import com.nagp.restaurantsandmenuservice.entity.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<Address, String> {

    @Query(value = "select address_id from address where area_code=?1", nativeQuery = true)
    List<String> findByAreaCode(String areaCode);

}
