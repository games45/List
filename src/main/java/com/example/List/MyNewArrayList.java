package com.example.List;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

// добавить метод проверки что переданный индекс больше 0 и меньше размера листа. добавить метод увеличения листа, когда переходит нужный размер
public class MyNewArrayMyNewList<T> implements MyNewList<T>, Serializable {
    private static final String THE_SIZE_CANNOT_BE_LESS_THAN_ZERO = "Размер не может быть меньше нуля";
    private static final String THE_INDEX_IS_OUT_OF_RANGE = "Индекс выходит за рамки допустимого диапазона";
    private int size = 0;
    //добавить параметр ай ди версии
    private static final int DEFAULT_SIZE = 10;
    private Object[] array;

    public MyNewArrayMyNewList() {
        this.array = new Object[DEFAULT_SIZE];
    }

    public MyNewArrayMyNewList(Collection<T> collection) {
        int sizeCollection = collection.size();
        this.array = collection.toArray();
        size = sizeCollection;
    }

    public MyNewArrayMyNewList(int size) {
        if (size >= 0) {
            array = new Object[size];
        } else {
            throw new IllegalArgumentException(THE_SIZE_CANNOT_BE_LESS_THAN_ZERO);
        }

    }

    @Override
    public boolean add(Object o) {
        checkingAndEnlargingTheArrayIfItIsFull();
        array[size] = o;
        size++;
        return true;
    }


    @Override
    public boolean add(Object element, int index) {
        rangeCheck(index);
        checkingAndEnlargingTheArrayIfItIsFull();
        if (array[index] == null) {
            array[index] = element;
            size++;
            return true;
        }
        if (array[index] != null) {
            freeingTheIndexCell(index);
            array[index] = element;
            size++;
            return true;
        }
        return false;
    }

    // остальные элементы должны сместится
    @Override
    public boolean remove(int index) {
        rangeCheck(index);
        int range = size - index;
        for (int i = 0; i < range; i++) {
            array[index + i] = array[index + i + 1];
        }
        size--;
        return true;
    }

    public boolean remove(Object element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(array[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public Object get(int index) {
        return array[index];
    }

    // сет должен выбрасывать исключение ArrayOutOfBoundIndex если нечего менять
    @Override
    public void set(int index, Object o) {
        rangeCheck(index);

        array[index] = o;
    }

    @Override
    public int size() {
        int countElement = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                countElement++;
            }
        }
        return countElement;
    }

    @Override
    public MyNewList subList(int fromIndex, int toIndex) {
        int sizeSubList = toIndex - fromIndex;
        MyNewList<Object> subList = new MyNewArrayMyNewList<>(sizeSubList);
        for (int i = 0; i < sizeSubList; i++) {
            subList.add(array[fromIndex + i]);
        }
        return subList;
    }

    private void rangeCheck(int index) {
        if (array.length < index || 0 > index) {
            throw new IllegalArgumentException(THE_INDEX_IS_OUT_OF_RANGE);
        }
    }

    //увеличение массива
    private void increasingTheArray() {
        int sizeArray = array.length;
        int newSizeArray = sizeArray + (int) (sizeArray * 0.5) + 1;
//        Object[] newArray = new Object[newSizeArray];
//        for (int i = 0; i < sizeArray; i++) {
//            newArray[i] = array[i];
//        }
//        array = newArray;
        array = Arrays.copyOf(array, newSizeArray);
//        System.out.println("Отработал метод increasingTheArray");
    }

    //проверка полный ли массив
    private boolean isArrayFull() {
        int lastElement = array.length - 1;
        if (array[lastElement] == null) {
//            System.out.println("Отработал метод isArrayFull результат false");
            return false;
        }
//        System.out.println("Отработал метод isArrayFull результат true");
        return true;
    }

    private void checkingAndEnlargingTheArrayIfItIsFull() {
        if (isArrayFull()) {
            increasingTheArray();
        }
    }


    // метод отчищает индекс для вставки
    private void freeingTheIndexCell(int index) {
        Object[] subArray = new Object[array.length];
        for (int i = 0; i < index; i++) {
            subArray[i] = array[i];
        }
        for (int i = index + 1; i < array.length; i++) {
            subArray[i] = array[i - 1];
        }
        array = subArray;
    }
}
