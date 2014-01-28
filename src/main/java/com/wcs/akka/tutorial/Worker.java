package com.wcs.akka.tutorial;

import java.math.BigInteger;

import scala.collection.mutable.ArraySeq;

import akka.actor.Props;
import akka.actor.UntypedActor;

public class Worker extends UntypedActor {

    @Override
    public void onReceive(Object message) {
        if (message instanceof Work) {
            BigInteger bigInt = new CalculateFactorial().calculate();
            getSender().tell(new Result(bigInt), getSelf());
        } else unhandled(message);
    }

    public static Props createWorker() {
        return Props.create(Worker.class, new ArraySeq<Object>(0));
    }
}
