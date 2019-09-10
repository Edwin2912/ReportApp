package com.example.system;

public class User
{
    public String studentNr, email, phoneNr,role;

    public User()
    {

    }

    public User(String studentNr, String email,String phone,String role )
    {
        this.studentNr = studentNr;
        this.email = email;
        this.phoneNr = phone;
        this.role = role;
    }

    public void setStudentNr(String studentNr) {
        this.studentNr = studentNr;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public String getStudentNr()
     {
         return studentNr;
     }

    public String getEmail()
    {
        return email;

    }
    public String getPhoneNr()
    {
        return phoneNr;

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
