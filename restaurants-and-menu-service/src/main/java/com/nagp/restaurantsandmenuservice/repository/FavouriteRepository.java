package com.nagp.restaurantsandmenuservice.repository;

import com.nagp.restaurantsandmenuservice.entity.FavouriteRestaurants;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteRepository extends CrudRepository<FavouriteRestaurants, String> {
    @Query(value = "select restaurant_id from favourite_restaurants where username=?1", nativeQuery = true)
    Optional<List<String>> findByUsername(String username);

}
