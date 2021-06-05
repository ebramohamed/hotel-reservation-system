
package Services;

import Models.Fac;
import Models.Hotel;
import Models.Room;
import Models.Search;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchService extends MySqlService<Search> {

    @Override
    protected Search toTypeT(ResultSet _resultSet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void setPreparedStatement(PreparedStatement _preparedStatement, Search _t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Hotel> getAllBy(String _checkInDate, 
                                String _checkOutDate, 
                                String _adults, 
                                String _children, 
                                String _governorate, 
                                String _hotelName)
    {
        List<Hotel> list = null;

        try
        {
            list = new ArrayList<Hotel>();

            String sql = "SELECT DISTINCT rooms.hotel_id, hotels.* FROM hotels INNER JOIN rooms ON rooms.hotel_id = hotels.id "
                    + "WHERE rooms.is_available = '1' "
                    + "AND rooms.number_of_adults = ? "
                    + "AND rooms.number_of_children = ? " + "AND hotels.governorate = ? ";
            
            sql += " AND rooms.id NOT IN (SELECT reservations.room_id FROM reservations "
                    + "WHERE ((reservations.check_in_date BETWEEN ? AND ? ) OR (reservations.check_out_date BETWEEN ? AND ? )) "
                    + "AND reservations.status = 'confirmed') ";
            
            if(!_hotelName.equals(""))
                sql += " AND hotels.name = ? ";
            
            this.connection = ConnectionSqlDataBase.openConnection();
            this.preparedStatement = this.connection.prepareStatement(sql);
            this.preparedStatement.setString(1, _adults);
            this.preparedStatement.setString(2, _children);
            this.preparedStatement.setString(3, _governorate);
            
            this.preparedStatement.setString(4, _checkInDate);
            this.preparedStatement.setString(5, _checkOutDate);
            this.preparedStatement.setString(6, _checkInDate);
            this.preparedStatement.setString(7, _checkOutDate);
            
            
            if(!_hotelName.equals(""))
                this.preparedStatement.setString(8, _hotelName);
            
            this.resultSet = this.preparedStatement.executeQuery();
            System.out.println(sql);
            while (resultSet.next()) {

                Hotel hotel = new Hotel();

                hotel.setId(resultSet.getInt("id"));
                hotel.setUserId(resultSet.getInt("user_id"));
                hotel.setName(resultSet.getString("name"));
                hotel.setDescription(resultSet.getString("description"));
                hotel.setPhoneNumber(resultSet.getString("phone_number"));
                hotel.setImagePath(resultSet.getString("image_path"));
                hotel.setRates(resultSet.getDouble("rates"));
                hotel.setNumberOfRates(resultSet.getInt("number_of_rates"));
                hotel.setStars(resultSet.getInt("stars"));
                hotel.setAddress(resultSet.getString("address"));
                hotel.setGovernorate(resultSet.getString("governorate"));
                hotel.setCity(resultSet.getString("city"));
                hotel.setZipcode(resultSet.getString("zipcode"));
                hotel.setLongitude(resultSet.getDouble("longitude"));
                hotel.setLatitude(resultSet.getDouble("latitude"));
                hotel.setCreationDate(resultSet.getString("creation_date"));

                //search.setHotel(null);
                list.add(hotel);
                System.out.println(hotel);

            }
            //list.add(this.toTypeT(this.resultSet));
        } catch (SQLException _e) {
            _e.printStackTrace();
        }

        return list;
    }
    
    public List<Room> getRoomsBy(String _checkInDate, 
                                String _checkOutDate, 
                                String _adults, 
                                String _children,
                                Integer _hotelId)
    {
        List<Room> list = null;

        try
        {
            list = new ArrayList<Room>();

            String sql = "SELECT * FROM rooms INNER JOIN hotels ON rooms.hotel_id = hotels.id "
                    + "WHERE rooms.is_available = '1' "
                    + "AND rooms.number_of_adults = ? "
                    + "AND rooms.number_of_children = ? "
                    + "AND hotels.id = ? ";
            
            
            sql += " AND rooms.id NOT IN (SELECT reservations.room_id FROM reservations "
                    + "WHERE ((reservations.check_in_date BETWEEN ? AND ? ) OR (reservations.check_out_date BETWEEN ? AND ? )) "
                    + "AND reservations.status = 'confirmed') ORDER BY rooms.price_per_day ASC";
            
            
            
            this.connection = ConnectionSqlDataBase.openConnection();
            this.preparedStatement = this.connection.prepareStatement(sql);
            this.preparedStatement.setString(1, _adults);
            this.preparedStatement.setString(2, _children);
            this.preparedStatement.setInt(3, _hotelId);
            this.preparedStatement.setString(4, _checkInDate);
            this.preparedStatement.setString(5, _checkOutDate);
            this.preparedStatement.setString(6, _checkInDate);
            this.preparedStatement.setString(7, _checkOutDate);
            
            
            
            
            this.resultSet = this.preparedStatement.executeQuery();
            System.out.println(sql);
            while (resultSet.next()) {

                Room room = new Room();

                room.setId(resultSet.getInt("id"));
                room.setHotelId(resultSet.getInt("hotel_id"));
                room.setNumber(resultSet.getInt("number"));
                room.setIsAvailable(resultSet.getBoolean("is_available"));
                room.setType(resultSet.getString("type"));
                room.setNumberOfAdults(resultSet.getInt("number_of_adults"));
                room.setNumberOfChildren(resultSet.getInt("number_of_children"));
                room.setPricePerDay(resultSet.getInt("price_per_day"));
                room.setFacilities(resultSet.getString("facilities"));
                room.setCreationDate(resultSet.getString("creation_date"));

                //search.setHotel(null);
                list.add(room);
                System.out.println(room);

            }
            //list.add(this.toTypeT(this.resultSet));
        } catch (SQLException _e) {
            _e.printStackTrace();
        }

        return list;
    }

}
