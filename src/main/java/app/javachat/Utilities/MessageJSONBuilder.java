package app.javachat.Utilities;

public class MessageJSONBuilder {
    public static String parseMessageToJSON(String message, String username,String sender) {
        String jsonParsed="{" +
                "\"type\":\"message\"," +
                "\"content\":{" +
                "\"username\": \""+username+"\"," +
                "\"sender\": \""+sender+"\","+
                "\"content\": \""+message+"\"}}";
        return jsonParsed;
    }
}
