package Models;


public class User
{
    private int id;
    private String email;
    private String username;
    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private String userType;
    private String creationDate;

    public int getId()
    {
        return this.id;
    }

    public String getEmail()
    {
        return this.email;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public String getPassword()
    {
        return this.password;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public String getUserType()
    {
        return this.userType;
    }

    public String getCreationDate()
    {
        return this.creationDate;
    }

    public void setId(int _id)
    {
        this.id = _id;
    }

    public void setEmail(String _email)
    {
        this.email = _email;
    }

    public void setUsername(String _username)
    {
        this.username = _username;
    }

    public void setPhoneNumber(String _phoneNumber)
    {
        this.phoneNumber = _phoneNumber;
    }

    public void setPassword(String _password)
    {
        this.password = _password;
    }

    public void setFirstName(String _firstName)
    {
        this.firstName = _firstName;
    }

    public void setLastName(String _lastName)
    {
        this.lastName = _lastName;
    }

    public void setUserType(String _userType)
    {
        this.userType = _userType;
    }

    public void setCreationDate(String _creationDate)
    {
        this.creationDate = _creationDate;
    }

    @Override
    public String toString() 
    {
        return "User{" + 
                "id=" + this.id + 
                ", email=" + this.email + 
                ", username=" + this.username + 
                ", phoneNumber=" + this.phoneNumber + 
                ", password=" + this.password + 
                ", firstName=" + this.firstName + 
                ", lastName=" + this.lastName + 
                ", userType=" + this.userType + 
                ", creationDate=" + this.creationDate + '}';
    }
}
