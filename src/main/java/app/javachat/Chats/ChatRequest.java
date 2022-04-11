//package app.javachat.Chats;
//
//import app.javachat.Utilities.Info;
//import app.javachat.Models.User;
//
//import java.io.Serializable;
//
//import static app.javachat.Utilities.Info.Call.getAvailableCallPort;
//
//public class ChatRequest implements Serializable {
//    private final User otherUser;
//    private int chatPort;
//    private final int chatListenerPort;
//    private final boolean accept;
//    private final int callListenerPort;
//
//    //This will be use to see where is my port in my local list.
//    private int indexOfChatPort;
//
//    public ChatRequest(User sender) {
//        this.otherUser = sender;
//        this.chatPort = getAvailableChatPort();
//        this.accept= false;
//        this.chatListenerPort= Info.NEW_CHAT_LISTENER_PORT;
//        this.callListenerPort = Info.Call.CALL_LISTENER_PORT;
//
//
//    }
//    public ChatRequest(User sender,boolean accept) {
//        this.otherUser = sender;
//        this.chatPort = getAvailableChatPort();
//        this.accept=accept;
//        this.chatListenerPort= Info.NEW_CHAT_LISTENER_PORT;
//        this.callListenerPort = Info.Call.CALL_LISTENER_PORT;
//    }
//
//    public int getCallListenerPort() {
//        return callListenerPort;
//    }
//
//    /**
//     * Searches for a port and returns it, saving his index on indexOfChatPort
//     * @return
//     */
//    private int getAvailableChatPort() {
//        //default port 100
//        int port = 55400;
//        for (int i = 55400; i < 55700; i++) {
//            if (Info.isPortFree(i)) {
//                port = i;
//                break;
//            }
//        }
//        this.indexOfChatPort =Info.usePort(port);
//        return port;
//    }
//
//
//
//
//    public User getOtherUser() {
//        return otherUser;
//    }
//
//    public int getChatPort() {
//        return chatPort;
//    }
//
//    public boolean isAccept() {
//        return accept;
//    }
//
//    public int getChatListenerPort() {
//        return chatListenerPort;
//    }
//
//    public int getIndexOfChatPort() {
//        return indexOfChatPort;
//    }
//
//    public void setIndexOfChatPort(int indexOfChatPort) {
//        this.indexOfChatPort = indexOfChatPort;
//    }
//
//    public void setChatPort(int chatPort) {
//        this.chatPort = chatPort;
//    }
//}
