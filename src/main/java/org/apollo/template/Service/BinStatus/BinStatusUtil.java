package org.apollo.template.Service.BinStatus;

import org.apollo.template.Domain.Bin;
import org.apollo.template.Domain.BinStatus;

import java.util.ArrayList;
import java.util.List;

public class BinStatusUtil {

    public static int getLifeTimeWaste(List<Bin> binList){

        List<Integer> binIDList = new ArrayList<>();
        int totalWeight = 0;

        for (Bin bin : binList) {
            binIDList.add(bin.getBinID());
        }

        BinStatusDAO binStatusDAO = new BinStatusDAODB();


        for (int i = 0; i < binIDList.size(); i++) {

            for (BinStatus bs : binStatusDAO.readAllFromBinID(binIDList.get(i)) ){

                totalWeight += bs.getWeight();

            }

        }


        return totalWeight;
    }

}
