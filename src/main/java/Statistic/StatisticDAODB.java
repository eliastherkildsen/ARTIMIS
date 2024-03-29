package Statistic;

import org.apollo.template.Model.Records;
import org.apollo.template.Service.Database.JDBC;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StatisticDAODB implements StatisticDAO{

    @Override
    public List<Records> readRecords(int days, int binID) {

        ArrayList<Records> recordsList = new ArrayList<>();
        Records row;

        LocalDate currentDate = LocalDate.now();
        LocalDate backwardsDate = currentDate.minusDays(days);

        //System.out.println("current date: " + currentDate);
        //System.out.println("backwards date: " + backwardsDate);


            try {
                PreparedStatement preparedStatement = JDBC.get().getConnection().prepareStatement("SELECT * FROM tblBinStatus WHERE fldDateTime >= ? AND fldDateTime < ? AND fldBinID = ?;");
                preparedStatement.setDate(1, Date.valueOf(backwardsDate));
                preparedStatement.setDate(2, Date.valueOf(currentDate));
                preparedStatement.setInt(3,binID);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){

                    row = getDataFromResultSet(resultSet);
                    //System.out.println("row: " + row);

                    // add record to recordsList
                    recordsList.add(row);
                }

            }catch (SQLException e){
                System.err.println("[readRecords] could not read all" + e.getMessage());
            }

            return recordsList;
    }




    private Records getDataFromResultSet(ResultSet resultSet) throws SQLException {

        // get data from the result set
        int recordID = resultSet.getInt("fldStatusID");
        String dateTime = resultSet.getString("fldDateTime");
        int weight = resultSet.getInt("fldWeight");

        // return a new Record object with the retrieved data
        return new Records(recordID, dateTime, weight);
    }


}
