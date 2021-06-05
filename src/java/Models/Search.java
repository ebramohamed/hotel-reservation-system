
package Models;


public class Search
{
    private int hotelId;
    private int userId;
    private Hotel hotel;
    private Room room;

    public int getHotelId()
    {
        return this.hotelId;
    }

    public int getUserId()
    {
        return this.userId;
    }

    public Hotel getHotel()
    {
        return this.hotel;
    }

    public Room getRoom()
    {
        return this.room;
    }

    public void setHotelId(int _hotelId)
    {
        this.hotelId = _hotelId;
    }

    public void setUserId(int _userId)
    {
        this.userId = _userId;
    }

    public void setHotel(Hotel _hotel)
    {
        this.hotel = _hotel;
    }

    public void setRoom(Room _room)
    {
        this.room = _room;
    }

    @Override
    public String toString()
    {
        return "Search{" + 
               "hotelId=" + this.hotelId + 
               ", userId=" + this.userId + 
               ", hotel=" + this.hotel +
               ", room=" + this.room + '}';
    }
    
    
    
    
}
