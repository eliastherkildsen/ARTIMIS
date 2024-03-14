package Statistic;

import org.apollo.template.Model.Records;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StatisticUtil {

    private static final char DELIMITER = ' ';

    public static int cntEmptyings = 1;




    public static List<String> timeDay(List<Records> records){

        ArrayList<String> datesChosen = new ArrayList<>();
        String currentTime = "";


        for (Records times : records){

            // gets the date and time from the record
            String dateTime = times.getDateTime();

            // splits the date and time using the delimiter
            String[] splittedDateTime = dateTime.split(String.valueOf(DELIMITER));
            // time part
            String timePart = splittedDateTime[1];    // last part

            if (!timePart.equals(currentTime)){
                datesChosen.add(timePart);
                currentTime = timePart;
            }
        }
        return datesChosen;
    }


    public static List<Integer> timeWeights(List<Records> records) {

        ArrayList<Integer> chosenWeights = new ArrayList<>();
        String currentTime = "";
        int sumTime = 0;


        for (Records weight : records) {

            // gets the date and time from the record
            String dateTime = weight.getDateTime();
            // splits the date and time using the delimiter
            String[] splittedDateTime = dateTime.split(String.valueOf(DELIMITER));

            String timePart = splittedDateTime[1];

            // if new time than previous time
            if (!timePart.equals(currentTime)) {
                if (!currentTime.isEmpty()) {
                    chosenWeights.add(weight.getWeight());
                }
                // start weight for the new date
                sumTime = weight.getWeight();
                // updates current date
                currentTime = timePart;
                cntEmptyings = 1;
            }

            if (weight.getRecordID() == records.getLast().getRecordID() && cntEmptyings == 1){
                chosenWeights.add(sumTime);
            }
        }

        return chosenWeights;
    }





    public static List<String> datesPeriod(List<Records> records){

        ArrayList<String> datesChosen = new ArrayList<>();
        String currentDate = "";


        for (Records dates : records){

            // gets the date and time from the record
            String dateTime = dates.getDateTime();
            // splits the date and time using the delimiter
            String[] splittedDateTime = dateTime.split(String.valueOf(DELIMITER));

            String datePart = splittedDateTime[0];      // first part
            //String timePart = splittedDateTime[1];    // last part

            // new date
            if (!datePart.equals(currentDate)){
                datesChosen.add(datePart);
                currentDate = datePart;
            }
        }
        return datesChosen;
    }


    public static List<Integer> weightsPerDayPeriod(List<Records> records) {

            ArrayList<Integer> chosenWeights = new ArrayList<>();
            int sumDate = 0;
            String currentDate = "";


            for (Records weight : records) {

                // gets the date and time from the record
                String dateTime = weight.getDateTime();
                // splits the date and time using the delimiter
                String[] splittedDateTime = dateTime.split(String.valueOf(DELIMITER));


                String datePart = splittedDateTime[0];
                //String timePart = splittedDateTime[1];

                // if new date than previous date
                if (!datePart.equals(currentDate)) {
                    if (!currentDate.isEmpty()) {
                        chosenWeights.add(sumDate);
                    }
                    // start weight for the new date
                    sumDate = weight.getWeight();
                    // updates current date
                    currentDate = datePart;
                    cntEmptyings = 1;

                } else {
                    // if same date as previous record
                    sumDate += weight.getWeight();
                    cntEmptyings++;
                }

                if (weight.getRecordID() == records.getLast().getRecordID() && sumDate > 0){
                    chosenWeights.add(sumDate);
                }


            }

            return chosenWeights;
    }



    public static List<String> prev12MonthsByName(){
        // this date
        LocalDate currentDate = LocalDate.now();

        List <String> month12 = new ArrayList<>(12);

        for (int i = 1; i <= 12; i ++) {
            // prev month
            LocalDate prevMonth = currentDate.minusMonths(i);

            String monthPrev = String.valueOf(prevMonth.getMonth());
            //System.out.println(monthPrev);

            month12.add(monthPrev);
        }

        // reversing the order to correctly populate the x-axis
        Collections.reverse(month12);

        return month12;
    }



    public static List<Integer> weightsPerMonth(List<Records> records) {

        // List to store total weights per month
        ArrayList<Integer> totalWeightsPerMonth = new ArrayList<>();

        int sumMonth = 0;
        String currentMonth = "";

        for (Records totalWeightMonth : records) {

            // gets the date and time from the record
            String dateTime = totalWeightMonth.getDateTime();

            // splits the date and time using the delimiter
            String[] splittedDateTime = dateTime.split(String.valueOf(DELIMITER));

            // the date part
            String datePart = splittedDateTime[0];

            // parse the date part to a LocalDate object
            LocalDate date = LocalDate.parse(datePart);
            String month = String.valueOf(date.getMonth());
            //System.out.println(month);


            // if it's a new month
            if (!month.equals(currentMonth)) {

                if (!currentMonth.isEmpty()) {
                    totalWeightsPerMonth.add(sumMonth);
                }

                // start summing weights for the new month
                sumMonth = totalWeightMonth.getWeight();

                // update the current month
                currentMonth = month;
            } else {
                // if it's the same month as the previous record, add the weight to the sum
                sumMonth += totalWeightMonth.getWeight();
                cntEmptyings++;
            }
        }

        // add the sum of weights for the last month to the list
        if (!currentMonth.isEmpty()) {
            totalWeightsPerMonth.add(sumMonth);
        }

        return totalWeightsPerMonth;
    }


    public static int minWeigth (List<Integer> weight){
        return Collections.min(weight);
    }

    public static int maxWeigth (List<Integer> weight){
        return Collections.max(weight);
    }


    public static double averageWeight(List<Integer> weight){
        int totalWeight = 0;

        for (Integer intWeight : weight){
            totalWeight += intWeight;
        }

        return (double) totalWeight / weight.size();
    }


}

