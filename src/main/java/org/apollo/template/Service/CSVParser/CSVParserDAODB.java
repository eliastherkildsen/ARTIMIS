package org.apollo.template.Service.CSVParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apollo.template.Service.CSVParser.CSVParserDAO;
import org.apollo.template.Service.Database.DatabaseUtil;
import org.apollo.template.Service.Database.JDBC;

public class CSVParserDAODB implements CSVParserDAO {
    Connection connection = JDBC.get().getConnection();

    @Override
    public void read() {

    }

    @Override
    public void readAll() {

    }

    @Override
    public void add() {

    }

    @Override
    public void insert(List<String[]> data) throws SQLException {
        String sql = "INSERT INTO tblBinStatus (fldDatetime, fldWeight, fldBinID) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (String[] row : data) {
                statement.setString(1, row[0]);
                statement.setString(2, row[1]);
                statement.setString(3, row[2]);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void delete() {

    }

    public int getBinIDForRBID(String rbID) throws SQLException {
        String query = "SELECT fldBinID FROM tblBin WHERE fldRaspberryID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, rbID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("fldBinID");
                } else {
                    throw new SQLException("RaspberryID not found in bin table");
                }
            }
        }
    }
}