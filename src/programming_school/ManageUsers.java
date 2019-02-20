package programming_school;

import java.util.Scanner;

public class ManageUsers {
    public void runManageUsers() {
        try {
            choice();
        } catch (Exception e) {
            System.err.println("Managing users failed.");
            e.printStackTrace();
        }
    }

    private void choice() {
        System.out.println("List of users:");
        this.listAllUsers();
        System.out.println("** End of list **");
        System.out.println("Wybierz jedną z opcji:\n" +
                "add – dodanie użytkownika\n" +
                "edit – edycja użytkownika\n" +
                "delete – usunięcie użytkownika\n" +
                "quit – zakończenie programu zarządzania użytkownikami");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().toLowerCase();

        switch (choice) {
            case "add":
                addUser();
                choice();
                break;
            case "edit":
                editUser();
                choice();
                break;
            case "delete":
                deleteUser();
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

    private static void addUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj nazwę użytkownika:");
        String uName = scanner.nextLine();
        System.out.println("Podaj hasło użytkownika:");
        String uPassword = scanner.nextLine();
        System.out.println("Podaj email użytkownika:");
        String uEmail = scanner.nextLine();
        System.out.println("Podaj id grupy użytkownika:");
        int uGroupId = scanner.nextInt();

        User user = new User();
        user.setUsername(uName);
        user.setPassword(uPassword);
        user.setEmail(uEmail);
        user.setUser_group_id(uGroupId);
        user.saveToDb();
    }

    private static void editUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj id użytkownika do edycji (0 doda użytkownika):");
        int uId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj nową nazwę użytkownika:");
        String uName = scanner.nextLine();
        System.out.println("Podaj nowe hasło użytkownika:");
        String uPassword = scanner.nextLine();
        System.out.println("Podaj nowy email użytkownika:");
        String uEmail = scanner.nextLine();
        System.out.println("Podaj nowe id grupy użytkownika:");
        int uGroupId = scanner.nextInt();

        User user = new User(uId, uName, uPassword, uEmail);
        user.setUser_group_id(uGroupId);
        user.update();
    }

    private void listAllUsers() {
        User[] users = new User().loadAll();
        for (User user : users) {
            System.out.println(user.getId() + ", " + user.getUsername() + ", " + user.getEmail() + ", " + user.getUser_group_id());
        }
    }

    private static void deleteUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj id użytkownika do USUNIĘCIA:");
        int uId = scanner.nextInt();

        User user = new User(uId, "", "", "");
        user.delete();
    }
}