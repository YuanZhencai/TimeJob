package com.wcs.akka.showcase;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.RoundRobinRouter;

public class PrintlnActor extends UntypedActor {
    LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object msg) throws Exception {
        logger.info(String.format("Received message ’%s’ in actor %s", msg, getSelf().path().name()));
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef roundRobinRouter = system.actorOf(Props.create(PrintlnActor.class).withRouter(new RoundRobinRouter(5)),
                "router");
        for (int i = 1; i <= 10; i++) {
            roundRobinRouter.tell(i, roundRobinRouter);
        }
    }
}
