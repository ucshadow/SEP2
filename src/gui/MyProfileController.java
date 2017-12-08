package gui;

import client.Controller;
import common.Response;
import common.User;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class MyProfileController {

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
//        if (user.getPicture() == null || user.getPicture().equals("") || user.getPicture().equals("null") ) {



        Image i = new Image(user.getPicture());
        profilePicture.setImage(i);
        profileUsernameLabel.setText(user.getUsername());
        profilePassword.setText(user.getPassword());
        profileFirstName.setText(user.getFirstName());
        profileSecondName.setText(user.getSecondName());
        profileLastName.setText(user.getLastName());
        profileCprLabel.setText(user.getCpr());
//        getAllUsersEvent(user);
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

    }

    private void getDepartmentsEvent(User user) {
        System.out.println("cpr:" + user.getCpr());
        controller.getMyWorkingDepartments(user.getCpr());
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

    private void responseReader(Response res) {
        System.out.println("HEre 1");

        if (res != null) {
            System.out.println("HEre 2");
            if (res.getResponse().equals("getMyWorkingDepartments")) {
                System.out.println("HEre 3");
                System.out.println(res.toString());
//                departments = (ArrayList<String>) res.getRespnoseObject();
//                System.out.println("Departments:" + departments.toString());
                getWorkingDepartments((ArrayList<String>) res.getRespnoseObject());
            }
        }
    }


    public void getWorkingDepartments(ArrayList<String> strings){
        System.out.println("Strings" + strings);

        String str = "";

        for (String string: strings){
            str+= string + "  ";
        }

        profileDepartmentsLabel.setText(str);
    }


    @FXML
    private void closePanel() {
        System.exit(0);
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
