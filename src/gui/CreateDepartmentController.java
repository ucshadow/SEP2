package gui;

import client.Controller;
import common.Department;
import common.Response;
import common.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class CreateDepartmentController {

    private Controller controller;
    private User user;

    /**
     * Author: Radu Orleanu
     * <p>
     * Create user functionality for admin for creating departments.
     */
    @FXML
    private TextField departmentNumberCreate;
    @FXML
    private TextField departmentNameCreate;
    @FXML
    private TextField departmentLocationCreate;
    @FXML
    private TextField departmentManagerCreate;
    @FXML
    private ListView departmentList;

    private ArrayList<Department> arrayListDepartments = new ArrayList<>();
    private ObservableList<Department> observableListDepartments;

    @FXML
    private void createDept(ActionEvent event) {
        String dNumber = departmentNumberCreate.getText();
        String dName = departmentNameCreate.getText();
        String dLocation = departmentLocationCreate.getText();
        String dManager = departmentManagerCreate.getText();
        controller.createDepartment(dNumber, dName, dLocation, dManager);
    }

    private void populateDepartments(ArrayList<Department> departments) {
        observableListDepartments = FXCollections.observableArrayList();
        departments.forEach(System.out::println);
        arrayListDepartments.addAll(departments);
        for (Department d : arrayListDepartments) {
            observableListDepartments.add(d);
        }
        departmentList.getItems().setAll(observableListDepartments);
        departmentList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void selectDepartment() {
        Department selectedDepartment = (Department) departmentList.getSelectionModel().getSelectedItem();
        departmentNumberCreate.setText(selectedDepartment.getdNumber());

        departmentNameCreate.setText(selectedDepartment.getdName());
        departmentLocationCreate.setText(selectedDepartment.getdLocation());
        departmentManagerCreate.setText(selectedDepartment.getdManager());
    }

    @FXML
    public void getAllDepartmentsEvent() {
        controller.getAllDepartments();
        Task task = new Task<Response>() {
            @Override
            public Response call() {
                int tries = 0;
                while (tries < 10) {
                    Response r = controller.getLastResponse();
                    if (r != null) {
                        System.out.println("that null");

                        return r;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tries++;
                }
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(t -> responseReader((Response) task.getValue()));
    }

    private void responseReader(Response res) {
        if (res != null) {
            if (res.getResponse().equals("getAllDepartments")) {
                System.out.println(res.toString());
                populateDepartments((ArrayList<Department>) res.getRespnoseObject());
            }
        }
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
