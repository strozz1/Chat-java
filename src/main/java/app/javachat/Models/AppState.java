//package app.javachat.Models;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//public class AppState implements Serializable {
//    private User user;
//    private List<Integer> occupatedPorts;
//    private List<ChaDataContainer> chaDataContainerList;
//
//    public List<ChaDataContainer> getChatInfoList() {
//        return chaDataContainerList;
//    }
//
//    public void setChatInfoList(List<ChaDataContainer> chaDataContainerList) {
//        this.chaDataContainerList = chaDataContainerList;
//    }
//
//    public AppState() {
//        this.user = new User("Default");
//        this.occupatedPorts = new ArrayList<>();
//        this.chaDataContainerList = new ArrayList<>();
//    }
//
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public List<Integer> getOccupatedPorts() {
//        return occupatedPorts;
//    }
//
//    public void setOccupatedPorts(List<Integer> occupatedPorts) {
//        this.occupatedPorts = occupatedPorts;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        AppState appState = (AppState) o;
//        return Objects.equals(user, appState.user);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(user);
//    }
//}
