package programming_school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SolutionManager {
    public static void main(String[] args) {
        new SolutionManager().choice(Integer.parseInt(args[0]));
    }

    private void choice(int param) {
        System.out.println("Wybierz jedną z opcji:\n" +
                "add – dodawanie rozwiązania\n" +
                "view – przeglądanie swoich rozwiązań\n" +
                "quit – zakończenie programu zarządzania rozwiązaniami");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().toLowerCase();

        switch (choice) {
            case "add":
                addSolution(param);
                choice(param);
                break;
            case "view":
                viewSolution(param);
                choice(param);
                break;
            case "quit":
                break;
            default: {
                System.out.println("Nierozpoznano wyboru");
                choice(param);
            }
        }
    }

    private void addSolution(int param) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "select id from exercise where id>?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 0);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> exerciseIds = new ArrayList<>();
            while (resultSet.next()) {
                exerciseIds.add(resultSet.getInt(1));
            }

            sql = "select exercise_id from solution where users_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, param);
            resultSet = preparedStatement.executeQuery();
            List<Integer> userExerciseIds = new ArrayList<>();
            while (resultSet.next()) {
                userExerciseIds.add(resultSet.getInt(1));
            }

            exerciseIds.removeAll(userExerciseIds);

            for (int exerciseId : exerciseIds) {
                sql = "select * from exercise where id=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, exerciseId);
                resultSet = preparedStatement.executeQuery();
            }
            System.out.println("Zadania, do których użytkownik nie dodał rozwiązania:");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("title") +
                        ", " + resultSet.getString("description"));
            }
            System.out.println("* koniec listy zadań *");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Podaj ID zadania, do którego chcesz dodać rozwiązanie:");
            int idForSolution = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Podaj opis rozwiązania:");
            String userSolution = scanner.nextLine();

            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = localDateTime.format(myFormatObj);
            Solution solution = new Solution(formattedDate, formattedDate, userSolution, idForSolution, param);
            solution.saveToDb();
        } catch (Exception e) {
            System.out.println("Wystąpil błąd podczas dodawania rozwiązania.");
            e.printStackTrace();
        }
    }

    private void viewSolution(int param) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "SELECT * FROM solution where users_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, param);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + ", " +
                        resultSet.getString("created") + ", " +
                        resultSet.getString("updated") + ", " +
                        resultSet.getString("description") + ", " +
                        resultSet.getInt("exercise_id") + ", " +
                        resultSet.getInt("users_id"));
            }
        } catch (Exception e) {
            System.out.println("Wystąpil błąd podczas wyświetlania rozwiązań.");
            e.printStackTrace();
        }
    }
}