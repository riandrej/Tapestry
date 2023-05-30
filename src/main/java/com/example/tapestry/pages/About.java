package com.example.tapestry.pages;

import com.example.tapestry.employees.Employee;
import com.example.tapestry.employees.EmployeeService;
import com.example.tapestry.employees.Gender;
import com.example.tapestry.employees.Title;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.upload.services.UploadedFile;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class About {
    @Inject
    private EmployeeService employeeService;
    @Property
    private List<Employee> employees;
    @Property
    private Employee employee;
    @Property
    @Persist
    private Employee newEmployee;
    @Property
    @Persist
    private Employee selectedEmployee;
    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;
    @Inject
    private Block viewModal;
    @Inject
    private Block viewSpecific;
    @Inject
    private Block updatedName;
    @Property
    private String newEmployeeDateOfBirth;
    @Property
    private UploadedFile photoFile;
    @PageActivationContext
    private String learn;


    public Title getIng() {
        return Title.ING;
    }

    public Title getMudr() {
        return Title.MUDR;
    }

    public Title getJudr() {
        return Title.JUDR;
    }

    public Title getBc() {
        return Title.BC;
    }

    public Title getWtf() {
        return Title.WTF;
    }

    public Gender getMale() {
        return Gender.MALE;
    }

    public Gender getFemale() {
        return Gender.FEMALE;
    }

    public Gender getHybrid() {
        return Gender.HYBRID;
    }

    void onActivate() {
        employees = employeeService.getEmployees();
    }

    @SetupRender
    void setupRender() {
        newEmployee = new Employee();
        selectedEmployee = new Employee();
    }

    @OnEvent(component = "editEmployee")
    void editEmployeeForm(int index) {
        selectedEmployee = employees.get(index);
        ajaxResponseRenderer.addRender("updatedName", updatedName);
    }

    @OnEvent(component = "viewEmployee")
    void viewEmployeePhoto(int index) {
        selectedEmployee = employees.get(index);
        ajaxResponseRenderer.addRender("actualPhoto", viewModal);
        ajaxResponseRenderer.addRender("viewSpecific", viewSpecific);
    }

    @OnEvent(component = "deleteEmployee")
    void deleteEmployee(int index) {
        employees.remove(index);
        adjustEmployeeIds();
        employeeService.saveEmployees(employees);
    }

    private void adjustEmployeeIds() {
        for (int i = 0; i < employees.size(); i++) {
            employees.get(i).setId(i + 1);
        }
    }


    public void onSubmitFromEditForm() {
        if (photoFile != null) {
            String fileName = UUID.randomUUID() + "_" + photoFile.getFileName();
            String uploadPath = "src/main/webapp/images/" + fileName;
            File copiedPhoto = new File(uploadPath);
            photoFile.write(copiedPhoto);
            newEmployee.setPhotoFilePath("/images/" + fileName);
        }

        employeeService.editEmployeeAttribute(employees, employees.indexOf(selectedEmployee), newEmployee, newEmployeeDateOfBirth,selectedEmployee);

        employeeService.saveEmployees(employees);
        newEmployee = new Employee();
        selectedEmployee = new Employee();
    }

    public void onSubmitFromEmployeeForm() {
        Employee employee = new Employee();

        if (photoFile != null) {
            String fileName = UUID.randomUUID() + "_" + photoFile.getFileName();
            String uploadPath = "src/main/webapp/images/" + fileName;
            File copiedPhoto = new File(uploadPath);
            photoFile.write(copiedPhoto);
            employee.setPhotoFilePath("/images/" + fileName);
        }

        employee.setName(newEmployee.getName());
        employee.setLastName(newEmployee.getLastName());
        employee.setTitle(newEmployee.getTitle());
        employee.setGender(newEmployee.getGender());
        employee.setDateOfBirth(LocalDate.parse(newEmployeeDateOfBirth));

        List<Employee> employees = employeeService.getEmployees();
        employees.add(employee);

        employeeService.saveEmployees(employees);
        newEmployee = new Employee();

    }

    public String getLearn() {
        return learn;
    }

    public void setLearn(String learn) {
        this.learn = learn;
    }
}
