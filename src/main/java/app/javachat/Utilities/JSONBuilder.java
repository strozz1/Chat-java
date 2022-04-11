package app.javachat.Utilities;

import java.security.SecureRandom;

public class JSONBuilder {
    public static String parseMessageToJSON(String message, String username,String sender,String type) {
        String jsonParsed="{" +
                "\"type\":\""+type+"\"," +
                "\"content\":{" +
                "\"username\": \""+username+"\"," +
                "\"sender\": \""+sender+"\","+
                "\"content\": \""+message+"\"}}";
        return jsonParsed;
    }
    public static String parseRoomToJSON(String name, String[] users) {
        String id= generateRandomString(15);
        StringBuilder userString = new StringBuilder("[");
        for (int i = 0; i <users.length ; i++) {
            userString.append("\"").append(users[i]).append("\",");
        }
        String substring = userString.substring(0, userString.length()-1);
        substring+="]";

        String jsonParsed="{" +
                "\"type\":\"room\"," +
                "\"content\":{" +
                "\"id\":\""+id+"\"," +
                "\"name\":\""+name+"\"," +
                "\"users\":"+substring+
                "}}";

        return jsonParsed;
    }

    public static String generateRandomString(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {

            // 0-62 (exclusive), random returns 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);


            sb.append(rndChar);

        }

        System.out.println(sb);
        return sb.toString();

    }
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";

    private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
    private static SecureRandom random = new SecureRandom();
}
