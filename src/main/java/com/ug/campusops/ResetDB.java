package com.ug.campusops;

import com.ug.campusops.db.DatabaseConnector;
import com.ug.campusops.db.SchemaSetup;

public class ResetDB {
    public static void main(String[] args) {
        DatabaseConnector db = new DatabaseConnector();
        SchemaSetup schema = new SchemaSetup(db);
        schema.seedFromCSV("data");
        db.closeConnection();
    }
}