package programming_school;

import java.util.Scanner;

public class ManageGroups {
    public void runManageGroups() {
        try {
            choice();
        } catch (Exception e) {
            System.err.println("Managing groups failed.");
            e.printStackTrace();
        }
    }

    private void choice() {
        System.out.println("List of groups:");
        this.listAllGroups();
        System.out.println("** End of list **");
        System.out.println("Wybierz jedną z opcji:\n" +
                "add – dodanie grupy\n" +
                "edit – edycja grupy\n" +
                "delete – usunięcie grupy\n" +
                "quit – zakończenie programu zarządzania grupami");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().toLowerCase();

        switch (choice) {
            case "add":
                addGroup();
                choice();
                break;
            case "edit":
                editGroup();
                choice();
                break;
            case "delete":
                deleteGroup();
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

    private static void addGroup() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj nazwę grupy:");
        String grpName = scanner.nextLine();

        Group group = new Group();
        group.setName(grpName);
        group.saveToDb();
    }

    private static void editGroup() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj id grupy do edycji (0 doda grupę):");
        int grpId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj nową nazwę grupy:");
        String grpName = scanner.nextLine();

        Group group = new Group(grpId, grpName);
        group.update();
    }

    private void listAllGroups() {
        Group[] groups = new Group().loadAll();
        for (Group group : groups) {
            System.out.println(group.getId() + ", " + group.getName());
        }
    }

    private static void deleteGroup() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj id grupy do USUNIĘCIA:");
        int grpId = scanner.nextInt();

        Group group = new Group(grpId, "");
        group.delete();
    }
}