package com.wcs.akka;

import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Greeter extends UntypedActor {

    LoggingAdapter logger = Logging.getLogger(getContext().system(), this);
    
    public static enum Msg {
        GREET, DONE;
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg == Msg.GREET) {
            logger.info("Hello World!");
            getSender().tell(Msg.DONE, getSelf());
        } else unhandled(msg);

    }

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("MySystem");
        final ActorRef greeterActor = system.actorOf(Props.create(Greeter.class), "Greeter");
        greeterActor.tell(Greeter.Msg.GREET, greeterActor);
        greeterActor.tell(Greeter.Msg.GREET, greeterActor);
        greeterActor.tell(Greeter.Msg.GREET, greeterActor);
    }
}
