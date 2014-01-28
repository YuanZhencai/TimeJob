package com.wcs.akka.showcase;

import java.io.Serializable;

import akka.actor.UntypedActor;

public class FibonacciActor extends UntypedActor {

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof FibonacciNumber) {
            FibonacciNumber fibonacciNumber = (FibonacciNumber) msg;
            getSender().tell(fibonacci(fibonacciNumber.getNbr()), getSelf());
        } else {
            unhandled(msg);
        }
    }

    private int fibonacci(int n) {
        return fib(n, 1, 0);
    }

    private int fib(int n, int b, int a) {
        if (n == 0) return a;
        // recursion
        return fib(n - 1, a + b, b);
    }

    public static class FibonacciNumber implements Serializable {
        private static final long serialVersionUID = 1L;
        private final int nbr;

        public FibonacciNumber(int nbr) {
            this.nbr = nbr;
        }

        public int getNbr() {
            return nbr;
        }
    }
}
