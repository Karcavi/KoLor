package co.edu.konradlorenz.autenticacion;

public class Usuario {
    private String email;
    private String name;
    private String lastname;
    private String password;
    private int phonenumber;


    public Usuario() {
    }

    public Usuario(String email, String name, String lastname, String password, int phonenumber) {
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }
}
