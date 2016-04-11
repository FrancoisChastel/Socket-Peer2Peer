package Tracker.NetworkInteraction;

import Tracker.Model.PeerLightSending;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Francois on 22/12/15.
 */
public interface IPeerToTrackerInteraction
{
    /**
     * login of a peer will return all the peers register in the tracker.
     * @param identifiant   : id of login ;
     * @param password      : password associated to the login.
     * @return              : return all the peers with their id associated.
     */
    public HashMap<String,PeerLightSending> login(String identifiant, String password) throws IOException;

    /**
     * Logout of a peer.
     * @param identifiant   : id of login ;
     * @param password      : password associated to the login.
     * @return              : true if it was successfully logout ;
     *                        false if it was not logout because there is no peers with this id, or with this password
     *                        , if it is already logout.
     */
    public boolean logout(String identifiant, String password);

    /**
     * Register a new peer in the tracker.
     * @param identifiant   : id of login ;
     * @param password      : password associated to the login ;
     * @param url           : url (host) were peer can be reach ;
     * @param port          : port were peer can be reach.
     * @return              : true if it was successfully register ;
     *                        false if it was not register because already exist.
     */
    public boolean signup(String identifiant, String password, String url, Integer port);
}
