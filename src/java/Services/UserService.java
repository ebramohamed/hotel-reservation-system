package Services;

import Models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
        
public class UserService extends MySqlService<User>
{
    public UserService()
    {
        this.tableName = "users";
        this.primaryKey = "id";
        this.columns = "email, username, phone_number, password, first_name, last_name, user_type, creation_date";
        this.values = "?,?,?,?,?,?,?,?";
    }
    
    @Override
    public User toTypeT(ResultSet _resultSet)
    {
        User user = null;
        try
        {
            user = new User();
            user.setId(_resultSet.getInt("id"));
            user.setEmail(_resultSet.getString("email"));
            user.setUsername(_resultSet.getString("username"));
            user.setPhoneNumber(_resultSet.getString("phone_number"));
            user.setPassword(_resultSet.getString("password"));
            user.setFirstName(_resultSet.getString("first_name"));
            user.setLastName(_resultSet.getString("last_name"));
            user.setUserType(_resultSet.getString("user_type"));
            user.setCreationDate(_resultSet.getString("creation_date"));
        }
        catch(Exception _e)
        {
            _e.printStackTrace();
        }
        
        return user;
    }

    @Override
    protected void setPreparedStatement(PreparedStatement _preparedStatement, User _user)
    {
        try
        {
            _preparedStatement.setString(1, _user.getEmail());
            _preparedStatement.setString(2, _user.getUsername());
            _preparedStatement.setString(3, _user.getPhoneNumber());
            _preparedStatement.setString(4, _user.getPassword());
            _preparedStatement.setString(5, _user.getFirstName());
            _preparedStatement.setString(6, _user.getLastName());
            _preparedStatement.setString(7, _user.getUserType());
            _preparedStatement.setString(8, _user.getCreationDate());
        }
        catch(Exception _e)
        {
            _e.printStackTrace();
        }
    }

    
}
