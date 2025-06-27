package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {
        String fileName = "tasks.csv";
        String[][] taskArray = readFile(fileName);

        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println(ConsoleColors.BLUE + "Welcome to Task Manager!");

        while (true) {
            System.out.println(ConsoleColors.PURPLE + "\nPlease, choose one of the following options: ");
            System.out.println(ConsoleColors.RESET + " 1.add\n 2.remove\n 3.list\n 4.exit");
            input = scanner.next().toLowerCase();

            if (input.equals("exit") || input.equals("4") || input.equals("4.")) {
                break;
            }

            switch (input) {
                case "1", "1.", "add" -> taskArray = addTask(taskArray);
                case "2", "2.", "remove" -> taskArray = removeTask(taskArray);
                case "3", "3.", "list" -> taskList(taskArray);
                default -> System.out.println(ConsoleColors.RED_BOLD + "Invalid input");
            }

        }
        saveFile(taskArray, fileName);
        System.out.println(ConsoleColors.RED + "Goodbye!");

    }

    public static String[][] readFile(String fileName) {
        File file = new File(fileName);
        String[][] taskArr = new String[0][3];
        int i = 0;

        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                taskArr = Arrays.copyOf(taskArr, taskArr.length + 1);
                taskArr[taskArr.length - 1] = line.split(", ");
            }

        } catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "File not found");
        }
        return taskArr;
    }

    public static void saveFile(String[][] taskArr, String fileName) {
        try (PrintWriter pw = new PrintWriter(fileName)) {
            for (String[] task : taskArr) pw.println(String.join(", ", task));
            System.out.println("Zapisano plik: " + fileName);
        } catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "Błąd zapisu do pliku");
        }
    }

    public static String[][] addTask(String[][] array) {
        Scanner scanner = new Scanner(System.in);
        String[] newTask = new String[3];
        System.out.print(ConsoleColors.CYAN);
        //pobranie opisu zadania ze spradzeniem poprawnosci danych
        while (true) {
            System.out.println("Enter task description: ");
            String localInput = scanner.nextLine();

            if (!((localInput == null) || (localInput.isBlank()))) {
                newTask[0] = localInput;
                break;
            } else System.out.println(ConsoleColors.RED + "Task description is empty!");
        }

        //pobranie daty ze sprawdzeniem danych
        while (true) {
            System.out.println("Enter task due date (YYYY-MM-DD): ");
            String localInput = scanner.nextLine();
            try {
                LocalDate.parse(localInput);
                newTask[1] = localInput;
                break;
            } catch (DateTimeParseException e) {
                System.out.println(ConsoleColors.RED + "Invalid date format!");
            }
        }

        //pobranie ważności zadania, ze sprawdzeniem
        while (true) {
            System.out.println("Is your task important: true/false: ");
            try {
                String localInput = String.valueOf(scanner.nextBoolean());
                newTask[2] = localInput;
                break;
            } catch (InputMismatchException e) {
                System.out.println(ConsoleColors.RED + "Invalid input!");
                scanner.nextLine();
            }

        }

        array = Arrays.copyOf(array, array.length + 1);
        array[array.length - 1] = newTask;
        return array;
    }

    public static String[][] removeTask(String[][] array) {
        Scanner scanner = new Scanner(System.in);
        int localInput;

        while (true) {
            System.out.println(ConsoleColors.CYAN  + "Enter the number of a task to remove \nIf you want to go back, choose a number greater then is available: " + ConsoleColors.RESET);
            taskList(array);
            try {
                localInput = scanner.nextInt();
                if (localInput > 0) {
                    break;
                }else {
                    System.out.println(ConsoleColors.YELLOW + "Number must be greater than 0\n");
                }
            } catch (InputMismatchException e) {
                System.out.println(ConsoleColors.RED + "Invalid input!");
            }
        }

        try {
            array = ArrayUtils.remove(array, localInput - 1);
            System.out.println(ConsoleColors.CYAN + "The task has been removed successfully.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(ConsoleColors.RED + "Invalid index! It should be between 1 and " + (array.length));
        }

        return array;
    }

    public static void taskList(String[][] array) {
        System.out.println(ConsoleColors.PURPLE + "\nCurrent tasks: " + ConsoleColors.RESET);
        for (int i = 0; i < array.length; i++) {
            System.out.print((i + 1) + ": ");
            for (String s : array[i]) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


}
