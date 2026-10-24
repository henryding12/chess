package dataaccess;

import model.UserData;

public interface UserDAO {
    void clear();

    void createAuth();
    UserData getUser(String username);
    void createUser(UserData user);

}
