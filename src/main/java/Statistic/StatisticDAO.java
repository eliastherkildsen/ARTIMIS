package Statistic;

import org.apollo.template.Model.Records;

import java.util.List;

public interface StatisticDAO {

    public List<Records> readRecords(int days, int binID);  // Read. (All records.)


}
