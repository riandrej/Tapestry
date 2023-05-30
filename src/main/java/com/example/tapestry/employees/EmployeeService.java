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
            writer.println("Id,Name,LastName,Title,Gender,DateOfBirth,PhotoFilePath");

            int maxId = 0;

            for (Employee employee : employees) {
                Integer id = employee.getId();
                if (id != null) {
                    writer.println(id + ","
                            + employee.getName() + ","
                            + employee.getLastName() + ","
                            + employee.getTitle() + ","
                            + employee.getGender() + ","
                            + employee.getDateOfBirth() + ","
                            + employee.getPhotoFilePath());
                    if (id > maxId) {
                        maxId = id;
                    }
                }
            }

            int nextId = maxId + 1;

            for (Employee employee : employees) {
                if (employee.getId() == null) {
                    employee.setId(nextId++);
                    writer.println(employee.getId() + ","
                            + employee.getName() + ","
                            + employee.getLastName() + ","
                            + employee.getTitle() + ","
                            + employee.getGender() + ","
                            + employee.getDateOfBirth() + ","
                            + employee.getPhotoFilePath());
                }
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
                if (data.length == 7) {
                    Integer id = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    String lastName = data[2].trim();
                    Title title = Title.valueOf(data[3].trim());
                    Gender gender = Gender.valueOf(data[4].trim());
                    LocalDate dateOfBirth = LocalDate.parse(data[5]);
                    String photoFilePath = data[6].trim();

                    Employee employee = Employee.builder()
                            .id(id)
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

    public void editEmployeeAttribute(List<Employee> employees, int index, Employee newEmployee, String updatedDate, Employee selectedEmployee) {
        Employee employee;
        if (index == -1) {
            employee = selectedEmployee;
            for(Employee emp : employees) {
                if(employee.getId().equals(emp.getId())) {
                    employees.remove(emp);
                    employees.add(employee);
                }
            }
        } else {
            employee = employees.get(index);
        }

        employee.setName(newEmployee.getName() != null ? newEmployee.getName() : employee.getName());
        employee.setLastName(newEmployee.getLastName() != null ? newEmployee.getLastName() : employee.getLastName());
        employee.setTitle(newEmployee.getTitle() != null ? newEmployee.getTitle() : employee.getTitle());
        employee.setGender(newEmployee.getGender() != null ? newEmployee.getGender() : employee.getGender());
        employee.setDateOfBirth(updatedDate != null ? LocalDate.parse(updatedDate) : employee.getDateOfBirth());
        employee.setPhotoFilePath(newEmployee.getPhotoFilePath() != null ? newEmployee.getPhotoFilePath() : employee.getPhotoFilePath());

        saveEmployees(employees);
    }

}

