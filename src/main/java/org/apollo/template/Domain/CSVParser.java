package org.apollo.template.Domain;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apollo.template.Service.CSVParser.CSVParserDAO;
import org.apollo.template.Service.CSVParser.CSVParserDAODB;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CSVParser {

    private CSVParserDAO parserDAO;

    public CSVParser() {
        this.parserDAO = new CSVParserDAODB();
    }

    private Stage stage;

    public void fileStuff() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getInitialDirectory();
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("Microsoft Excel Comma Separated Files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(csvFilter);
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                System.out.println("Selected File: " + selectedFile.getAbsolutePath());
                List<String> contentLines = Files.readAllLines(selectedFile.toPath());

                if (!contentLines.isEmpty()) {
                    contentLines.remove(0);
                }

                List<String[]> data = new ArrayList<>();
                for (String line : contentLines) {
                    String[] fields = line.split(",");

                    String date = convertToSQLDateTime(fields[1]);
                    String weight = fields[2];
                    String raspberryID = fields[3];
                    data.add(new String[]{date, weight, raspberryID});
                }

                parserDAO.insert(data);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private String convertToSQLDateTime(String dateTimeString) {
        return dateTimeString.replace('/', '-');
    }
}