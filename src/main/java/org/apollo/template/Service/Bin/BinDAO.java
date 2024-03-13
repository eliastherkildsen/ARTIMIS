package org.apollo.template.Service.Bin;

import org.apollo.template.Domain.Bin;
import java.util.List;

public interface BinDAO {

    void add (Bin bin);
    Bin read (int id);
    List<Bin> readAll();
    List<Bin> readAllFromResturentID(int id);
    void update(int id, Bin bin);
    void delete(int id);

}
