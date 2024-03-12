package org.apollo.template.Service.Resturent;

import org.apollo.template.Domain.Resturent;
import org.apollo.template.Service.Database.JDBC;
import org.apollo.template.Service.Debugger.DebugMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResturenDAODB implements ResturentDAO {

    private Connection conn = JDBC.get().getConnection();

    @Override
    public void add(Resturent resturent) {

        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO tblResturant (fldResturantName, fldAdress, fldZipcode) VALUES (?,?,?)");

            ps.setString(1, resturent.getResturentName());
            ps.setString(2, resturent.getResturentAdress());
            ps.setInt(3, resturent.getResturentZip());

            ps.executeQuery();

            DebugMessage.info(this, " Added a new restaurant to the database.");

            ps.close();

        } catch (SQLException e) {
            DebugMessage.error(this, "in add; An error occurred" + e.getMessage());

        }


    }

    @Override
    public Resturent read(int id) {

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tblResturant WHERE fldResturantID = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            String fldResturentName = rs.getString("fldResturantName");
            String fldAdress = rs.getString("fldAdress");
            int fldZipcode = rs.getInt("fldZipcode");

            DebugMessage.info(this, "fetching resturent with id:" + id );
            ps.close();

            return new Resturent(id, fldResturentName, fldAdress, fldZipcode);


        } catch (SQLException e) {
            DebugMessage.error(this, "In read; an error chored " + e.getMessage());
        }

        return null;

    }

    @Override
    public List<Resturent> readall() {

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tblResturant");
            ResultSet rs = ps.executeQuery();

            List<Resturent> resturentList = new ArrayList<>();

            while (rs.next()) {

                String fldResturentName = rs.getString("fldResturantName");
                String fldAdress = rs.getString("fldAdress");
                int fldZipcode = rs.getInt("fldZipcode");
                int id = rs.getInt("fldResturantID");

                resturentList.add(new Resturent(id, fldResturentName, fldAdress, fldZipcode));

            }
            DebugMessage.info(this, "Fetching all restaurants");
            return resturentList;




        } catch (SQLException e) {
            DebugMessage.error(this, "In readAll; an error chored " + e.getMessage());
        }

        return null;

    }

    @Override
    public void update(int id, Resturent resturent) {

    }

    @Override
    public void delete(int id) {

    }
}
