package tdtu.report.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import tdtu.report.Model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT * FROM user WHERE email = :email")
    User getUserByEmail(String email);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    User getUserByEmailAndPassword(String email, String password);
}
