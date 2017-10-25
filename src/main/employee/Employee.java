package main.employee;

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

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getCpr() {
        return cpr;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCity() {
        return city;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getEmail() {
        return email;
    }

    public String getKonto() {
        return konto;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public String getPrefferd() {
        return prefferd;
    }

    public String getMoreInformation() {
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
