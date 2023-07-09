package Anant;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Employee {
    private int id;
    private String name;
    private String department;

    public Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "{  [ ID:  " + id + " ], [ Name:  " + name + " ], [ Department:  " + department+" ] }";
    }
}

class EmployeeManagementSystem {
    private List<Employee> employees;
    private File file;

    public EmployeeManagementSystem() {
        employees = new ArrayList<>();
        file = new File("employees.txt");
        loadEmployees();
    }

    private void loadEmployees() {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String department = data[2];
                Employee employee = new Employee(id, name, department);
                employees.add(employee);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No employees data found. Starting with an empty employee list.");
        }
    }

    public void addEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter employee details:");
        System.out.println();
        try {
            System.out.print("ID          :- ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consume the newline character
            System.out.print("Name        :- ");
            String name = scanner.nextLine();
            System.out.print("Department  :- ");
            String department = scanner.nextLine();
            Employee employee = new Employee(id, name, department);
            employees.add(employee);
            saveEmployees();
            System.out.println("===================================");
            System.out.println("Employee added successfully.");
            System.out.println("===================================");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Employee not added.");
        }
    }

    public void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        System.out.println("[ Employee List ]");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    public void updateEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the employee to update: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine(); // consume the newline character
            Employee employee = findEmployee(id);
            if (employee != null) {
                System.out.println("Enter new details for the employee:");
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Department: ");
                String department = scanner.nextLine();
                employee = new Employee(id, name, department);
                employees.remove(employee);
                employees.add(employee);
                saveEmployees();
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Employee not updated.");
        }
    }

    private Employee findEmployee(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    private void saveEmployees() {
        try (PrintWriter writer = new PrintWriter(file)) {
            for (Employee employee : employees) {
                writer.println(employee.getId() + "," + employee.getName() + "," + employee.getDepartment());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving employees data.");
        }
    }
}

public class Crud {
    public static void main(String[] args) {
        EmployeeManagementSystem system = new EmployeeManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nEmployee Management System");
            System.out.println("1. Add Employee     :-");
            System.out.println("2. View Employees   :-");
            System.out.println("3. Update Employee  :-");
            System.out.println("4. Exit             :-");
            System.out.println();
            System.out.println("===================================");
            System.out.println();
            System.out.print("Enter your choice   :- ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    system.addEmployee();
                    break;
                case 2:
                    system.viewEmployees();
                    break;
                case 3:
                    system.updateEmployee();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
