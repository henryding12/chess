package dataaccess;
import model.*;
import java.util.HashMap;
public class MemoryUserDAO implements UserDAO{
    final private HashMap<String, UserData> user = new HashMap<>();

    @Override
    public void clear() {
        user.clear();
    }
}
