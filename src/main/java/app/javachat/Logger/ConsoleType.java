package app.javachat.Logger;

public class ConsoleType implements LoggerType{
    @Override
    public void showMessage(String msg) {
        System.out.println("|LOG| > " + msg);
    }

    @Override
    public void showError(String msg) {
        System.err.println("|ERROR| > " + msg);
    }

    @Override
    public void showMessage(String msg, String sender) {
        System.out.println("|"+sender +" LOG| > " + msg);
    }

    @Override
    public void showError(String msg, String sender) {

        System.err.println("|"+sender + " ERROR| > " + msg);
    }
}
