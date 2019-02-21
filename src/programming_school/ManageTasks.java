package programming_school;

import java.util.Scanner;

public class ManageTasks {
    public void runManageTasks() {
        try {
            choice();
        } catch (Exception e) {
            System.err.println("Managing tasks failed.");
            e.printStackTrace();
        }
    }

    private void choice() {
        System.out.println("List of tasks:");
        this.listAllTasks();
        System.out.println("** End of list **");
        System.out.println("Wybierz jedną z opcji:\n" +
                "add – dodanie zadania\n" +
                "edit – edycja zadania\n" +
                "delete – usunięcie zadania\n" +
                "quit – zakończenie programu zarządzania zadaniami");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().toLowerCase();

        switch (choice) {
            case "add":
                addTask();
                choice();
                break;
            case "edit":
                editTask();
                choice();
                break;
            case "delete":
                deleteTask();
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

    private static void addTask() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj nazwę zadania:");
        String exTitle = scanner.nextLine();
        System.out.println("Podaj opis zadania:");
        String exDescription = scanner.nextLine();

        Exercise exercise = new Exercise();
        exercise.setTitle(exTitle);
        exercise.setDescription(exDescription);
        exercise.saveToDb();
    }

    private static void editTask() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj id zadania do edycji (0 doda zadanie):");
        int exId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj nową nazwę zadania:");
        String exTitle = scanner.nextLine();
        System.out.println("Podaj nowy opis zadania:");
        String exDescription = scanner.nextLine();

        Exercise exercise = new Exercise(exId, exTitle, exDescription);
        exercise.update();
    }

    private void listAllTasks() {
        Exercise[] exercises = new Exercise().loadAll();
        for (Exercise exercise : exercises) {
            System.out.println(exercise.getId() + ", " + exercise.getTitle() + ", " + exercise.getDescription());
        }
    }

    private static void deleteTask() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj id zadania do USUNIĘCIA:");
        int exId = scanner.nextInt();

        Exercise exercise = new Exercise(exId, "", "");
        exercise.delete();
    }
}