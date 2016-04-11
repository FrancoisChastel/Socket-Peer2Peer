package Peer.NetworkControler;

import java.io.IOException;

/**
 * Created by Francois on 09/12/15.
 */
public interface ITrackerToPeerInteraction
{   public boolean addPeers(String identifiant, String url, Integer port) throws IOException;

    public boolean deletePeers(String identifiant);
}
