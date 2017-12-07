package gui;

import client.Controller;
import common.Department;
import common.Response;
import common.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
//    lists
    @FXML
    private ListView departmentList;
    @FXML
    private ListView workerList;


    //    text fields
    @FXML
    private TextField departmentNumberCreate;
    @FXML
    private TextField departmentNameCreate;
    @FXML
    private TextField departmentLocationCreate;
    @FXML
    private TextField departmentManagerCreate;


    //    buttons
    @FXML
    private Button departmentCreateButton;
    @FXML
    private Button departmentSelectButton;
    @FXML
    private Button departmentEditButton;
    @FXML
    private Button departmentRemoveButton;
    @FXML
    private Button departmentLoadButton;
    @FXML
    private Button listRefreshButton;
    @FXML
    private Button departmentEmployeeLoadButton;

    private ArrayList<Department> arrayListDepartments;
    private ObservableList<Department> observableListDepartments;

    private ArrayList<User> arrayListWorkers;
    private ObservableList<String> observableListWorkers;

    private Department selectedDepartment;

    //    initialize method is called automatically
    public void initialize() {
        arrayListDepartments = new ArrayList<>();
        observableListDepartments = FXCollections.observableArrayList();

        arrayListWorkers = new ArrayList<>();
        observableListWorkers = FXCollections.observableArrayList();
        departmentList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        if (newValue != null) {
                            Department department = (Department) departmentList.getSelectionModel().getSelectedItem();
                            selectedDepartment = (Department) departmentList.getSelectionModel().getSelectedItem();
                            departmentNumberCreate.setText(department.getdNumber());
                            departmentNameCreate.setText(department.getdName());
                            departmentManagerCreate.setText(department.getdManager());
                            departmentLocationCreate.setText(department.getdLocation());
                        }
                    }
                }
        );
    }


    @FXML
    public void createDept(ActionEvent event) {
        String dNumber = departmentNumberCreate.getText();
        String dName = departmentNameCreate.getText();
        String dLocation = departmentLocationCreate.getText();
        String dManager = departmentManagerCreate.getText();
        controller.createDepartment(dNumber, dName, dLocation, dManager);
    }

    @FXML
    public void editDept(ActionEvent event) {
        ArrayList<String> newDepartment = readAllTextFields();
        controller.editDepartment(newDepartment.get(0), newDepartment.get(1), newDepartment.get(2), newDepartment.get(3), newDepartment.get(0));
        for (String s : newDepartment) {
            System.out.println(s);
        }
    }

    @FXML
    public void removeDept(ActionEvent event) {
        String departmentToRemove = departmentNumberCreate.getText();
        controller.deleteDepartment(departmentToRemove);
    }

    private ArrayList<String> readAllTextFields() {
        ArrayList<String> departmentDetails = new ArrayList<>();
        departmentDetails.add(departmentNumberCreate.getText());
        departmentDetails.add(departmentNameCreate.getText());
        departmentDetails.add(departmentLocationCreate.getText());
        departmentDetails.add(departmentManagerCreate.getText());
        return departmentDetails;
    }

    @FXML
    public void refreshDepartment() {
        departmentList.getItems().clear();
        departmentList.getSelectionModel().clearSelection();
    }

    @FXML
    public void refreshWorkers() {
        workerList.getItems().clear();
        workerList.getSelectionModel().clearSelection();
    }

    @FXML
    public void getAllDepartmentsEvent(ActionEvent event) {
        controller.getAllDepartments();
        Task task = new Task<Response>() {
            @Override
            public Response call() {
                int tries = 0;
                while (tries < 10) {
                    Response r = controller.getLastResponse();
                    if (r != null) {
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

    private void populateDepartments(ArrayList<Department> departments) {
        for (Department d : departments) {
            observableListDepartments.add(d);
        }
        departmentList.setItems(observableListDepartments);
        arrayListDepartments.addAll(departments);
    }

    private void responseReader(Response res) {
        if (res != null) {
            if (res.getResponse().equals("getAllDepartments")) {
                System.out.println(res.toString());
                populateDepartments((ArrayList<Department>) res.getRespnoseObject());
            }
            if (res.getResponse().equals("getuserbydepartment")) {
                System.out.println(res.toString());
                populateWorkersInDepartment((ArrayList<User>) res.getRespnoseObject());

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

    @FXML
    public void getAllUsersInDepartment(ActionEvent event) {
        controller.getUserByDepartment(selectedDepartment.getdNumber());
        Task task = new Task<Response>() {
            @Override
            public Response call() {
                int tries = 0;
                while (tries < 10) {
                    Response r = controller.getLastResponse();
                    if (r != null) {
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

    private void populateWorkersInDepartment(ArrayList<User> workers) {
        arrayListWorkers.addAll(workers);
        for (User u : workers) {
            observableListWorkers.add(u.getFirstName() + " " + u.getLastName() + " CPR: " + u.getCpr());
            System.out.println(u);
        }
        workerList.setItems(observableListWorkers);
    }
}
