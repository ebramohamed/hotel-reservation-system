package Models;


public class Hotel
{
    private int id;
    private int userId;
    private String name;
    private String description;
    private String phoneNumber;
    private String imagePath;
    private double rates;
    private int numberOfRates;
    private int stars;
    private String address;
    private String governorate;
    private String city;
    private String zipcode;
    private double longitude;
    private double latitude;
    private String creationDate;

    public int getId()
    {
        return this.id;
    }

    public int getUserId()
    {
        return this.userId;
    }

    public String getName()
    {
        return this.name;
    }
    
    public String getDescription()
    {
        return this.description;
    }

    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public String getImagePath()
    {
        return this.imagePath;
    }

    public double getRates()
    {
        return this.rates;
    }

    public int getNumberOfRates()
    {
        return this.numberOfRates;
    }

    public int getStars()
    {
        return this.stars;
    }

    public String getAddress()
    {
        return this.address;
    }

    public String getGovernorate()
    {
        return this.governorate;
    }

    public String getCity()
    {
        return this.city;
    }

    public String getZipcode()
    {
        return this.zipcode;
    }

    public double getLongitude()
    {
        return this.longitude;
    }

    public double getLatitude()
    {
        return this.latitude;
    }

    public String getCreationDate()
    {
        return this.creationDate;
    }

    public void setId(int _id)
    {
        this.id = _id;
    }

    public void setUserId(int _userId)
    {
        this.userId = _userId;
    }

    public void setName(String _name)
    {
        this.name = _name;
    }
    
    public void setDescription(String _description)
    {
        this.description = _description;
    }

    public void setPhoneNumber(String _phoneNumber)
    {
        this.phoneNumber = _phoneNumber;
    }

    public void setImagePath(String _imagePath)
    {
        this.imagePath = _imagePath;
    }

    public void setRates(double _rates)
    {
        this.rates = _rates;
    }

    public void setNumberOfRates(int _numberOfRates)
    {
        this.numberOfRates = _numberOfRates;
    }

    public void setStars(int _stars)
    {
        this.stars = _stars;
    }

    public void setAddress(String _address)
    {
        this.address = _address;
    }

    public void setGovernorate(String _governorate)
    {
        this.governorate = _governorate;
    }

    public void setCity(String _city)
    {
        this.city = _city;
    }

    public void setZipcode(String _zipcode)
    {
        this.zipcode = _zipcode;
    }

    public void setLongitude(double _longitude)
    {
        this.longitude = _longitude;
    }

    public void setLatitude(double _latitude)
    {
        this.latitude = _latitude;
    }

    public void setCreationDate(String _creationDate)
    {
        this.creationDate = _creationDate;
    }

    @Override
    public String toString()
    {
        return "Hotel{" + 
                "id=" + this.id + 
                ", userId=" + this.userId + 
                ", name=" + this.name + 
                ", description=" + this.description + 
                ", phoneNumber=" + this.phoneNumber + 
                ", imagePath=" + this.imagePath + 
                ", rates=" + this.rates + 
                ", numberOfRates=" + this.numberOfRates + 
                ", stars=" + this.stars + 
                ", address=" + this.address + 
                ", governorate=" + this.governorate + 
                ", city=" + this.city + 
                ", zipcode=" + this.zipcode + 
                ", longitude=" + this.longitude + 
                ", latitude=" + this.latitude + 
                ", creationDate=" + this.creationDate + '}';
    }
    
    
}
