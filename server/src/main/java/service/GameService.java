package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.GameData;

import java.util.List;
import java.util.Objects;

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
            throw new BadRequestException("Error: bad request");
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

    public void joinGame(String token, String playerColor, int gameID) throws UnauthorizedException, BadRequestException, AlreadyTakenException {
        AuthData getAuthResult = authDao.getAuth(token);
        if (getAuthResult == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }
        GameData getGameResult = gameDAO.getGame(gameID);
        if ((!Objects.equals(playerColor, "WHITE") && !Objects.equals(playerColor, "BLACK")) || getGameResult == null || gameID == 0) {
            throw new BadRequestException("Error: bad request");
        } else if ((playerColor.equals("WHITE") && getGameResult.whiteUsername() != null) || (playerColor.equals("BLACK") & getGameResult.blackUsername() != null)) {
            throw new AlreadyTakenException("Error: already taken");
        }

        gameDAO.updateGame(gameID, getAuthResult.username(), playerColor);
    }
}
