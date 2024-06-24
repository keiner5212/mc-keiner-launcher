package minecraft.client.impl.common;

import java.util.HashSet;

import minecraft.client.api.common.IObservable;
import minecraft.client.api.common.IObserver;

public class Observable<E> implements IObservable<E> {
    private final HashSet<IObserver<E>> observers = new HashSet<IObserver<E>>();

    @Override
    public void addObserver(IObserver<E> obs) {
        observers.add(obs);
    }

    @Override
    public void deleteObserver(IObserver<E> obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers(E changedObj) {
        for (IObserver<E> obs : observers) {
            obs.onUpdate(this, changedObj);
        }
    }

}
