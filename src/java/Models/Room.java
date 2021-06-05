package Models;

public class Room
{
    private int id;
    private int hotelId;
    private int number;
    private boolean isAvailable;
    private String type;
    private int numberOfAdults;
    private int numberOfChildren;
    private int pricePerDay;
    private String facilities;
    private String creationDate;

    public int getId()
    {
        return this.id;
    }

    public int getHotelId()
    {
        return this.hotelId;
    }

    public int getNumber()
    {
        return this.number;
    }

    public boolean getIsAvailable()
    {
        return this.isAvailable;
    }

    public String getType()
    {
        return this.type;
    }

    public int getNumberOfAdults()
    {
        return this.numberOfAdults;
    }

    public int getNumberOfChildren()
    {
        return this.numberOfChildren;
    }

    public int getPricePerDay()
    {
        return this.pricePerDay;
    }
    
    public String getFacilities()
    {
        return this.facilities;
    }
    
    public String getCreationDate()
    {
        return this.creationDate;
    }

    public void setId(int _id)
    {
        this.id = _id;
    }

    public void setHotelId(int _hotelId)
    {
        this.hotelId = _hotelId;
    }

    public void setNumber(int _number)
    {
        this.number = _number;
    }

    public void setIsAvailable(boolean _isAvailable)
    {
        this.isAvailable = _isAvailable;
    }

    public void setType(String _type)
    {
        this.type = _type;
    }

    public void setNumberOfAdults(int _numberOfAdults)
    {
        this.numberOfAdults = _numberOfAdults;
    }

    public void setNumberOfChildren(int _numberOfChildren)
    {
        this.numberOfChildren = _numberOfChildren;
    }

    public void setPricePerDay(int _pricePerDay)
    {
        this.pricePerDay = _pricePerDay;
    }
    
    public void setFacilities(String _facilities)
    {
        this.facilities = _facilities;
    }

    public void setCreationDate(String _creationDate)
    {
        this.creationDate = _creationDate;
    }

    @Override
    public String toString()
    {
        return "Room{" + 
                "id=" + this.id + 
                ", hotelId=" + this.hotelId + 
                ", number=" + this.number + 
                ", isAvailable=" + this.isAvailable + 
                ", type=" + this.type + 
                ", numberOfAdults=" + this.numberOfAdults + 
                ", numberOfChildren=" + this.numberOfChildren + 
                ", pricePerDay=" + this.pricePerDay + 
                ", facilities=" + this.facilities + 
                ", creationDate=" + this.creationDate + '}';
    }
}
