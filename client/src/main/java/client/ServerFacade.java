package client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import model.AuthData;
import model.UserData;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerFacade {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;

    public ServerFacade(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    // throw exceptions later better
    public AuthData register(UserData user) throws Exception {
        var request = buildRequest("POST", "/user", user, null);
        var response = sendRequest(request);
        return handleResponse(response, AuthData.class);
    }

    public AuthData login(UserData user) throws Exception {
        var request = buildRequest("POST", "/session", user, null);
        var response = sendRequest(request);
        return handleResponse(response, AuthData.class);
    }

    public String logout(AuthData auth) throws Exception {
        var request = buildRequest("DELETE", "/session", null, auth);
        var response = sendRequest(request);
        handleResponse(response, null); // check for a status error
        return response.body();
    }

    // not in repl, just admin usage
    public String clear() throws Exception {
        var request = buildRequest("DELETE", "/db", null, null);
        var response = sendRequest(request);
        handleResponse(response, null);
        return response.body();

    }

//    public void deletePet(int id) throws ResponseException {
//        var path = String.format("/pet/%s", id);
//        var request = buildRequest("DELETE", path, null);
//        var response = sendRequest(request);
//        handleResponse(response, null);
//    }

//    public void deleteAllPets() throws ResponseException {
//        var request = buildRequest("DELETE", "/pet", null);
//        sendRequest(request);
//    }

//    public PetList listPets() throws ResponseException {
//        var request = buildRequest("GET", "/pet", null);
//        var response = sendRequest(request);
//        return handleResponse(response, PetList.class);
//    }

    private HttpRequest buildRequest(String method, String path, Object body, AuthData auth) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-Type", "application/json");
        } if (auth != null) {
            request.setHeader("authorization", auth.authToken());
        }
        return request.build();
    }

    private HttpRequest.BodyPublisher makeRequestBody(Object request) {
        if (request != null) {
            return HttpRequest.BodyPublishers.ofString(new Gson().toJson(request));
        } else {
            return HttpRequest.BodyPublishers.noBody();
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws Exception {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws Exception {
        var status = response.statusCode();
        if (!isSuccessful(status)) {
            var body = response.body();
            if (body != null) {
                throw new Exception(body + status);
            }

//            throw new ResponseException(ResponseException.fromHttpStatusCode(status), "other failure: " + status);
        }

        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }

        return null;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

}




