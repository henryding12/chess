package dataaccess;
import model.*;
import java.util.HashMap;
public class MemoryUserDAO implements UserDAO{
    final private HashMap<String, UserData> user = new HashMap<>();

    @Override
    public void clear() {
        user.clear();
    }

    @Override
    public void createAuth() {

    }

    @Override
    public UserData getUser(String username) {
        return user.get(username);
    }

    @Override
    public void createUser(UserData userData) {
        user.put(userData.username(), userData);
    }

}
