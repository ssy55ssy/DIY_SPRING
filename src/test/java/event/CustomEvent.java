package event;

import context.ApplicationEvent;

public class CustomEvent extends ApplicationEvent {

    private Long id;
    private String message;

    public CustomEvent(Object source, Long id, String message) {
        super(source);
        this.id = id;
        this.message = message;
    }

    public Long getId(){return this.id;}

    public String getMessage(){return this.message;}

}
