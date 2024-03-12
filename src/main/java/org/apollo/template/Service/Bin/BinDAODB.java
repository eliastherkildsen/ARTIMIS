package org.apollo.template.Service.Bin;

import org.apollo.template.Domain.Bin;
import org.apollo.template.Service.Database.JDBC;
import org.apollo.template.Service.Debugger.DebugMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BinDAODB implements BinDAO {

    private Connection conn = JDBC.get().getConnection();

    @Override
    public void add(Bin bin) {
        try{

            PreparedStatement ps = conn.prepareCall("INSERT INTO tblBin(fldMaxCapacity, fldInstallationDate, fldResturantID) VALUES (?,?,?)");

            ps.setInt(1, bin.getMaxCapacity());
            ps.setDate(2, (java.sql.Date)bin.getInstalationDate());
            ps.setInt(3, bin.getResturentID());

            ps.executeQuery();

            DebugMessage.info(this, " Added a new bin to the database.");

            ps.close();

        }catch (SQLException e){
            DebugMessage.error(this, " in add; An error occurred " + e.getMessage());
        }
    }

    @Override
    public Bin read(int id) {
        try{

            PreparedStatement ps = conn.prepareCall("SELECT * FROM tblBin WHERE fldBinID = ?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            int fldMaxCapacity = rs.getInt("fldMaxCapacity");
            java.sql.Date fldInstallationDate = rs.getDate("fldInstallationDate");
            int fldResturantID = rs.getInt("fldResturantID");

            DebugMessage.info(this, " fetching bin with id: " + id);

            ps.close();

            return new Bin(id, fldResturantID, fldMaxCapacity, fldInstallationDate);

        }catch (SQLException e){
            DebugMessage.error(this, " in read; An error occurred " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Bin> readAll() {
        try{

            PreparedStatement ps = conn.prepareCall("SELECT * FROM tblBin");
            ResultSet rs = ps.executeQuery();

            List<Bin> binList = new ArrayList<>();

            while (rs.next()){

                int fldMaxCapacity = rs.getInt("fldMaxCapacity");
                java.sql.Date fldInstallationDate = rs.getDate("fldInstallationDate");
                int fldResturantID = rs.getInt("fldResturantID");
                int id = rs.getInt("fldBinID");

                binList.add(new Bin(id, fldResturantID, fldMaxCapacity, fldInstallationDate));

            }

            DebugMessage.info(this, " Fetching all bins.");

        }catch (SQLException e){
            DebugMessage.error(this, " in readAll; An error occurred " + e.getMessage());
        }
        return null;
    }

    @Override
    public void update(int id, Bin bin) {
        try{

            PreparedStatement ps = conn.prepareCall("UPDATE tblBin SET fldMaxCapacity = ?, " +
                    "fldInstallationDate = ?, fldResturantID = ? WHERE id = ?");

            ps.setInt(1, bin.getMaxCapacity());
            ps.setDate(2, (java.sql.Date) bin.getInstalationDate());
            ps.setInt(3, bin.getResturentID());
            ps.setInt(4, id);

            ps.executeQuery();

            DebugMessage.info(this, " Updating bin where id is " + id);

            ps.close();

        }catch (SQLException e){
            DebugMessage.error(this, " in update; An error occurred " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try{

            PreparedStatement ps = conn.prepareCall("DELETE FROM tblBin WHERE fldBinID = ?");

            ps.setInt(1, id);

            ps.executeQuery();

            DebugMessage.info(this, " Deleting the bin where id is " + id);

        }catch (SQLException e){
            DebugMessage.error(this, " in delete; An error occurred " + e.getMessage());
        }
    }
}
