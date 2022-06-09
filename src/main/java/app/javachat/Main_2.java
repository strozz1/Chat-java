package app.javachat;

import app.javachat.Utilities.LocalDataManager;

public class Main_2 {
    public static void main(String[] args) {
        if(args.length>0 ) {
            String arg = args[0];
            System.out.println("File> "+arg);
            LocalDataManager.APP_FOLDER=arg;

        }
        MainApplication.main(args);
    }
}
