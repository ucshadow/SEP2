package gui;

import client.Controller;
import common.Department;
import common.Response;
import common.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private Label profileUsernameLabel;
    @FXML
    private TextField profilePassword;
    @FXML
    private Label profileUserRole;
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
    private ImageView profilePicture;
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
        c.createUser(name, pass, cpr, role, wage);
    }

    @FXML
    private void handleEditInformationClientPanel(ActionEvent event) {
        ArrayList<String> newUserInformation = new ArrayList<>();

        newUserInformation.add("");
        newUserInformation.add(profileUsernameLabel.getText());
        newUserInformation.add(profilePassword.getText());
        newUserInformation.add(profileFirstName.getText());
        newUserInformation.add(profileSecondName.getText());
        newUserInformation.add(profileLastName.getText());
        newUserInformation.add(profileCprLabel.getText());
        newUserInformation.add(profileDOB.getText());
        newUserInformation.add(profileAddress.getText());
        newUserInformation.add(profilePostcode.getText());
        newUserInformation.add(profileCity.getText());
        newUserInformation.add(profileMobile.getText());
        newUserInformation.add(profileLandline.getText());
        newUserInformation.add(profileEmail.getText());
        newUserInformation.add(profileKonto.getText());
        newUserInformation.add(profileRecNumber.getText());
        newUserInformation.add(profileLicencePlate.getText());
        newUserInformation.add(profilePreferredCommunication.getText());
        newUserInformation.add(profileMoreInfo.getText());
        newUserInformation.add(profileWageLabel.getText());
        newUserInformation.add(profileUserRole.getText());

        c.changeUserInformation(newUserInformation);
    }

//    public void setText(ActionEvent event) {
//        Random rand = new Random();
//        int n = rand.nextInt(10000 + 1);
//        String str = Integer.toString(n);
//        this.profileCprLabel.setText("0987654321");
//        this.profileWageLabel.setText(str);
//        this.profileUsernameLabel.setText("Yusuf AJ Farah");
//    }

    @FXML
    private void createDept(ActionEvent event) {
        String dNumber = departmentNumberCreate.getText();
        String dName = departmentNameCreate.getText();
        String dLocation = departmentLocationCreate.getText();
        String dManager = departmentManagerCreate.getText();
        c.createDepartment(dNumber, dName, dLocation, dManager);
    }

//    public void setUser(Response x) {
//        System.out.println(x.getRespnoseObject());
//        user = (User) x.getRespnoseObject();
//        System.out.println(user);
//        System.out.println(x);
////        Image i = new Image(user.getPicture());
////        profilePicture.setImage(i);
//    }

    public void setUser(Response x) {
        System.out.println(x.getRespnoseObject());
        user = (User) x.getRespnoseObject();
        if(user.getPicture().equals("")) {
            user.setPicture("https://supercharge.info/images/avatar-placeholder.png");
        }
        Image i = new Image(user.getPicture());
        profilePicture.setImage(i);
        profileUsernameLabel.setText(user.getUsername());
        profilePassword.setText(user.getPassword());
        profileFirstName.setText(user.getFirstName());
        profileSecondName.setText(user.getSecondName());
        profileLastName.setText(user.getLastName());
        profileCprLabel.setText(user.getCpr());
        profileDOB.setText(user.getDob());
        profileAddress.setText(user.getAddress());
        profilePostcode.setText(user.getPostcode());
        profileCity.setText(user.getCity());
        profileMobile.setText(user.getMobile());
        profileLandline.setText(user.getLandline());
        profileEmail.setText(user.getEmail());
        profileKonto.setText(user.getKonto());
        profileRecNumber.setText(user.getRecnumber());
        profileLicencePlate.setText(user.getLicencePlate());
        profilePreferredCommunication.setText(user.getPrefferedCommunication());
        profileMoreInfo.setText(user.getMoreInfo());
        profileWageLabel.setText(user.getWage());
        profileUserRole.setText(user.getUserRole());


        System.out.println(user);
        System.out.println(x);
    }

    public void setClientController(Controller c) {
        this.c = c;
    }
}
