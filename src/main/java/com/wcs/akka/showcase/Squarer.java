package com.wcs.akka.showcase;


import scala.concurrent.Future;
import akka.japi.Option;

public interface Squarer {
    void squareDontCare(int i); // fire-forget

    Future<Integer> square(int i); // non-blocking send-request-reply

    Option<Integer> squareNowPlease(int i);// blocking send-request-reply

    int squareNow(int i); //
}
