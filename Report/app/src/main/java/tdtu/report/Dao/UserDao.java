package tdtu.report.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tdtu.report.Model.User;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();


    @Query("SELECT * FROM user WHERE email = :email")
    User getUserByEmail(String email);


    @Delete
    void delete(User user);

    @Update
    void update(User user);



    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    User getUserByEmailAndPassword(String email, String password);
}
