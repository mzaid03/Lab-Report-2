import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

class Handler implements URLHandler {
    StringBuilder chat = new StringBuilder();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/add-message")) {
            Map<String, String> parameters = getQueryMap(url.getQuery());
            String user = parameters.get("user");
            String message = parameters.get("s");
            if (user != null && message != null) {
                chat.append(user).append(": ").append(message).append("\n");
            }
            return chat.toString();
        } else {
            return "404 Not Found!";
        }
    }

    public Map<String, String> getQueryMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            String[] p = param.split("=");
            if (p.length > 1) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }
}

class ChatServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}