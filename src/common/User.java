package common;

public class User {

    private String username, firstName, secondName, lastName, CPR, dob, address, postcode, city, mobile, landline,
            email, konto, recnumber, licencePlate, prefferedCommunication, moreInfo;

    public User(){}

    public User(String username, String firstName, String secondName, String lastName, String CPR, String dob,
                String address, String postcode, String city, String mobile, String landline, String email,
                String konto, String regNumber, String licencePlate, String preferredCommunication, String moreInfo) {
        this.username = username;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.CPR = CPR;
        this.dob = dob;
        this.address = address;
        this.postcode = postcode;
        this.city = city;
        this.mobile = mobile;
        this.landline = landline;
        this.email = email;
        this.konto = konto;
        this.recnumber = regNumber;
        this.licencePlate = licencePlate;
        this.prefferedCommunication = preferredCommunication;
        this.moreInfo = moreInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCPR() {
        return CPR;
    }

    public void setCPR(String CPR) {
        this.CPR = CPR;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKonto() {
        return konto;
    }

    public void setKonto(String konto) {
        this.konto = konto;
    }

    public String getRecnumber() {
        return recnumber;
    }

    public void setRecnumber(String recnumber) {
        this.recnumber = recnumber;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getPrefferedCommunication() {
        return prefferedCommunication;
    }

    public void setPrefferedCommunication(String prefferedCommunication) {
        this.prefferedCommunication = prefferedCommunication;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (secondName != null ? !secondName.equals(user.secondName) : user.secondName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (CPR != null ? !CPR.equals(user.CPR) : user.CPR != null) return false;
        if (dob != null ? !dob.equals(user.dob) : user.dob != null) return false;
        if (address != null ? !address.equals(user.address) : user.address != null) return false;
        if (postcode != null ? !postcode.equals(user.postcode) : user.postcode != null) return false;
        if (city != null ? !city.equals(user.city) : user.city != null) return false;
        if (mobile != null ? !mobile.equals(user.mobile) : user.mobile != null) return false;
        if (landline != null ? !landline.equals(user.landline) : user.landline != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (konto != null ? !konto.equals(user.konto) : user.konto != null) return false;
        if (recnumber != null ? !recnumber.equals(user.recnumber) : user.recnumber != null) return false;
        if (licencePlate != null ? !licencePlate.equals(user.licencePlate) : user.licencePlate != null) return false;
        if (prefferedCommunication != null ? !prefferedCommunication.equals(user.prefferedCommunication) : user.prefferedCommunication != null)
            return false;
        return moreInfo != null ? moreInfo.equals(user.moreInfo) : user.moreInfo == null;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", CPR='" + CPR + '\'' +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                ", postcode='" + postcode + '\'' +
                ", city='" + city + '\'' +
                ", mobile='" + mobile + '\'' +
                ", landline='" + landline + '\'' +
                ", email='" + email + '\'' +
                ", konto='" + konto + '\'' +
                ", recnumber='" + recnumber + '\'' +
                ", licencePlate='" + licencePlate + '\'' +
                ", prefferedCommunication='" + prefferedCommunication + '\'' +
                ", moreInfo='" + moreInfo + '\'' +
                '}';
    }
}
