package Peer;

import Peer.NetworkControler.NetworkControler;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

/**
 * Example of ChatServiceBackup on localHost
 * @author Alan BAZARSI && François CHASTEL
 */
public class PeerInstance {
    public static void main(String [ ] args) throws IOException, ClassNotFoundException, NotBoundException, AlreadyBoundException, InterruptedException {
        int serveurPort = 10000;
        int portAdding = 1;
        int clientPort = serveurPort+portAdding;
        String serverIP = InetAddress.getLocalHost().getCanonicalHostName();
        String serveurName = "localhost";

        NetworkControler networkControler = new NetworkControler("ChatServicesBackup", "sudoAdmin");
        networkControler.startPeer(clientPort);
        networkControler.login(clientPort, serveurName, serverIP, serveurPort);
    }
}