package co.edu.konradlorenz.autenticacion;

import android.widget.TextView;

public class PublicationEntity {

    private String Title;
    private String Description;

    private String Category;
    //created by karcavi
    private String User;
    private int Kind;
    //private String email;


    public PublicationEntity() {
    }

    public PublicationEntity(String title) {
        Title = title;
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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getKind() {
        return Kind;
    }

    public void setKind(int kind) {
        Kind = kind;
    }
}
