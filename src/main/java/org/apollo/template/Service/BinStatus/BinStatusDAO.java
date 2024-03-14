package org.apollo.template.Service.BinStatus;

import org.apollo.template.Domain.BinStatus;

import java.util.Date;
import java.util.List;

public interface BinStatusDAO {

    List<BinStatus> readAllFromBinID(int binID);

}
