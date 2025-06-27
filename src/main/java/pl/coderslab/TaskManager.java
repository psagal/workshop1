package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {
        System.out.println(ConsoleColors.BLUE + "Welcome to Task Manager\nPlease, choose one of the following options: ");
        System.out.println(ConsoleColors.RESET + " 1.add\n 2.remove\n 3.list\n 4.exit");
        String[][] taskArray = readFile();
        //taskArray = addTask(taskArray);
        taskList(taskArray);
        //System.out.println(Arrays.deepToString(taskArray));


    }

    public static String[][] readFile() {
        File file = new File("tasks.csv");
        String[][] taskArr = new String[0][3];
        int i = 0;

        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                taskArr = Arrays.copyOf(taskArr, taskArr.length + 1);
                taskArr[taskArr.length - 1] = line.split(", ");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return taskArr;
    }


    public static void inputOperation(String[][] array) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter here: ");
        String input = scanner.next().toLowerCase();

        switch (input) {
            case "1", "1.", "add" -> addTask(array);
            case "2", "2.", "remove" -> removeTask(array);
        }
    }

    public static String[][] addTask(String[][] array) {
        Scanner scanner = new Scanner(System.in);
        String[] newTask = new String[3];

        //pobranie opisu zadania ze spradzeniem poprawnosci danych
        while (true) {
            System.out.println("Enter task description: ");
            String localInput = scanner.nextLine();

            if (!((localInput == null) || (localInput.isBlank()))) {
                newTask[0] = localInput;
                break;
            } else System.out.println("Task description is empty!");
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
                System.out.println("Invalid date format!");
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
                System.out.println("Invalid input!");
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
            System.out.println("Enter the number of a task to remove: ");
            try {
                localInput = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input!");
            }
        }
        array = ArrayUtils.remove(array, localInput - 1);
        System.out.println("The task has been removed successfully.");
        return array;
    }

    public static void taskList(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print((i+1) + ": ");
            for (String s : array[i]) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }


}
