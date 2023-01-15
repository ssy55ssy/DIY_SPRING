package context;

import java.util.EventObject;

// every event will extend this class
public abstract class ApplicationEvent extends EventObject {

    public ApplicationEvent(Object source){
        super(source);
    }

}
