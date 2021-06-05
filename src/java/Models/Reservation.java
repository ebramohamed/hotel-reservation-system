package Models;


public class Reservation
{
    private int id;
    private int userId;
    private int roomId;
    private String status;
    private String checkInDate;
    private String checkOutDate;
    private String creationDate;

    public int getId()
    {
        return this.id;
    }

    public int getUserId()
    {
        return this.userId;
    }

    public int getRoomId()
    {
        return this.roomId;
    }

    public String getStatus()
    {
        return this.status;
    }
    
    public String getCheckInDate()
    {
        return this.checkInDate;
    }
    
    public String getCheckOutDate()
    {
        return this.checkOutDate;
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

    public void setRoomId(int _roomId)
    {
        this.roomId = _roomId;
    }

    public void setStatus(String _status)
    {
        this.status = _status;
    }
    
    public void setCheckInDate(String _checkInDate)
    {
        this.checkInDate = _checkInDate;
    }
    
    public void setCheckOutDate(String _checkOutDate)
    {
        this.checkOutDate = _checkOutDate;
    }

    public void setCreationDate(String _creationDate)
    {
        this.creationDate = _creationDate;
    }

    @Override
    public String toString()
    {
        return "Reservation{" + 
                "id=" + this.id + 
                ", userId=" + this.userId + 
                ", roomId=" + this.roomId + 
                ", status=" + this.status +
                ", checkInDate=" + this.checkInDate + 
                ", checkOutDate=" + this.checkOutDate + 
                ", creationDate=" + this.creationDate + '}';
    }

    
}
