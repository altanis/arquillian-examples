package com.github.altanis.arquillian.core.items;


import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {

    @Query("select i from Item i order by i.name")
    public List<Item> getAll();

}
