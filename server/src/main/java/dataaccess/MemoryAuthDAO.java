package dataaccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO{
    private final HashMap<String, AuthData> auth = new HashMap<>();

    @Override
    public void clear() {
        auth.clear();
    }

    @Override
    public void createAuth(AuthData authData) {
        auth.put(authData.authToken(), authData);
    }

    @Override
    public AuthData getAuth(String token) {
        return auth.get(token);
    }
}
