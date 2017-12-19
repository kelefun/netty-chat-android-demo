package com.funstill.netty.chat.observer;

import java.util.Vector;

/**
 * @author liukaiyang
 * @date 2017/12/6 10:15
 */
public class BaseObservable {
    private boolean changed = false;

    private Vector<ProtoMsgObserver> obs;

    public Vector<ProtoMsgObserver> getObservers() {
        return obs;
    }


    public BaseObservable() {
        obs = new Vector<>();
    }

    public synchronized void addObserver(ProtoMsgObserver o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.addElement(o);
        }
    }

    public synchronized void deleteObserver(ProtoMsgObserver o) {
        obs.removeElement(o);
    }

    public synchronized void deleteObservers() {
        obs.removeAllElements();
    }

    protected synchronized void setChanged() {
        changed = true;
    }

    protected synchronized void clearChanged() {
        changed = false;
    }

    public synchronized boolean hasChanged() {
        return changed;
    }
    public synchronized int countObservers() {
        return obs.size();
    }

}
