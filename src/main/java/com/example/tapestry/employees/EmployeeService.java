package com.example.tapestry.employees;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private static final String FILE_DIRECTORY = "src/main/webapp";
    private static final String FILE_NAME = "/employees.csv";
    private static final String FILE_PATH = FILE_DIRECTORY + FILE_NAME;

    public void saveEmployees(List<Employee> employees) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {

            writer.println("Name,LastName,Title,Gender,DateOfBirth,PhotoFilePath");


            for (Employee employee : employees) {
                writer.println(employee.getName() + ","
                        + employee.getLastName() + ","
                        + employee.getTitle() + ","
                        + employee.getGender() + ","
                        + employee.getDateOfBirth() + ","
                        + employee.getPhotoFilePath());
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    String name = data[0].trim();
                    String lastName = data[1].trim();
                    Title title = Title.valueOf(data[2].trim());
                    Gender gender = Gender.valueOf(data[3].trim());
                    LocalDate dateOfBirth = LocalDate.parse(data[4]);
                    String photoFilePath = data[5].trim();

                    Employee employee = Employee.builder()
                            .name(name)
                            .lastName(lastName)
                            .title(title)
                            .gender(gender)
                            .dateOfBirth(dateOfBirth)
                            .photoFilePath(photoFilePath)
                            .build();

                    employees.add(employee);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public void editEmployeeAttribute(List<Employee> employees, int index, Employee newEmployee, String updatedDate) {
        Employee employee = employees.get(index);
        String name = newEmployee.getName() != null ? newEmployee.getName() : employee.getName();
        String lastName = newEmployee.getLastName() != null ? newEmployee.getLastName() : employee.getLastName();
        Title title = newEmployee.getTitle() != null ? newEmployee.getTitle() : employee.getTitle();
        Gender gender = newEmployee.getGender() != null ? newEmployee.getGender() : employee.getGender();
        LocalDate dateOfBirth = updatedDate != null ? LocalDate.parse(updatedDate) : employee.getDateOfBirth();
        String photoFilePath = newEmployee.getPhotoFilePath() != null ? newEmployee.getPhotoFilePath() : employee.getPhotoFilePath();

        try {
            Class<?> employeeClass = employee.getClass();

            Field[] fields = employeeClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                switch (fieldName) {
                    case "name":
                        field.set(employee, name);
                        break;
                    case "lastName":
                        field.set(employee, lastName);
                        break;
                    case "title":
                        field.set(employee, title);
                        break;
                    case "gender":
                        field.set(employee, gender);
                        break;
                    case "dateOfBirth":
                        field.set(employee, dateOfBirth);
                        break;
                    case "photoFilePath":
                        field.set(employee, photoFilePath);
                        break;
                }
            }
        } catch (IllegalAccessException | DateTimeParseException e) {
            e.printStackTrace();
        }


        saveEmployees(employees);
    }

}

