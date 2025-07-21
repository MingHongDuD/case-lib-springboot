package com.damon.error;

import java.rmi.UnexpectedException;

public class RemoteExceptionWrapper extends UnexpectedException {
    public RemoteExceptionWrapper(String s) {
        super(s);
    }
}
