package gui;

import client.Controller;
import common.Department;
import common.Response;
import common.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Random;

public class GUIController {

    User user;
    Controller c;


    /**
     * Author: Catalin Udrea
     * <p>
     * Create user functionality for admin.
     */
    @FXML
    private TextField userNameFieldCreate;
    @FXML
    private TextField CPRFieldCreate;
    @FXML
    private TextField passwordFieldCreate;
    @FXML
    private TextField userRoleCreate;
    @FXML
    private TextField userWageCreate;

    /**
     * Author: Yusuf Farah
     * <p>
     * My profile implementation.
     */
    @FXML
    private TextField profileUsernameLabel;
    @FXML
    private TextField profilePassword;
    @FXML
    private TextField profileUserRole;
    @FXML
    private TextField profileFirstName;
    @FXML
    private TextField profileSecondName;
    @FXML
    private TextField profileLastName;
    @FXML
    private TextField profileDOB;
    @FXML
    private TextField profileAddress;
    @FXML
    private TextField profilePostcode;
    @FXML
    private TextField profileCity;
    @FXML
    private TextField profileMobile;
    @FXML
    private TextField profileLandline;
    @FXML
    private TextField profileEmail;
    @FXML
    private TextField profileKonto;
    @FXML
    private TextField profileRecNumber;
    @FXML
    private TextField profileLicencePlate;
    @FXML
    private TextField profilePreferredCommunication;
    @FXML
    private TextField profilePicture;
    @FXML
    private TextField profileMoreInfo;
    @FXML
    private Label profileCprLabel;
    @FXML
    private Label profileWageLabel;
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
    private void handleCreateUserAdminPanel(ActionEvent event) {
        String name = userNameFieldCreate.getText();
        String cpr = CPRFieldCreate.getText();
        String pass = passwordFieldCreate.getText();
        String role = userRoleCreate.getText();
        String wage = userWageCreate.getText();
        User u = new User(name, pass, cpr, role, wage);
    }

    @FXML
    private void handleEditInformationClientPanel(ActionEvent event) {
        ArrayList<String> newUserInformation = new ArrayList<>();
        String s0 = profilePicture.getText();
        String s1 = profileUsernameLabel.getText();
        String s2 = profilePassword.getText();
        String s3 = profileFirstName.getText();
        String s4 = profileSecondName.getText();
        String s5 = profileLastName.getText();
        String s6 = profileCprLabel.getText();
        String s7 = profileDOB.getText();
        String s8 = profileAddress.getText();
        String s9 = profilePostcode.getText();
        String s10 = profileCity.getText();
        String s11 = profileMobile.getText();
        String s12 = profileLandline.getText();
        String s13 = profileEmail.getText();
        String s14 = profileKonto.getText();
        String s15 = profileRecNumber.getText();
        String s16 = profileLicencePlate.getText();
        String s17 = profilePreferredCommunication.getText();
        String s18 = profileMoreInfo.getText();
        String s19 = profileWageLabel.getText();
        String s20 = profileUserRole.getText();
        for (int i = 0; i < 21; i++) {
            newUserInformation.add("s" + i);
        }
    }

    public void setText(ActionEvent event) {
        Random rand = new Random();
        int n = rand.nextInt(10000 + 1);
        String str = Integer.toString(n);
        this.profileCprLabel.setText("0987654321");
        this.profileWageLabel.setText(str);
        this.profileUsernameLabel.setText("Yusuf AJ Farah");
    }

    @FXML
    private void createDept(ActionEvent event) {
        String dNumber = departmentNumberCreate.getText();
        String dName = departmentNameCreate.getText();
        String dLocation = departmentLocationCreate.getText();
        String dManager = departmentManagerCreate.getText();
        Department d = new Department(dNumber, dName, dLocation, dManager);
        c.createDepartment(d);
    }

    public void setUser(Response x) {
        System.out.println(x.getRespnoseObject());
        user = (User) x.getRespnoseObject();
        System.out.println(user);
        System.out.println(x);
    }

    public void setClientController(Controller c) {
        this.c = c;
    }
}
