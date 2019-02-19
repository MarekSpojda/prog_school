package programming_school;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProgrammingSchoolApp {
    public static void main(String[] args) throws SQLException {
        //ConnectionManager.getConnection();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDate = localDateTime.format(myFormatObj);
        System.out.println(formattedDate);
    }
}