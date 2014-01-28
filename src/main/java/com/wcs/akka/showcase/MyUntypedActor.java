package com.wcs.akka.showcase;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MyUntypedActor extends UntypedActor {
    LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            logger.info("Received String message: {}", message);
            getSender().tell(message, getSelf());
        } else unhandled(message);
    }
}
