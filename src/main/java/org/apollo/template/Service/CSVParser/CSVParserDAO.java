package org.apollo.template.Service.CSVParser;

import java.sql.SQLException;
import java.util.List;

public interface CSVParserDAO {

    void read();
    void readAll();
    void add();
    void insert(List<String[]> data) throws SQLException;
    void delete();
    int getBinIDForRBID(String rbID) throws SQLException;


}
