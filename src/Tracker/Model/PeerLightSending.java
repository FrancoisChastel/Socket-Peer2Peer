package Tracker.Model;

import java.io.Serializable;

/**
 * LightWeight peers datas in order to be send faster though network
 */
public class PeerLightSending implements Serializable {
    String url;
    int port;

    public PeerLightSending(String url, int port) {
        this.url = url;
        this.port = port;
    }

    public PeerLightSending(Peer peer) {
        this.url = peer.getUrl();
        this.port = peer.getPort();
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
