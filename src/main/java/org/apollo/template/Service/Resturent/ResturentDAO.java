package org.apollo.template.Service.Resturent;

import org.apollo.template.Domain.Resturent;

import java.util.List;

public interface ResturentDAO {

    void add(Resturent resturent);
    Resturent read(int id);
    List<Resturent> readall();
    void update(int id, Resturent resturent);
    void delete(int id);

}
