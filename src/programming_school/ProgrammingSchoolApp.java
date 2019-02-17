package programming_school;

import java.sql.SQLException;

public class ProgrammingSchoolApp {
    public static void main(String[] args) throws SQLException {
        ConnectionManager.getConnection();
    }
}