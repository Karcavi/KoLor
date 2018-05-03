package co.edu.konradlorenz.autenticacion;

import android.widget.TextView;

public class PublicationEntity {

    private String Title;
    private String Description;


    //created by karcavi
    private String User;
    private String email;


    public PublicationEntity() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
