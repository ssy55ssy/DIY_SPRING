package event;

import context.ApplicationListener;

import java.util.Date;

public class CustomEventListener implements ApplicationListener<CustomEvent> {

    public void onApplicationEvent(CustomEvent event){
        System.out.println("receive：" + event.getSource() + " info;time：" + new Date());
        System.out.println("info：" + event.getId() + ":" + event.getMessage());
    }

}
