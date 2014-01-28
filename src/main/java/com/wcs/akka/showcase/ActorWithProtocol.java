package com.wcs.akka.showcase;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActorWithStash;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public class ActorWithProtocol extends UntypedActorWithStash {
    LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg.equals("open")) {
            logger.info("" + msg);
            unstashAll();
            getContext().become(new Procedure<Object>() {
                public void apply(Object msg) throws Exception {
                    if (msg.equals("write")) {
                        // do writing...
                        logger.info("" + msg);
                    } else if (msg.equals("close")) {
                        logger.info("" + msg);
                        unstashAll();
                        getContext().unbecome();
                    } else {
                        stash();
                    }
                }
            }, false); // add behavior on top instead of replacing
        } else {
            stash();
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef actorWithProtocol = system.actorOf(Props.create(ActorWithProtocol.class));
        actorWithProtocol.tell("open", actorWithProtocol);
        actorWithProtocol.tell("write", actorWithProtocol);
        actorWithProtocol.tell("close", actorWithProtocol);
        actorWithProtocol.tell("open", actorWithProtocol);
        actorWithProtocol.tell("write", actorWithProtocol);
    }
}
