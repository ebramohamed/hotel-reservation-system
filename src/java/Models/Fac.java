package Models;


public class Fac
{
    private int id;
    private int roomId;
    private String name;

    public int getId()
    {
        return this.id;
    }

    public int getRoomId()
    {
        return this.roomId;
    }

    public String getName()
    {
        return this.name;
    }

    public void setId(int _id)
    {
        this.id = _id;
    }

    public void setRoomId(int _roomId)
    {
        this.roomId = _roomId;
    }

    public void setName(String _name)
    {
        this.name = _name;
    }

    @Override
    public String toString()
    {
        return "Facility{" + 
                "id=" + this.id + 
                ", roomId=" + this.roomId + 
                ", name=" + this.name + '}';
    }
}
