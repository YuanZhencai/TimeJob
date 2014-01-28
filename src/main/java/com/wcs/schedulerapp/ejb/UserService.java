package com.wcs.schedulerapp.ejb;

import javax.ejb.Stateless;

@Stateless
public class UserService implements Say {

    @Override
    public void sayHello() {
        System.out.println("Hello EJB Rrmote SessionBean");
    }

}
