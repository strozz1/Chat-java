package app.javachat.Calls;

import app.javachat.Models.User;

public class OutcomeSoundCall extends Thread{


    public OutcomeSoundCall(User otherUser, int otherPort) {

    }

    @Override
    public void run() {
        while(true){
            try {
                sleep(1000);
                System.out.println("sending sound!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopCall() {
    }
}
