package com.example.List;

import com.example.List.stringNameUtil.StringName;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * Реализует интерфейс {@code MyNewList} с расширяющимся массивом. Массив расширяется, когда заканчиваются ячейки для вставки элементов.
 * Класс параметризирован.
 * Доступные методы для использования: добавления элемента в конце списка или по индексу,
 * удаления элемента по индексу или по элементу, удаление всех элементов, замена элемента по индексу, получение элемента по индексу,
 * получение количества добавленных элементов, получения нового списка у которого будет только часть элементов изначального списка
 * от указанного и до указанного индекса.
 * @param <T> тип элементов
 * @author Anton Nechunaev
 * @version 1.0
 * @see MyNewList
 * @see MyNewArrayList содержит воспомогательные методы необходимые всем наследникам
 */
public class MyNewArrayList<T> extends MyNewAbstractList<T> implements MyNewList<T> {

    /**
     * Счётчик имеющихся в списке элементов.
     */
    private int size = 0;
    /**
     * Размер внутреннего массива по умолчанию.
     */
    private static final int DEFAULT_SIZE = 10;
    /**
     * Внутренний массив, в котором хранятся все элементы. Он расширяется при добавлении элемента, когда массив уже заполнен полностью.
     */
    private Object[] array;

    /**
     * Конструктор без параметров создаёт внутренний массив размером 10.
     */
    public MyNewArrayList() {
        this.array = new Object[DEFAULT_SIZE];
    }

    /**
     * Конструктор создаёт список, который будет содержать элементы переданной коллекции.
     * @param collection коллекция, элементы которой нужно добавить в список
     * @throws NullPointerException если передать null в качестве параметра
     */
    public MyNewArrayList(Collection<T> collection) {
        int sizeCollection = collection.size();
        this.array = collection.toArray();
        size = sizeCollection;
    }

    /**
     * Конструктор создаёт пустой список нужного размера.
     * @param size размер списка
     * @throws IllegalArgumentException если передать меньше нуля
     */
    public MyNewArrayList(int size) {
        if (size >= 0) {
            array = new Object[size];
        } else {
            throw new IllegalArgumentException(StringName.THE_SIZE_CANNOT_BE_LESS_THAN_ZERO);
        }

    }

    @Override
    public boolean add(T element) {
        checkingAndEnlargingTheArrayIfItIsFull();
        array[size] = element;
        size++;
        return true;
    }

    @Override
    public boolean add(T element, int index) {
        rangeCheck(index, size);
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

    @Override
    public boolean remove(int index) {
        rangeCheck(index, size);
        size--;
        if (index == size) {
            array[size] = null;
        } else {
            System.arraycopy(array, index + 1, array, index, size - index);
        }
        return true;
    }

    @Override
    public boolean remove(T element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(array[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Удаляет все элементы из списка. Счётчик элементов обнуляется. Размер внутреннего массива остаётся, такой же какой был.
     */
    public void removeAll() {
        array = new Object[array.length];
        size = 0;
    }

    @Override
    public T get(int index) {
        return (T) array[index];
    }

    @Override
    public void set(int index, T element) {
        rangeCheck(index, size);
        array[index] = element;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public MyNewList subList(int fromIndex, int toIndex) {
        int sizeSubList = toIndex - fromIndex;
        MyNewList<Object> subList = new MyNewArrayList<>(sizeSubList);
        for (int i = 0; i < sizeSubList; i++) {
            subList.add(array[fromIndex + i]);
        }
        return subList;
    }

    /**
     * Метод для увеличения внутреннего массива, когда все ячейки заняты.
     * Используется перед добавлением новых элементов в методах add.
     */
    private void increasingTheArray() {
        int sizeArray = array.length;
        int newSizeArray = sizeArray + (int) (sizeArray * 0.5) + 1;
        array = Arrays.copyOf(array, newSizeArray);
    }

    /**
     * Метод проверяет все ли ячейки массива заполнены.
     * @return true если все ячейки заполнены.
     * @return false если последняя ячейка ещё не заполнена.
     */
    private boolean isArrayFull() {
        int lastElement = array.length - 1;
        if (array[lastElement] == null) {
            return false;
        }
        return true;
    }

    /**
     * Метод объединяющий проверки: заполнены ли все ячейки внутреннего массива и увеличивающий размер массива, если пустых ячеек не осталось.
     */
    private void checkingAndEnlargingTheArrayIfItIsFull() {
        if (isArrayFull()) {
            increasingTheArray();
        }
    }


    /**
     * Метод. Освобождает ячейку перед добавлением нового элемента в уже занятую ячейку.
     * Все элементы начиная с элемента по переданному индексу смещаются на 1 ячейку вправо.
     * @param index индекс ячейки, которую нужно освободить
     */
    private void freeingTheIndexCell(int index) {
        System.arraycopy(array, index, array, index + 1, size - index);
    }

    @Override
    public String toString() {
        Object[] newArray = Arrays.stream(array).filter(Objects::nonNull).toArray(Object[]::new);
        return "MyNewArrayList = " + Arrays.toString(newArray);

    }
}
