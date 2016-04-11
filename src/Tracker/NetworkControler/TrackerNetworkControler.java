package Tracker.NetworkControler;

import Tracker.Model.PeerLightSending;
import Tracker.Model.Peers;
import Tracker.NetworkInteraction.IPeerToTrackerInteraction;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Francois on 23/12/15.
 */
public class TrackerNetworkControler implements IPeerToTrackerInteraction
{   Peers peers;

    public TrackerNetworkControler()
    {   this.peers = new Peers();
    }

    public Peers getPeers() {
        return peers;
    }

    public void setPeers(Peers peers) {
        this.peers = peers;
    }

    public void initiateServer(String name, int port)
    {   this.peers.initiateServeur(name, port, this);
    }

    @Override
    public HashMap<String,PeerLightSending> login(String identifiant, String password) throws IOException {
        return peers.login(identifiant,password);
    }

    @Override
    public boolean logout(String identifiant, String password) {
        return peers.logout(identifiant,password);
    }

    @Override
    public boolean signup(String identifiant, String password, String url, Integer port) {
        return peers.signup(identifiant,password,url,port);
    }
}
