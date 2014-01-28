package com.wcs.akka.tutorial;

import java.math.BigInteger;
import java.util.ArrayList;

import scala.collection.mutable.ArraySeq;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.RoundRobinRouter;

public class Master extends UntypedActor {
    LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    private long messages = 100;
    private ActorRef workerRouter;
    private final Time time = new Time();
    private ArrayList<BigInteger> list = new ArrayList<BigInteger>();

    public Master() {
        workerRouter = this.getContext().actorOf(Worker.createWorker().withRouter(new RoundRobinRouter(4)), "workerRouter");
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof Calculate) {
            time.start();
            processMessages();
        } else if (message instanceof Result) {
            BigInteger factorial = ((Result) message).getFactorial();
            logger.info("[factorial]" + factorial);
            list.add(factorial);
            if (list.size() == messages) end();
        } else {
            unhandled(message);
        }
    }

    private void processMessages() {
        for (int i = 0; i < messages; i++) {
            workerRouter.tell(new Work(), getSelf());
        }
    }

    private void end() {
        time.end();
        logger.info("Done: " + time.elapsedTimeMilliseconds());
        getContext().system().shutdown();
    }

    public static Props createMaster() {
        return Props.create(Master.class, new ArraySeq<Object>(0));
    }
}