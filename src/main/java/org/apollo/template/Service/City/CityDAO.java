package org.apollo.template.Service.City;

import org.apollo.template.Domain.City;

import java.util.List;

public interface CityDAO {

    City read(int zipCode);
    List<City> readAll();
    void add();
    void update();
    void delete();

}
