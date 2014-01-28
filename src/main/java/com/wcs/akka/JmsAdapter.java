package com.wcs.akka;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class JmsAdapter extends UntypedActor {
    LoggingAdapter logger = Logging.getLogger(getContext().system(), this);
    
    @Override
    public void preStart() {
        // create the greeter actor
        final ActorRef greeter = getContext().actorOf(Props.create(Greeter.class), "greeter");
        // tell it to perform the greeting
        greeter.tell(Greeter.Msg.GREET, getSelf());
    }

    @Override
    public void onReceive(Object msg) {
        if (msg == Greeter.Msg.DONE) {
            // when the greeter is done, stop this actor and with it the application
            getContext().stop(getSelf());
        } else unhandled(msg);
    }

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("MySystem");
        final ActorRef myActor = system.actorOf(Props.create(JmsAdapter.class), "JmsAdapter");
        myActor.tell(Greeter.Msg.GREET, myActor);
    }
}
