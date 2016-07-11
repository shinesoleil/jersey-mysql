package com.thoughtworks.api.mappers;

import com.thoughtworks.api.core.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {

  List<Product> find();

  Product findById(String id);

  int save( @Param("id") String id,
            @Param("name") String name,
            @Param("description") String description,
            @Param("price") float price,
            @Param("rating") int rating);

}
