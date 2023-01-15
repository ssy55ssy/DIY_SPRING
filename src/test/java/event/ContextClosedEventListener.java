package event;

import context.ApplicationListener;
import context.event.ContextClosedEvent;
import context.event.ContextRefreshedEvent;

public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    public void onApplicationEvent(ContextClosedEvent event){
        System.out.println("close event：" + this.getClass().getName());
    }

}
