package com.wcs.schedulerapp.ejb;

import javax.ejb.Remote;

@Remote
public interface Say {
    public void sayHello();
}
