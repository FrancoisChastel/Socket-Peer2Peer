package Network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * @author Francois Chastel
 */
public class Registry {
    private Map<String, Object> services = new HashMap<String, Object>();
    private Socket server;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private ServerSocket ss;

    /**
     * Register a new object in the registry identify by his name
     * @param name
     * @param service
     * @return
     */
    public Registry register(String name, Object service) {
        services.put(name, service);
        return this;
    }

    /**
     * Publish a registry on a specific port which, every client will have his own thread in order to listen
     * network interactions
     * @param port
     */
    public void publish(final int port) {
        if (server != null) {
            System.out.print("Socket is already connected to a server");
        }

        try {
            new Thread() {
                public void run() {
                    try {
                    ss = new ServerSocket(port);
                    while (true) {
                        final Socket s = ss.accept();
                        new Thread() {
                            public void run() {
                                try {
                                    //System.err.println("[" + new Date() + "]" + "Internal network : Beginning of transmission");
                                    ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                                    ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

                                    while (s.isConnected()) {
                                        //System.err.println("[" + new Date() + "]" + "Internal network : Request recieve at " + s.getPort());
                                        InvocationContext ic = (InvocationContext) ois.readObject();
                                        Object targetObject = services.get(ic.getName());
                                        Object result = targetObject.getClass().getMethod(ic.getMethod(), args2Class(ic.getArgs())).invoke(targetObject, ic.getArgs());
                                        oos.writeObject(result);
                                        //System.err.println("[" + new Date() + "]" + "Internal network : sent");
                                    }
                                    oos.close();
                                    ois.close();
                                } catch (Exception e) {
                                    System.err.println("[" + new Date() + "]" + "Internal network : End of transmission "+e);
                                }

                            }
                        }.start();
                    }
                    } catch (Exception e) {
                        throw new NetworkInvocationException(e.getMessage(), e);
                    }
                }
            }.start();
        } catch (Exception e) {
            throw new NetworkInvocationException(e.getMessage(), e);
        }
    }

    /**
     * Connect you on a specific host and port get you the registry associated
     * @param host
     * @param port
     * @return registry on this specific host and port
     */
    public Registry connect(String host, int port) {
        if (server != null) {
            System.out.print("Socket is already connected");
        }

        if (ss != null) {
            System.out.print("Registry is listening");
        }

        try {
            server = new Socket(host, port);
            ois = new ObjectInputStream(server.getInputStream());
            oos = new ObjectOutputStream(server.getOutputStream());

        } catch (Exception e) {
            throw new NetworkInvocationException(e.getMessage(), e);
        }

        return this;
    }

    /**
     * Get object present on the registry
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(String name, Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{clazz},
                new NetworkHandler(name, ois, oos)
        );
    }

    /**
     * Convert args in class of objects
     * @param objs
     * @return
     */
    private Class[] args2Class (Object[] objs) {
        List<Class> classes = new ArrayList<Class>();
        for (Object o : objs) {
            classes.add(o.getClass());
        }
        return classes.toArray(new Class[]{});
    }
}