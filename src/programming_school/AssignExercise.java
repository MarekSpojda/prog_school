package programming_school;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AssignExercise {
    public void runAssignExercise() {
        try {
            choice();
        } catch (Exception e) {
            System.err.println("Assigning exercises failed.");
            e.printStackTrace();
        }
    }

    private void choice() {
        System.out.println("Wybierz jedną z opcji:\n" +
                "add – przypisywanie zadań do użytkowników\n" +
                "view – przeglądanie rozwiązań danego użytkownika\n" +
                "quit – zakończenie programu przypisywania zadań");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().toLowerCase();

        switch (choice) {
            case "add":
                assignExercise();
                choice();
                break;
            case "view":
                viewUserSolutions();
                choice();
                break;
            case "quit":
                break;
            default: {
                System.out.println("Nierozpoznano wyboru");
                choice();
            }
        }
    }

    private void assignExercise() {
        System.out.println("Lista użytkowników:");
        for (User user : new User().loadAll()) {
            System.out.println(user.getId() + ", " + user.getUsername() + ", " + user.getEmail() + ", " + user.getUser_group_id());
        }

        System.out.println("Podaj ID użytkownika, kturemu chcesz przypisać zadanie:");
        Scanner scanner = new Scanner(System.in);
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Lista zadań możliwych do przypisania:");
        for (Exercise exercise : new Exercise().loadAll()) {
            System.out.println(exercise.getId() + ", " + exercise.getTitle() + ", " + exercise.getDescription());
        }
        System.out.println("Podaj ID zadania, które chcesz przypisać wybranemu użytkownikowi:");
        int exerciseId = scanner.nextInt();
        scanner.nextLine();

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = localDateTime.format(myFormatObj);
        Solution solution = new Solution(formattedDate, formattedDate, exerciseId, userId);
        solution.saveToDb();
    }

    @SuppressWarnings("Duplicates")
    private void viewUserSolutions() {
        try {
            User[] users = new User().loadAll();
            for (User user : users) {
                System.out.println(user.getId() + ", " + user.getUsername() + ", " + user.getEmail() + ", " + user.getUser_group_id());
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("Podaj ID uzytkownika, którego zadania chcesz przeglądać:");
            int userId = scanner.nextInt();

            Solution solution = new Solution();
            Solution[] solutions = solution.loadAllByUserId(userId);

            System.out.println("Rozwiązania użytkownika o ID = " + userId + ":");
            for (Solution solutionToView : solutions) {
                System.out.println(solutionToView.getId() + ", " + solutionToView.getCreated() + ", " +
                        solutionToView.getUpdated() + ", " + solutionToView.getDescription() + ", " +
                        solutionToView.getExercise_id() + ", " + solutionToView.getUsers_id());
            }
            System.out.println("* Koniec listy rozwiązań wybranego użytkownika *");
        } catch (Exception e) {
            System.err.println("Nie rozpoznano formatu ID użytkownika.");
            e.printStackTrace();
        }
    }
}