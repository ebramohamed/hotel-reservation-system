package Services;

import Models.Reservation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class ReservationService extends MySqlService<Reservation>
{
    public ReservationService()
    {
        this.tableName = "reservations";
        this.primaryKey = "id";
        this.columns = "user_id, room_id, status, check_in_date, check_out_date, creation_date";
        this.values = "?,?,?,?,?,?";
    }
    
    @Override
    protected Reservation toTypeT(ResultSet _resultSet)
    {
        Reservation reservation = null;
        try
        {
            reservation = new Reservation();
            reservation.setId(_resultSet.getInt("id"));
            reservation.setUserId(_resultSet.getInt("user_id"));
            reservation.setRoomId(_resultSet.getInt("room_id"));
            reservation.setStatus(_resultSet.getString("status"));
            reservation.setCheckInDate(_resultSet.getString("check_in_date"));
            reservation.setCheckOutDate(_resultSet.getString("check_out_date"));
            reservation.setCreationDate(_resultSet.getString("creation_date"));
        }
        catch(Exception _e)
        {
            _e.printStackTrace();
        }
        
        return reservation;
    }

    @Override
    protected void setPreparedStatement(PreparedStatement _preparedStatement, Reservation _reservation)
    {
        try
        {
            _preparedStatement.setInt(1, _reservation.getUserId());
            _preparedStatement.setInt(2, _reservation.getRoomId());
            _preparedStatement.setString(3, _reservation.getStatus());
            _preparedStatement.setString(4, _reservation.getCheckInDate());
            _preparedStatement.setString(5, _reservation.getCheckOutDate());
            _preparedStatement.setString(6, _reservation.getCreationDate());
        }
        catch(Exception _e)
        {
            _e.printStackTrace();
        }
    }
    
}
