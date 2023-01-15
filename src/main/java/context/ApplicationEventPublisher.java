package context;

// every event will be published through this interface
public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent applicationEvent);

}
