package Models;


public class Rate
{
    private int id;
    private int userId;
    private int hotelId;
    private int rate;
    private String comment;
    private String creationDate;

    public int getId()
    {
        return this.id;
    }

    public int getUserId()
    {
        return this.userId;
    }

    public int getHotelId()
    {
        return this.hotelId;
    }

    public int getRate()
    {
        return this.rate;
    }

    public String getComment()
    {
        return this.comment;
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

    public void setHotelId(int _hotelId)
    {
        this.hotelId = _hotelId;
    }

    public void setRate(int _rate)
    {
        this.rate = _rate;
    }

    public void setComment(String _comment)
    {
        this.comment = _comment;
    }

    public void setCreationDate(String _creationDate)
    {
        this.creationDate = _creationDate;
    }

    @Override
    public String toString()
    {
        return "Rate{" + "id=" + this.id + 
                ", userId=" + this.userId + 
                ", hotelId=" + this.hotelId + 
                ", rate=" + this.rate + 
                ", comment=" + this.comment + 
                ", creationDate=" + this.creationDate + '}';
    }
 
}
