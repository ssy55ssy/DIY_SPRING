package event;

import context.ApplicationListener;
import context.event.ContextRefreshedEvent;

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    public void onApplicationEvent(ContextRefreshedEvent event){
        System.out.println("refresh event：" + this.getClass().getName());
    }

}
