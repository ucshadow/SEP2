package old.gui;

//import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import old.main.employee.Employee;
import old.main.databasehandlers.EmployeeHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Employee information GUI
 *
 * @author Nikolay Dimitrov Nikolov
 * @version 1.2 adding Date Picker and Date Picker Skin
 */
public class EmployeeGUI {
    private Stage stage;
    private Scene mainScene;
    private BorderPane mainPane;
    private GridPane mainsetPane, buttonPane, datePane, calendarPane;
    private Label firstNameLabel, secondNameLabel, familyNameLabel, cprLabel, dateOfBirthLabel, addressLabel,
            postCodeLabel, cityLabel, mobilePhoneLabel, homePhoneLabel, licensePlateLabel, emailLabel, kontoLabel, regNumberLabel,
            preferredCommunicationLabel, moreInformationLabel;

    private TextField firstNameText, secondNameText, familyNameText, cprText, dateOfBirthText, addressText,
            postCodeText, cityText, mobilePhoneText, homePhoneText, licensePlateText, emailText, kontoText, regNumberText,
            moreInformationText;

    private ComboBox preferredCommunicationComboBox;

    private Button save, cancel, image, addDate, cancelDate;

    private DatePicker datePicker;
//    private DatePickerSkin datePickerSkin;

    private ArrayList<Label> allLabels;
    private ArrayList<TextField> allTextFields;
    private ArrayList<Button> allButtons;
    private EventHandler<ActionEvent> actionHandler = new MyAction();

    private static final String FONT = "*{Roboto};";
    private static final int SIZE = 12;
    private static final FontWeight FONT_WEIGHT = FontWeight.NORMAL;

    private Employee employeeMain;


    public void start() throws Exception {
        stage = new Stage();
        preLoad();
        stage.setTitle("Employee");
        stage.setScene(mainScene);
        stage.show();


    }

    /**
     * Preload all elements for Employee information GUI
     * Adding on action listener to buttons
     * Preload other methods
     */
    private void preLoad() {
        initialize();
        dateHandler();
        createLists();
        addElementsToPane();
        design();
        save.setOnAction(actionHandler);
        image.setOnAction(actionHandler);
        addDate.setOnAction(actionHandler);
        cancelDate.setOnAction(actionHandler);
        cancel.setOnAction(actionHandler);
    }

    /**
     * Creating design for Employee information GUI
     * Setting Alignment, Hgap, Vgap, Padding for Pane
     * Adding  font and size to text of labels and buttons using Array List created in createLists()
     */
    private void design() {


        //Design on mainsetPane
        mainsetPane.setAlignment(Pos.CENTER);
        mainsetPane.setHgap(10);
        mainsetPane.setVgap(10);
        mainsetPane.setPadding(new Insets(25, 25, 25, 25));

        //Design on buttonPane
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setHgap(10);
        buttonPane.setVgap(10);
        buttonPane.setPadding(new Insets(25, 25, 25, 25));

        for (Label item : allLabels) {
            item.setFont(Font.font(FONT, FONT_WEIGHT, SIZE));
        }
        for (Button item : allButtons) {
            item.setFont(Font.font(FONT, FONT_WEIGHT, SIZE));
        }
        for (TextField item : allTextFields) {
            item.setFont(Font.font(FONT, FONT_WEIGHT, SIZE));
        }

    }

    /**
     * Initializing all elements
     */
    private void initialize() {

        //Initializing BorderPane
        mainPane = new BorderPane();

        //Initializing GridPane
        mainsetPane = new GridPane();
        buttonPane = new GridPane();

        //Initializing Scene
        mainScene = new Scene(mainPane, 1400, 900);

        // Initializing Labels
        firstNameLabel = new Label("First name");
        secondNameLabel = new Label("Second name");
        familyNameLabel = new Label("Third name");
        cprLabel = new Label("CPR number");
        dateOfBirthLabel = new Label("Date of birth");
        addressLabel = new Label("Address");
        postCodeLabel = new Label("Post code");
        cityLabel = new Label("City");
        mobilePhoneLabel = new Label("Mobile");
        homePhoneLabel = new Label("Land line");
        emailLabel = new Label("Email");
        licensePlateLabel = new Label("License plate");
        kontoLabel = new Label("Konto");
        regNumberLabel = new Label("Reg Number");
        preferredCommunicationLabel = new Label("Preferred communication");
        moreInformationLabel = new Label("More information");


        //Initializing TextFields
        firstNameText = new TextField();
        secondNameText = new TextField();
        familyNameText = new TextField();
        cprText = new TextField();
        dateOfBirthText = new TextField();
        addressText = new TextField();
        postCodeText = new TextField();
        cityText = new TextField();
        mobilePhoneText = new TextField();
        homePhoneText = new TextField();
        emailText = new TextField();
        licensePlateText = new TextField();
        kontoText = new TextField();
        regNumberText = new TextField();
        moreInformationText = new TextField();

        //Initializing ComboBox
        preferredCommunicationComboBox = new ComboBox();

        //Initializing Buttons
        save = new Button("Save");
        cancel = new Button("Cancel");

        //Initializing ArrayList
        allLabels = new ArrayList<>();
        allTextFields = new ArrayList<>();
        allButtons = new ArrayList<>();
    }

    /**
     * Adding elements to Employee.sql pane
     */
    private void addElementsToPane() {

        // Adding Labels at column 0
        mainsetPane.add(firstNameLabel, 0, 0);
        mainsetPane.add(secondNameLabel, 0, 1);
        mainsetPane.add(familyNameLabel, 0, 2);
        mainsetPane.add(cprLabel, 0, 3);
        mainsetPane.add(dateOfBirthLabel, 0, 4);
        mainsetPane.add(addressLabel, 0, 5);
        mainsetPane.add(postCodeLabel, 0, 6);
        mainsetPane.add(cityLabel, 0, 7);
        mainsetPane.add(mobilePhoneLabel, 0, 8);
        mainsetPane.add(homePhoneLabel, 0, 9);
        mainsetPane.add(emailLabel, 0, 10);
        mainsetPane.add(kontoLabel, 0, 11);
        mainsetPane.add(regNumberLabel, 0, 12);
        mainsetPane.add(licensePlateLabel, 0, 13);
        mainsetPane.add(preferredCommunicationLabel, 0, 14);
        mainsetPane.add(moreInformationLabel, 0, 15);

        // Adding text fields at column 1

        mainsetPane.add(firstNameText, 1, 0);
        mainsetPane.add(secondNameText, 1, 1);
        mainsetPane.add(familyNameText, 1, 2);
        mainsetPane.add(cprText, 1, 3);
        mainsetPane.add(datePane, 1, 4);
        mainsetPane.add(addressText, 1, 5);
        mainsetPane.add(postCodeText, 1, 6);
        mainsetPane.add(cityText, 1, 7);
        mainsetPane.add(mobilePhoneText, 1, 8);
        mainsetPane.add(homePhoneText, 1, 9);
        mainsetPane.add(emailText, 1, 10);
        mainsetPane.add(kontoText, 1, 11);
        mainsetPane.add(regNumberText, 1, 12);
        mainsetPane.add(licensePlateText, 1, 13);
        mainsetPane.add(preferredCommunicationComboBox, 1, 14);
        mainsetPane.add(moreInformationText, 1, 15);

        //Adding buttons at column 1
        buttonPane.add(save, 0, 0);
        buttonPane.add(cancel, 1, 0);
        mainsetPane.add(buttonPane, 1, 16);
        mainPane.setCenter(mainsetPane);
    }

    /**
     * Adding elements to array for easy use of design
     */

    private void createLists() {

        Label[] tempLabels = {firstNameLabel, secondNameLabel, familyNameLabel, cprLabel, dateOfBirthLabel, addressLabel
                , postCodeLabel, cityLabel, mobilePhoneLabel, homePhoneLabel, emailLabel, licensePlateLabel, kontoLabel, regNumberLabel
                , preferredCommunicationLabel, moreInformationLabel};
        TextField[] tempTextFields = {firstNameText, secondNameText, familyNameText, cprText, dateOfBirthText
                , postCodeText, cityText, mobilePhoneText, homePhoneText, emailText, licensePlateText, kontoText
                , regNumberText, moreInformationText};
        Button[] tempButtons = {save, cancel, addDate, cancelDate};
        allButtons.addAll(Arrays.asList(tempButtons));
        allLabels.addAll(Arrays.asList(tempLabels));
        allTextFields.addAll(Arrays.asList(tempTextFields));
        preferredCommunicationComboBox.getItems().addAll("Mobile", "Home", "Email");
    }


    /**
     * Added in @version 1.3
     * Adding functionality for birthday field
     * Using Date Picker and Date Picker Skin
     * Initializing elements needed for calendar view of calendarPane
     */
    private void dateHandler() {

        calendarPane = new GridPane();
        //Initializing DatePicker
        datePane = new GridPane();
        datePicker = new DatePicker(LocalDate.now());
//        datePickerSkin = new DatePickerSkin(datePicker);
        image = new Button();
        addDate = new Button("Add date");
        cancelDate = new Button("Cancel");
//        calendarPane.add(datePickerSkin.getPopupContent(), 0, 1);
        calendarPane.add(addDate, 0, 2);
        calendarPane.add(cancelDate, 1, 2);


        //Date
        image.setStyle("-fx-background-image: url('/old/gui/images/download.png')");
        datePicker.setShowWeekNumbers(false);
        datePane.add(dateOfBirthText, 0, 0);
        datePane.add(image, 1, 0);
    }

    class MyAction implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            if (event.getSource() == save) {

                employeeMain = new Employee(firstNameText.getText(), secondNameText.getText(), familyNameText.getText(), cprText.getText(), dateOfBirthText.getText(), addressText.getText(), postCodeText.getText(), cityText.getText(), mobilePhoneText.getText(), homePhoneText.getText(), licensePlateText.getText(), emailText.getText(), kontoText.getText(), regNumberText.getText(), preferredCommunicationComboBox.getSelectionModel().getSelectedItem().toString(), moreInformationText.getText());

                EmployeeHandler.addNewEmployee(employeeMain);
            } else if (event.getSource() == image) {
                mainPane.setRight(calendarPane);
            } else if (event.getSource() == addDate) {
                LocalDate localDate = datePicker.getValue();
                dateOfBirthText.setText(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + localDate.getYear());
                mainPane.setRight(null);
            } else if (event.getSource() == cancelDate) {
                mainPane.setRight(null);
                dateOfBirthText.setText("");
            } else if (event.getSource() == cancel) {
                stage.close();
            }
        }
    }

}
