package base.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 *
 */
public class XApplicationEventListener implements ApplicationListener {

    public static final Logger LOG = LoggerFactory.getLogger(XApplicationEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof ContextClosedEvent) {

        } else if (event instanceof ContextRefreshedEvent) {
        } else if (event instanceof ContextStartedEvent) {
        } else if (event instanceof ContextStoppedEvent) {
        } else {
        }

        String requestUrl = "";
        if (event instanceof ServletRequestHandledEvent) {
            requestUrl = ((ServletRequestHandledEvent) event).getRequestUrl();
        }

        //LOG.error("OOOOOOOOOOOO, what the fuck is going on. the " + event.getClass().getSimpleName() + " be fired......" + requestUrl);

        System.out.println("Oh, what the fuck is going on. the " + event.getClass().getSimpleName() + " be fired......" + requestUrl);
    }

}