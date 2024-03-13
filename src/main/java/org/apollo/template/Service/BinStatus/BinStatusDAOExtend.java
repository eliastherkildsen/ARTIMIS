package org.apollo.template.Service.BinStatus;

import org.apollo.template.Domain.Bin;
import org.apollo.template.Domain.BinStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface BinStatusDAOExtend extends BinStatusDAO{

    List<BinStatus> fetchStatus(Date startDate, Date endDate, List<Bin> binID);


}
