package main;

public class Employee {
    private String firstName, secondName, familyName, cpr, dateOfBirth, address,
            postCode, city, mobilePhone, homePhone, licensePlate, email, konto, regNumber, prefferd,
            moreInformation;


    public Employee(String firstName, String secondName, String familyName, String cpr, String dateOfBirth, String address, String postCode, String city, String mobilePhone, String homePhone, String licensePlate, String email, String konto, String regNumber, String prefferd, String moreInformation) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.familyName = familyName;
        this.cpr = cpr;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.postCode = postCode;
        this.city = city;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.licensePlate = licensePlate;
        this.email = email;
        this.konto = konto;
        this.regNumber = regNumber;
        this.prefferd = prefferd;
        this.moreInformation = moreInformation;
        this.moreInformation = moreInformation;
    }

    String getFirstName() {
        return firstName;
    }

    String getSecondName() {
        return secondName;
    }

    String getFamilyName() {
        return familyName;
    }

    String getCpr() {
        return cpr;
    }

    String getDateOfBirth() {
        return dateOfBirth;
    }

    String getAddress() {
        return address;
    }

    String getPostCode() {
        return postCode;
    }

    String getCity() {
        return city;
    }

    String getMobilePhone() {
        return mobilePhone;
    }

    String getHomePhone() {
        return homePhone;
    }

    String getLicensePlate() {
        return licensePlate;
    }

    String getEmail() {
        return email;
    }

    String getKonto() {
        return konto;
    }

    String getRegNumber() {
        return regNumber;
    }

    String getPrefferd() {
        return prefferd;
    }

    String getMoreInformation() {

        return moreInformation;
    }


    @Override
    public String toString() {
        return "Employee.sql.EmployeeGUI{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", cpr='" + cpr + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", address='" + address + '\'' +
                ", postCode='" + postCode + '\'' +
                ", city='" + city + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", homePhone='" + homePhone + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", email='" + email + '\'' +
                ", konto='" + konto + '\'' +
                ", regNumber='" + regNumber + '\'' +
                ", prefferd='" + prefferd + '\'' +
                ", moreInformation='" + moreInformation + '\'' +
                '}';
    }
}
