package Services;

import Models.Room;


import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class RoomService extends MySqlService<Room>
{
    public RoomService()
    {
        this.tableName = "rooms";
        this.primaryKey = "id";
        this.columns = "hotel_id, number, is_available, type, number_of_adults, number_of_children, price_per_day, facilities, creation_date";
        this.values = "?,?,?,?,?,?,?,?,?";
    }

    @Override
    protected Room toTypeT(ResultSet _resultSet)
    {
        Room room = null;
        try
        {
            room = new Room();
            room.setId(_resultSet.getInt("id"));
            room.setHotelId(_resultSet.getInt("hotel_id"));
            room.setNumber(_resultSet.getInt("number"));
            room.setIsAvailable(_resultSet.getBoolean("is_available"));
            room.setType(_resultSet.getString("type"));
            room.setNumberOfAdults(_resultSet.getInt("number_of_adults"));
            room.setNumberOfChildren(_resultSet.getInt("number_of_children"));
            room.setPricePerDay(_resultSet.getInt("price_per_day"));
            room.setFacilities(_resultSet.getString("facilities"));
            room.setCreationDate(_resultSet.getString("creation_date"));
        }
        catch(Exception _e)
        {
            _e.printStackTrace();
        }
        
        return room;
    }

    @Override
    protected void setPreparedStatement(PreparedStatement _preparedStatement, Room _room)
    {
        try
        {
            _preparedStatement.setInt(1, _room.getHotelId());
            _preparedStatement.setInt(2, _room.getNumber());
            _preparedStatement.setBoolean(3, _room.getIsAvailable());
            _preparedStatement.setString(4, _room.getType());
            _preparedStatement.setInt(5, _room.getNumberOfAdults());
            _preparedStatement.setInt(6, _room.getNumberOfChildren());
            _preparedStatement.setInt(7, _room.getPricePerDay());
            _preparedStatement.setString(8, _room.getFacilities());
            _preparedStatement.setString(9, _room.getCreationDate());
        }
        catch(Exception _e)
        {
            _e.printStackTrace();
        }
    }
    
    
}
