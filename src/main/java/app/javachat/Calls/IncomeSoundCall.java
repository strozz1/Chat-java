package app.javachat.Calls;

public class IncomeSoundCall extends Thread {

    public IncomeSoundCall(int localPort) {

    }

    @Override
    public void run() {
        while(true){
            try {
                sleep(1000);
                System.out.println("receiving sound!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopCall() {
    }
}
