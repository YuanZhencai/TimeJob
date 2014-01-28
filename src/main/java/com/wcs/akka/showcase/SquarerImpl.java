package com.wcs.akka.showcase;

import scala.concurrent.Future;
import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import akka.dispatch.Futures;
import akka.japi.Creator;
import akka.japi.Option;

public class SquarerImpl implements Squarer {
    
    
    private String name;

    public SquarerImpl() {
        this.name = "default";
    }
    
    public SquarerImpl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void squareDontCare(int i) {
        int sq = i * i; // Nobody cares :(
    }

    @Override
    public Future<Integer> square(int i) {
        return Futures.successful(i * i);
    }

    @Override
    public Option<Integer> squareNowPlease(int i) {
        return Option.some(i * i);
    }

    @Override
    public int squareNow(int i) {
        return i * i;
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("MySystem");
        Squarer mySquarer = TypedActor.get(system).typedActorOf(new TypedProps<SquarerImpl>(Squarer.class, SquarerImpl.class));
        int squareNow = mySquarer.squareNow(2);
        System.out.println("[mySquarer]" + mySquarer);
        System.out.println("[squareNow]" + squareNow);
        Squarer otherSquarer = TypedActor.get(system).typedActorOf(
                new TypedProps<SquarerImpl>(Squarer.class, new Creator<SquarerImpl>() {
                    public SquarerImpl create() {
                        return new SquarerImpl("foo");
                    }
                }), "name");
        int square = otherSquarer.squareNow(3);
        System.out.println("[otherSquarer]" + otherSquarer);
        System.out.println("[square]" + square);
    }
}
