package sample;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by incloud on 2017/4/5.
 */
public class xmlMsg {
    private final SimpleStringProperty  name;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty age;
    private final SimpleStringProperty phone;

    public xmlMsg(String name, String gender, String age, String phone) {
        this.name = new SimpleStringProperty(name);
        this.gender = new SimpleStringProperty(gender);
        this.age = new SimpleStringProperty(age);
        this.phone = new SimpleStringProperty(phone);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getGender() {
        return gender.get();
    }

    public SimpleStringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getAge() {
        return age.get();
    }

    public SimpleStringProperty ageProperty() {
        return age;
    }

    public void setAge(String age) {
        this.age.set(age);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }
}
