package Tracker;

import Tracker.NetworkControler.TrackerNetworkControler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Server example called ServeurAuthentification on port 10000
 * @author Alan BAZARSI && François CHASTEL
 */
public class Server {
    static int SERVEUR_PORT = 10000;
    static String RMI_SERVER_NAME = "ServeurAuthentification";

    public static void main(String[] argv) throws UnknownHostException {
        System.out.println("["+(new Date()).toString()+"]"+"Launching server...."+(InetAddress.getLocalHost()).getCanonicalHostName());


        TrackerNetworkControler trackerNetworkControler = new TrackerNetworkControler();
        trackerNetworkControler.initiateServer(RMI_SERVER_NAME,SERVEUR_PORT);


        System.out.println("[" + (new Date()).toString() + "]" + "Server Ready");

    }
}
