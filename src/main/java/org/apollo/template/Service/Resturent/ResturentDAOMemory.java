package org.apollo.template.Service.Resturent;

import org.apollo.template.Domain.Bin;
import org.apollo.template.Domain.Resturent;

import java.util.ArrayList;
import java.util.List;

public class ResturentDAOMemory implements ResturentDAO{
    private List<Resturent> resturentList = new ArrayList<>();
    @Override
    public void add(Resturent resturent) {
        resturentList.add(resturent);
    }

    @Override
    public Resturent read(int id) {

        for (Resturent resturent: resturentList){
            if (resturent.getResturentID() == id) return resturent;

        }

        return null;

    }

    @Override
    public List<Resturent> readall() {
        return resturentList;
    }

    @Override
    public void update(int id, Resturent resturent) {

        for (Resturent oldResturent: resturentList){
            if (oldResturent.getResturentID() == id){
                oldResturent = resturent;
            };

        }

    }

    @Override
    public void delete(int id) {

        resturentList.removeIf( e -> e.getResturentID() == id);

    }
}
