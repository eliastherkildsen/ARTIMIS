package org.apollo.template.Service.City;

import org.apollo.template.Domain.City;
import org.apollo.template.Service.Database.JDBC;
import org.apollo.template.Service.Debugger.DebugMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityDAODB implements CityDAO{

    Connection conn = JDBC.get().getConnection();

    @Override
    public City read(int zipCode) {

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tblCity WHERE fldZipcode = ?");
            ps.setInt(1, zipCode);

            ResultSet res = ps.executeQuery();

            ps.close();

            String fldCity = res.getString("fldCityName");
            int fldZipcode = res.getInt("fldZipcode");

            return new City(fldZipcode, fldCity);


        } catch (SQLException e) {
            DebugMessage.error(this, "In read, an error occurred " + e.getMessage());
        }

        return null;



    }

    @Override
    public List<City> readAll() {

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tblCity");
            ResultSet res = ps.executeQuery();

            List<City> cityList = new ArrayList<>();

            while (res.next()) {

                String fldCity = res.getString("fldCityName");
                int fldZipcode = res.getInt("fldZipcode");

                cityList.add(new City(fldZipcode, fldCity));

            }
            DebugMessage.info(this, "Fetching all citys");
            return cityList;




        } catch (SQLException e) {
            DebugMessage.error(this, "In readAll; an error chored " + e.getMessage());
        }

        return null;

    }

    @Override
    public void add() {


    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
