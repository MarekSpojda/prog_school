package programming_school;

import java.sql.SQLException;
import java.util.Scanner;

public class ProgrammingSchoolApp {
    public static void main(String[] args) throws SQLException {
        //ConnectionManager.getConnection();
//        LocalDateTime localDateTime = LocalDateTime.now();
//        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        String formattedDate = localDateTime.format(myFormatObj);
//        new ManageUsers().runManageUsers();
        choiceProgram();
    }

    private static void choiceProgram() {
        System.out.println("Wybierz jedną z opcji:\n" +
                "a – zarządzanie użytkownikami\n" +
                "b – zarządzanie zadaniami\n" +
                "c – usunięcie grupami\n" +
                "d – przypisywanie zadań\n" +
                "quit – zakończenie programu głownego");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().toLowerCase();

        switch (choice) {
            case "a":
                new ManageUsers().runManageUsers();
                choiceProgram();
                break;
            case "b":
                new ManageTasks().runManageTasks();
                choiceProgram();
                break;
            case "c":
                break;
            case "d":
                break;
            case "quit":
                break;
            default: {
                System.out.println("Nierozpoznano wyboru");
                choiceProgram();
            }
        }
    }
}