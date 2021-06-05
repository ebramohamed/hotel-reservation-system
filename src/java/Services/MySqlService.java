package Services;

import Models.Hotel;
import Models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;


public abstract class MySqlService<T> implements IService<T>
{      
    protected Connection connection = null;
    protected Statement statement = null;
    protected PreparedStatement preparedStatement = null;
    protected ResultSet resultSet = null;
    
    protected String tableName;
    protected String primaryKey;
    protected String columns;
    protected String values;
    
    protected abstract T toTypeT(ResultSet _resultSet);
    protected abstract void setPreparedStatement(PreparedStatement _preparedStatement, T _t);
    
    public T get(int _id)
    {
        T t = null;
        String sql = "SELECT * FROM " + this.tableName + " WHERE " + this.primaryKey + " = ?";
        
        try
        {
            this.connection = ConnectionSqlDataBase.openConnection();
            this.preparedStatement = this.connection.prepareStatement(sql);
            this.preparedStatement.setInt(1, _id);
            this.resultSet = this.preparedStatement.executeQuery();
            
            if(this.resultSet.next())
                t = this.toTypeT(this.resultSet);
        }
        catch(Exception _e)
        {
            _e.printStackTrace();
        }

        return t;
    }
    
    public T getBy(String[] _keys, String[] _values)
    {
        T t = null;
        
        String sql = "SELECT * FROM " + this.tableName + " WHERE ";
        
        for(int i = 0; i < _keys.length; i++)
        {
            sql += _keys[i] + " = ? ";
            
            if(i != _keys.length - 1 )
                sql += " AND ";
        }
        
        try
        {
            this.connection = ConnectionSqlDataBase.openConnection();
            this.preparedStatement = this.connection.prepareStatement(sql);
            
            for(int i = 1; i <= _values.length; i++)
            {
                this.preparedStatement.setString(i, _values[i - 1]);
            }
            
            this.resultSet = this.preparedStatement.executeQuery();
            
            if(this.resultSet.next())
                t = this.toTypeT(this.resultSet);
        }
        catch(Exception _e)
        {
            _e.printStackTrace();
        }

        return t;
    }
    
    public List<T> getAll()
    {
        List<T> list = null;

        try
        {
            list = new ArrayList<T>();
            
            String sql = "SELECT * FROM " + this.tableName;
            this.connection = ConnectionSqlDataBase.openConnection();
            this.statement = this.connection.createStatement();
            this.resultSet = this.statement.executeQuery(sql);
            
            while(resultSet.next())
                list.add(this.toTypeT(this.resultSet));
        }
        catch(SQLException _e) 
        {
            _e.printStackTrace();
        }
        
        return list;
    }
    
    public List<T> getAllWhere(String _key, int _id)
    {
        List<T> list = null;

        try
        {
            list = new ArrayList<T>();
            
            String sql = "SELECT * FROM " + this.tableName + " WHERE " + _key + " = " + _id;
            this.connection = ConnectionSqlDataBase.openConnection();
            this.statement = this.connection.createStatement();
            this.resultSet = this.statement.executeQuery(sql);
            
            while(resultSet.next())
                list.add(this.toTypeT(this.resultSet));
        }
        catch(SQLException _e) 
        {
            _e.printStackTrace();
        }
        
        return list;
    }
    
    public int save(T _t)
    {
        int flag = 0;
        
        try
        {
            String sql = "INSERT INTO " + this.tableName + "(" + this.columns + ") VALUES(" + this.values + ")";
            this.connection = ConnectionSqlDataBase.openConnection();
            System.out.println(sql);
            this.preparedStatement = this.connection.prepareStatement(sql);
            this.setPreparedStatement(this.preparedStatement, _t);
            flag = this.preparedStatement.executeUpdate();
            
        }
        catch(SQLException _ex) 
        {
            _ex.printStackTrace();
        }
        
        return flag;
    }
    
    public int updateRowByColumn(String c, String v, int id)
    {
        int flag = 0;
        
        try
        {
            String sql = "UPDATE " + this.tableName + " SET "+c+"= '"+v+"' WHERE " + this.primaryKey + " = " + id;
            System.out.println(sql);
            this.connection = ConnectionSqlDataBase.openConnection();
            this.statement = this.connection.createStatement();
            
            
            flag = this.statement.executeUpdate(sql);
        }
        catch(SQLException _ex) 
        {
            _ex.printStackTrace();
        }
        
        return flag;
    }
    
    public int updateUser(User _t)
    {
        int flag = 0;
        
        try
        {
            
            String sql = "UPDATE users SET first_name = '" + _t.getFirstName()+ "', email = '"+_t.getEmail()+"' ,"
                    + "last_name = '" + _t.getLastName()+ "', phone_number = '" + _t.getPhoneNumber() + 
                    "', password= '"+_t.getPassword()+"' where id=" + _t.getId();
            
            connection = ConnectionSqlDataBase.openConnection();
            preparedStatement = connection.prepareStatement(sql);
            flag = preparedStatement.executeUpdate();
        }
        catch(SQLException _ex) 
        {
            _ex.printStackTrace();
        }
        
        return flag;
    }
    
    public int updateHotel(Hotel _t)
    {
        int flag = 0;
        
        try
        {
            
            String sql = "UPDATE hotels SET name = '" + _t.getName()+ "', description = '"+_t.getDescription()+"' ,"
                    + "phone_number = '" + _t.getPhoneNumber()+ "', stars = '" + _t.getStars()+ 
                    "', address = '" + _t.getAddress()+ "', governorate = '" + _t.getGovernorate()+ 
                    "', city = '" + _t.getCity()+ "', zipcode = '" + _t.getZipcode()+ 
                    "', longitude = '" + _t.getLongitude()+ "', latitude = '" + _t.getLatitude()+ "' where id=" + _t.getId();
            
            connection = ConnectionSqlDataBase.openConnection();
            preparedStatement = connection.prepareStatement(sql);
            flag = preparedStatement.executeUpdate();
        }
        catch(SQLException _ex) 
        {
            _ex.printStackTrace();
        }
        
        return flag;
    }
    
    //public void update(T _t, String[] _params);
    
    public boolean delete(int _id)
    {
        boolean flag = false;
        try
        {
            String sql = "DELETE FROM " + this.tableName + " WHERE " + this.primaryKey + " = ?";
            
            this.connection = ConnectionSqlDataBase.openConnection();
            this.preparedStatement = this.connection.prepareStatement(sql);
            this.preparedStatement.setInt(1, _id);
            this.preparedStatement.executeUpdate();
            
            flag = true;
        }
        catch(SQLException _e)
        {
            _e.printStackTrace();
        }
        
        return flag;
    }
    
    public List<T> getRes(String in, String out, int _userId)
    {
        List<T> list = null;

        try
        {
            list = new ArrayList<T>();
            
            String sql = "SELECT * FROM reservations " +
                        "INNER JOIN rooms ON rooms.id = reservations.room_id " +
                        "INNER JOIN hotels ON hotels.id = rooms.hotel_id " +
                        "INNER JOIN users ON users.id = hotels.user_id " +
                        "WHERE users.id = " + _userId; 
            
            if(in != null && !in.equals("") && out != null && !out.equals(""))
            {
                sql = "SELECT * FROM reservations " +
                        "INNER JOIN rooms ON rooms.id = reservations.room_id " +
                        "INNER JOIN hotels ON hotels.id = rooms.hotel_id " +
                        "INNER JOIN users ON users.id = hotels.user_id " +
                        "WHERE users.id = " + _userId + " AND reservations.check_in_date = '" + in + 
                        "' AND reservations.check_out_date = '" + out + "'"; 
            }
            
            this.connection = ConnectionSqlDataBase.openConnection();
            this.statement = this.connection.createStatement();
            this.resultSet = this.statement.executeQuery(sql);
            
            while(resultSet.next())
                list.add(this.toTypeT(this.resultSet));
        }
        catch(SQLException _e) 
        {
            _e.printStackTrace();
        }
        
        return list;
    }
}