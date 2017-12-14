package client.gui;

import client.Controller;
import common.Response;
import common.User;
import common.helpers.Helpers;
import common.helpers.ResponseReader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class MyProfileController implements ResponseReader {

    private Controller controller;
    private User user;
    private ArrayList<String> departments;


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
    @FXML
    private Label profileDepartmentsLabel;

    @FXML
    private TextField newPicture;
    @FXML
    private ListView clientList;

    @FXML
    private void handleEditInformationClientPanel() {
        ArrayList<String> newUserInformation = new ArrayList<>();

        newUserInformation.add(newPicture.getText());
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

        controller.changeUserInformation(newUserInformation);
    }

    public void displayUser(User user) {
        Image i = new Image(user.getPicture(), true);
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


    }

    private void getDepartmentsEvent(User user) {
        controller.getMyWorkingDepartments(user.getCpr());
        Helpers.getLastResponse(controller, this);
    }

    public void responseReader(Response res) {

        if (res != null) {
            if (res.getResponse().equals("getMyWorkingDepartments")) {
                getWorkingDepartments((ArrayList<String>) res.getRespnoseObject());
            }
        }
    }


    public void getWorkingDepartments(ArrayList<String> strings){

        String str = "";

        for (String string: strings){
            str+= string + "  ";
        }

        profileDepartmentsLabel.setText(str);
    }


    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
        getDepartmentsEvent(user);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
