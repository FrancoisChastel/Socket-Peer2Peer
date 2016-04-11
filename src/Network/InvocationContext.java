package Network;

import java.io.Serializable;

/**
 * @author François Chastel
 */
public class InvocationContext implements Serializable {
    private Object[] args;
    private String method;
    private String name;

    /**
     * Default constructor : Construct a context of invocation
     */
    public InvocationContext() {
    }

    /**
     * Constructor : Construct a context of invocation with all the parameters of invocation
     * @param name
     * @param args
     * @param method
     */
    public InvocationContext(String name, Object[] args, String method) {
        this.name = name;
        this.args = args;
        this.method = method;
    }
    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}