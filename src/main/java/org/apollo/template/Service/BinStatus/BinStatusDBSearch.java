package org.apollo.template.Service.BinStatus;

import org.apollo.template.Domain.Bin;
import org.apollo.template.Domain.BinStatus;
import org.apollo.template.Service.Database.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BinStatusDBSearch extends BinStatusDAODB implements BinStatusDAOExtend {

    private Connection conn = JDBC.get().getConnection();

    @Override
    public List<BinStatus> fetchStatus(Date startDate, Date endDate, List<Bin> binID) {

        // checks if any bins has been selected.
        if (binID.size() <= 0) return null;

        List<BinStatus> binStatusList = new ArrayList<>();
        String query = "SELECT fldInstallationDate, fldWeight, tblBin.fldBinID, fldResturantName, fldDateTime " +
                "FROM tblBinStatus " +
                "INNER JOIN tblBin ON tblBin.fldBinID = tblBinStatus.fldBinID " +
                "INNER JOIN tblResturant on tblBin.fldResturantID = tblResturant.fldResturantID " +
                "WHERE tblBinStatus.fldBinID  IN (" + "?,".repeat(binID.size() - 1) + "?) AND fldDateTime BETWEEN ? AND ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            int paramIndex = 1;

            for (Bin bin : binID) {
                ps.setInt(paramIndex++, bin.getBinID());
            }

            ps.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
            ps.setDate(paramIndex, new java.sql.Date(endDate.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Assuming BinStatus constructor requires the fields fldDateTime, fldWeight, and fldBinID
                    BinStatus binStatus = new BinStatus(rs.getDate("fldDateTime").toString(), rs.getInt("fldWeight"), rs.getInt("fldBinID"), rs.getString("fldResturantName"));
                    binStatusList.add(binStatus);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return binStatusList;
    }
}
