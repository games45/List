package com.example.List;

import com.example.List.stringNameUtil.StringName;

/**
 * Абстрактный класс в котором находятся переменные и методы необходимые всем его наследникам.
 * Класс имплементирует MyNewList, но не реализует его методы.
 * @param <T> тип элементов
 * @see MyNewList
 */
public abstract class MyNewAbstractList<T> implements MyNewList<T> {

    /**
     * Метод для проверки входит ли переданный индекс в диапазон текущего списка.
     *
     * @param index индекс, который нужно проверить
     * @param size  размер (диапазон) списка.
     * @throws IndexOutOfBoundsException если элемента с указанным индексом не существует
     */
    protected void rangeCheck(int index, int size) {
        if (size < index || 0 > index) {
            throw new IndexOutOfBoundsException(StringName.THE_INDEX_IS_OUT_OF_RANGE);
        }
    }
}
