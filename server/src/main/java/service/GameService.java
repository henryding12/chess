package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.AuthData;

import java.util.List;

public class GameService {
    private final AuthDAO authDao;
    private final GameDAO gameDAO;

    public GameService(AuthDAO authDao, GameDAO gameDAO) {
        this.authDao = authDao;
        this.gameDAO = gameDAO;
    }

    public int createGame(String token, String gameName) throws UnauthorizedException, BadRequestException {
        AuthData result = authDao.getAuth(token);
        if (result == null) {
            throw new UnauthorizedException("Error: unauthorized");
        } else if (gameName == null) {
            throw new BadRequestException(("Error: bad request"));
        }
        ChessGame game = new ChessGame();

        return gameDAO.addGame(gameName, game);

    }

    public List listGames(String token) throws UnauthorizedException {
        AuthData result = authDao.getAuth(token);
        if (result == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }
        return gameDAO.listGames();

    }
}
