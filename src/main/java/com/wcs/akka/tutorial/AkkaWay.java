package com.wcs.akka.tutorial;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class AkkaWay {

    /**
     * <p>
     * Description:
     * </p>
     * 
     * @param args
     */

    public static void main(String[] args) {
        new AkkaWay().run();
    }

    private void run() {
        ActorSystem system = ActorSystem.create("CalcSystem");
        ActorRef master = system.actorOf(Master.createMaster(), "master");
        master.tell(new Calculate(), ActorRef.noSender());
    }
}
