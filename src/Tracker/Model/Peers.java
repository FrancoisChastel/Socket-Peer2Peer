package Tracker.Model;

import Network.Registry;
import Peer.NetworkControler.ITrackerToPeerInteraction;
import Tracker.NetworkInteraction.IPeerToTrackerInteraction;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Francois on 22/12/15.
 */
public class Peers
{   HashMap<String,Peer> peers;

    public Peers() {
        this.peers = new HashMap<String, Peer>();
    }

    public HashMap<String, Peer> getPeers() {
        return peers;
    }

    public void setPeers(HashMap<String, Peer> peers) {
        this.peers = peers;
    }

    public HashMap<String,PeerLightSending> login(String identifiant, String password) throws IOException {
        System.out.println("["+(new Date()).toString()+"]" +identifiant+ " is attempting a connexion");
        if (!isMatching(identifiant, password))
        {   System.out.println("["+(new Date()).toString()+"]Connexion from "+identifiant+ " refused");
            return new HashMap<String, PeerLightSending>();
        }

        if (peers.get(identifiant).isConnected()) {
            System.out.println("[" + (new Date()).toString() + "]" + identifiant + " is already online");
            return new HashMap<String, PeerLightSending>();
        }


        HashMap<String,PeerLightSending> tmpPeers = new HashMap<String, PeerLightSending>();

        initiateStub(identifiant);

        int numberOfOnlinePeers = 0;

        Set cles = peers.keySet();
        for (Object cle : cles) {
            if (peers.get(cle).isConnected() && !cle.toString().equals(identifiant))
            {   numberOfOnlinePeers++;
                tmpPeers.put(cle.toString(), new PeerLightSending(peers.get(cle)));
                peers.get(cle).getStub().addPeers(identifiant, peers.get(identifiant).getUrl(), peers.get(identifiant).getPort());
            }
        }

        System.out.println("["+(new Date()).toString()+"]"+identifiant+ " just connect and "+numberOfOnlinePeers+" peers were just sent --");
        return  tmpPeers;
    }

    private Peer initiateStub(String identifiant)
    {   System.out.println("["+(new Date()).toString()+"]initialization stub of "+identifiant+" at "+peers.get(identifiant).getPort());
        peers.get(identifiant).setConnected(true);

        Registry r = new Network.Registry().connect(peers.get(identifiant).getUrl(), peers.get(identifiant).getPort());
        ITrackerToPeerInteraction stub = r.get(identifiant, ITrackerToPeerInteraction.class);
        peers.get(identifiant).setStub(stub);

        return peers.get(identifiant);
    }

    public void initiateServeur(String name, int port, IPeerToTrackerInteraction server)
    {   new Registry()
                .register(name, server)
                .publish(port);
    }

    /**
     * Set isConnected to false if id and password are coherent in our structure.
     * @param identifiant   : id of the peer that want to log out
     * @param password      : password associated to the id that want to log out
     * @return              : true if he was successfully logout;
     *                        false if he was not logout.
     */
    public boolean logout(String identifiant, String password)
    {
        if (!isMatching(identifiant,password))
        {   return false;
        }

        peers.get(identifiant).setConnected(false);
        peers.get(identifiant).setStub(null);

        Set cles = peers.keySet();
        for (Object cle : cles) {
            if (peers.get(cle).isConnected() && !cle.toString().equals(peers.get(identifiant)))
            {   peers.get(cle).getStub().deletePeers(identifiant);
            }
        }

        System.out.println("["+(new Date()).toString()+"]"+identifiant+ " just logout --");
        return true;
    }

    /**
     * Register a new peer if it is not already connected.
     * @param identifiant   : id associated to the peer ;
     * @param password      : password associated to the peer ;
     * @param url           : url (host) were you can reach the peer ;
     * @param port          : port were you can reach the peer.
     * @return              : true if register ;
     *                        false if not register.
     */
    public boolean signup(String identifiant, String password, String url, int port) {
        if (peers.containsKey(identifiant) && !isMatching(identifiant,password))
        {   System.out.println("["+(new Date()).toString()+"]"+identifiant+ " already signed --");
            return false;
        }

        if (peers.containsKey(identifiant))
        {   System.out.println("["+(new Date()).toString()+"]"+identifiant+ " url and port were updated --");
            peers.get(identifiant).setUrl(url);
            peers.get(identifiant).setPort(port);

            return true;
        }

        peers.put(identifiant,new Peer(password,url,port));

        System.out.println("["+(new Date()).toString()+"]"+identifiant+ " just signup --");

        return true;
    }

    /**
     * Check if password match to the id in our peers.
     * @param identifiant   : id of the peer ;
     * @param password      : password associated to the peer.
     * @return              : true if match ;
     *                        false if not match.
     */
    public boolean isMatching(String identifiant,String password)
    {   if (!peers.containsKey(identifiant))
        {   return false;
        }
        if (!peers.get(identifiant).isMatching(password))
        {   return false;
        }

        return true;
    }

}
