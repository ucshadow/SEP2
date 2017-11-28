package gui;

import client.Controller;
import common.Department;
import common.Response;
import common.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Random;

public class GUIController {

    User user;


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

    @FXML
    private TextField departmentNumberCreate;
    @FXML
    private TextField departmentNameCreate;
    @FXML
    private TextField departmentLocationCreate;
    @FXML
    private TextField departmentManagerCreate;

//    Controller c = new Controller();
    /**
     * Author: Yusuf Farah
     * <p>
     * My profile implementation.
     */
    @FXML
    private TextField profileUsername;
    @FXML
    private TextField profilePassword;
    @FXML
    private TextField profileCPR;
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
    private TextField profileWage;
    @FXML
    private TextField profileMoreInfo;
    @FXML
    private Label profileCprLabel;
    @FXML
    private Label profileWageLabel;
    @FXML
    private Label profileUsernameLabel;

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
        String userName = profileUsername.getText();
        String pass = profilePassword.getText();
        String cpr = profileCPR.getText();
        String role = profileUserRole.getText();
        String firstName = profileFirstName.getText();
        String secondName = profileSecondName.getText();
        String lastName = profileLastName.getText();
        String dob = profileDOB.getText();
        String address = profileAddress.getText();
        String postcode = profilePostcode.getText();
        String city = profileCity.getText();
        String mobile = profileMobile.getText();
        String landline = profileLandline.getText();
        String email = profileEmail.getText();
        String konto = profileKonto.getText();
        String recNumber = profileRecNumber.getText();
        String licence = profileLicencePlate.getText();
        String preferredcomm = profilePreferredCommunication.getText();
        String picture = profilePicture.getText();
        String wage = profileWage.getText();
        String moreInfo = profileMoreInfo.getText();
        User u = new User(picture, userName, pass, cpr, role, firstName, secondName, lastName, dob, address, postcode, city, mobile, landline, email, konto, recNumber, licence, preferredcomm, wage, moreInfo);
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
    public void createDept() {
    }

    public void setUser(Object x) {
        Response r = (Response) x;
        user = (User) r.getRespnoseObject();
        System.out.println(user);
    }
}
