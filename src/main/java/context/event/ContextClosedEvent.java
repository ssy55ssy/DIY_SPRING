package context.event;

import context.ApplicationEvent;

public class ContextClosedEvent extends ApplicationContextEvent {

    public ContextClosedEvent(Object source){
        super(source);
    }

}
