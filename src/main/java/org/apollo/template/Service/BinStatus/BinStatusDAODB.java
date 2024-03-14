package org.apollo.template.Service.BinStatus;

import org.apollo.template.Domain.BinStatus;
import org.apollo.template.Service.Database.JDBC;
import org.apollo.template.Service.Debugger.DebugMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BinStatusDAODB implements BinStatusDAO{

    private Connection conn = JDBC.get().getConnection();
    @Override
    public List<BinStatus> readAllFromBinID(int binID) {

        try{

            PreparedStatement ps = conn.prepareCall("SELECT * FROM tblBinStatus WHERE fldBinID = ?");

            ps.setInt(1, binID);

            ResultSet rs = ps.executeQuery();

            List<BinStatus> binList = new ArrayList<>();

            while(rs.next()){

                int fldStatusID = rs.getInt("fldStatusID");
                String fldDate = rs.getString("fldDateTime");
                int fldWeight = rs.getInt("fldWeight");
                int fldBinID = rs.getInt("fldBinID");

                binList.add(new BinStatus(fldStatusID, fldDate, fldWeight, fldBinID));

            }

            DebugMessage.info(this, " fetching bin status with bin ID: " + binID);

            ps.close();

            return binList;

        }catch (SQLException e){
            DebugMessage.error(this, " in readAllFromBinID; An error occurred " + e.getMessage());
        }

        return null;
    }



}
