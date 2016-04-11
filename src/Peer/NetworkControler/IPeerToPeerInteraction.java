package Peer.NetworkControler;

import Peer.Model.ChatHistory;
import Peer.Model.TextualMessage;

/**
 * Created by Francois on 09/12/15.
 */
public interface IPeerToPeerInteraction
{

    /**
     * Add message on a peer, it can be an Object
     * @param message       : message you want to add to the peer concerned
     * @return              : true if message were successfully add;
     *                        false if message were not add.
     */
    public boolean addMessage(TextualMessage message);

    /**
     * Delete message on a peer
     * @param message       :
     * @return              : true if message were successfully deleted ;
     *                        false if message were not deleted.
     */
    public boolean deleteMessage(TextualMessage message);

    /**
     * Send a request to access to the chat history of the exchange.
     * @return              : messages peer have.
     */
    public ChatHistory recoverMessages(Integer entier);
}
