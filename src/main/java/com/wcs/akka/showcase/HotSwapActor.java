package com.wcs.akka.showcase;

import akka.actor.UntypedActor;
import akka.japi.Procedure;

public class HotSwapActor extends UntypedActor {

    Procedure<Object> happy = new Procedure<Object>() {
        @Override
        public void apply(Object message) {
            if (message.equals("bar")) {
                getSender().tell("I am already happy :-)", getSelf());
            } else if (message.equals("foo")) {
                getContext().become(angry);
            }
        }
    };

    Procedure<Object> angry = new Procedure<Object>() {
        @Override
        public void apply(Object message) {
            if (message.equals("bar")) {
                getSender().tell("I am already angry?", getSelf());
            } else if (message.equals("foo")) {
                getContext().become(happy);
            }
        }
    };

    @Override
    public void onReceive(Object message) throws Exception {
        if (message.equals("bar")) {
            getContext().become(angry);
        } else if (message.equals("foo")) {
            getContext().become(happy);
        } else {
            unhandled(message);
        }
    }

}
