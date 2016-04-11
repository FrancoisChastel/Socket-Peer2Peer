package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author François Chastel
 */
public class NetworkHandler implements InvocationHandler,Serializable {
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private String name;

    /**
     * Construction : Build a network handler with the stream concerned and his associate name
     * @param name
     * @param ois
     * @param oos
     */
    public NetworkHandler(String name, ObjectInputStream ois, ObjectOutputStream oos) {
        this.name = name;
        this.ois = ois;
        this.oos = oos;
    }

    /**
     * Invocation of a distance object with all the context and args concerned
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws IOException, ClassNotFoundException {
        oos.writeObject(new InvocationContext(name, args, method.getName()));
        return ois.readObject();
    }
}
