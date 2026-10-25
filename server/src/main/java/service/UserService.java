package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.UserData;

import java.util.Objects;
import java.util.UUID;

public class UserService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService (UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }


    public AuthData register(UserData user) throws BadRequestException, AlreadyTakenException {
        if (user.username() == null || user.email() == null || user.password() == null) {
            throw new BadRequestException("Error: bad request");
        }
        var result = userDAO.getUser(user.username());
        if (result != null) {
            throw new AlreadyTakenException("Error: already taken");
        }
        userDAO.createUser(user);
        String token = generateToken();
        AuthData authData = new AuthData(user.username(), token);
        authDAO.createAuth(authData);

        return authData;

    }

    public AuthData login(UserData user) throws BadRequestException, UnauthorizedException {
        if (user.username() == null || user.password() == null) {
            throw new BadRequestException("Error: bad request");
        }
        var result = userDAO.getUser(user.username());
        if (result == null || !Objects.equals(result.password(), user.password())) {
            throw new UnauthorizedException("Error: unauthorized");
        }
        String token = generateToken();
        AuthData authData = new AuthData(user.username(), token);
        authDAO.createAuth(authData);

        return authData;

    }


    private static String generateToken() {
        return UUID.randomUUID().toString();
    }

}

