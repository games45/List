package com.example.List;

public interface ListAnton<T> {
    boolean add(T t);

    boolean add(T t, int index) ;


    boolean remove(T t);

    Object get(int index);

    void set(int index, Object o);

    int size();

    ListAnton<T> subList(int fromIndex, int toIndex);
}
