package Services;

import Models.Rate;
import Models.Rating;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RateService extends MySqlService<Rate>
{
    public RateService()
    {
        this.tableName = "rates";
        this.primaryKey = "id";
        this.columns = "user_id, hotel_id, rate, comment, creation_date";
        this.values = "?,?,?,?,?";
    }
    
    @Override
    public Rate toTypeT(ResultSet _resultSet)
    {
        Rate rate = null;
        try
        {
            rate = new Rate();
            rate.setId(_resultSet.getInt("id"));
            rate.setUserId(_resultSet.getInt("user_id"));
            rate.setHotelId(_resultSet.getInt("hotel_id"));
            rate.setRate(_resultSet.getInt("rate"));
            rate.setComment(_resultSet.getString("comment"));
            rate.setCreationDate(_resultSet.getString("creation_date"));
        }
        catch(Exception _e)
        {
            _e.printStackTrace();
        }
        
        return rate;
    }

    @Override
    protected void setPreparedStatement(PreparedStatement _preparedStatement, Rate _rate)
    {
        try
        {
            _preparedStatement.setInt(1, _rate.getUserId());
            _preparedStatement.setInt(2, _rate.getHotelId());
            _preparedStatement.setInt(3, _rate.getRate());
            _preparedStatement.setString(4, _rate.getComment());
            _preparedStatement.setString(5, _rate.getCreationDate());
        }
        catch(Exception _e)
        {
            _e.printStackTrace();
        }
    }
    
    public List<Rating> getAllInner(String _key, int _id)
    {
        List<Rating> list = null;
        
        try
        {
            list = new ArrayList<Rating>();
            
            String sql = "SELECT * FROM " + this.tableName + " INNER JOIN users ON rates.user_id = users.id WHERE rates." + _key + " = " + _id;
            this.connection = ConnectionSqlDataBase.openConnection();
            this.statement = this.connection.createStatement();
            this.resultSet = this.statement.executeQuery(sql);
            
            while(resultSet.next())
            {
                Rating userRate = new Rating();
                userRate.firstName = resultSet.getString("first_name");
                userRate.lastName = resultSet.getString("last_name");
                userRate.rate = resultSet.getInt("rate");
                userRate.comment = resultSet.getString("comment");
                userRate.date = resultSet.getString("creation_date");
                list.add(userRate);
            }
                
        }
        catch(SQLException _e) 
        {
            _e.printStackTrace();
        }
        
        return list;
    }
}
