package context.event;

import context.ApplicationContext;
import context.ApplicationEvent;

public class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(Object source){
        super(source);
    }

    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }

}
