package Statistic;

import org.apollo.template.Model.Records;

import java.util.List;

public interface StatisticDAO {

    // SELECT fldDateTime,fldWeight from tblBinStatus WHERE fldDateTime = ANY(SELECT TOP 7 fldDateTime from tblBinStatus ORDER BY fldDateTime DESC);


    public List<Records> readRecords(int days);  // Read. (All records.)









    public List<String> readDate();     // Read. (All dates.)'



}
