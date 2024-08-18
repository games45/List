package com.example.List;

import java.util.Collection;

/**
 * Реализует интерфейс {@code MyNewList}. Класс параметризирован.
 * Двусторонний связанный список. В экземпляре этого класса будут храниться ссылки только на первый и последний элемент коллекции.
 * Все хранящиеся элементы оборачиваются во вложенный класс Node.
 * Каждый из элементов Node хранит ссылки на следующий и предыдущий. Когда следующего или предыдущего элемента нет, ссылка будет null.
 * Доступные методы для использования: добавления элемента в конце списка или по индексу,
 * удаления элемента по индексу или по элементу, удаление всех элементов, замена элемента по индексу, получение элемента по индексу,
 * получение количества добавленных элементов, получения нового списка у которого будет только часть элементов изначального списка
 * от указанного и до указанного индекса.
 *
 * @param <T> тип элементов
 * @author Anton Nechunaev
 * @version 1.0
 * @see MyNewList
 * @see MyNewArrayList использует воспомогательные методы необходимые всем наследникам
 */
public class MyNewLinkedList<T> extends MyNewAbstractList<T> implements MyNewList<T> {
    /**
     * Счётчик имеющихся в списке элементов.
     */
    private int size = 0;

    /**
     * Ссылка на первый элемент. Если в коллекции нет элементов будет равен null.
     */
    private Node<T> first;

    /**
     * Ссылка на последний элемент. Если в коллекции нет элементов будет равен null.
     */
    private Node<T> last;

    /**
     * Коллекция будет создана пустой.
     */
    public MyNewLinkedList() {
    }

    /**
     * Коллекция будет хранить в себе элементы переданной коллекции.
     *
     * @param collection коллекция, элементы которой должна содержать данная коллекция.
     */
    public MyNewLinkedList(Collection<T> collection) {
        addALL(collection);
    }

    @Override
    public boolean add(T element) {
        lastAdd(element);
        return true;
    }

    @Override
    public boolean add(T element, int index) {
        rangeCheck(index, size);
        if (index == 0) {
            firstAdd(element);
            return true;
        }
        if (index == size) {
            lastAdd(element);
            return true;
        }
        Node<T> oldNode = getNode(index);
        Node<T> newNode = new Node<>(oldNode.prev, element, oldNode);
        oldNode.prev = newNode;
        newNode.prev.next = newNode;

        size++;
        return true;
    }

    /**
     * Добавляет все элементы переданные в параметре в данную коллекцию.
     * @param collection коллекция, содержащая элементы, которые будут добавлены
     */
    public void addALL(Collection<T> collection) {
        Object[] items = collection.toArray();
        for (int i = 0; i < collection.size(); i++) {
            lastAdd((T) items[i]);
        }
    }
//NullPointerException если удалять 0 элемент из пустой коллекции

    @Override
    public boolean remove(int index) {
        rangeCheck(index, size);
        if (index == 0) {
            removeFirst();
            return true;
        }
        if (index == size - 1) {
            removeLast();
            return true;
        }
        final Node<T> prevRemoveNode = getNode(index).prev;
        final Node<T> nextRemoveNode = getNode(index).next;
        prevRemoveNode.next = nextRemoveNode;
        nextRemoveNode.prev = prevRemoveNode;
        size--;
        return true;
    }

    /**
     * Удаляет первый элемент. Заменяет ссылку на первый элемент следующим или null, если следующего нет.
     */
    private void removeFirst() {
        first = first.next;
        if (first == last) {
            last = null;
        } else {
            first.prev = null;
        }
        size--;
    }

    /**
         * Удаляет последний элемент. Заменяет ссылку на последний элемент предыдущим или null, если предыдущего нет.
     */
    private void removeLast() {
        last = last.prev;
        if (first == last) {
            first = null;
        } else {
            last.next = null;
        }
        size--;
    }

    @Override
    public boolean remove(T element) {
        for (int i = 0; i < size; i++) {
            T item = getNode(i).item;
            if (item.equals(element)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    /**
     * Возвращает Node по переданному индексу. Сам метод решает с начала или с конца будет быстрее добраться до переданного индека.
     * А после вызывает соответствующий вспомогательный метод.
     * @param index индекс
     * @return элемент вложенного класса Node
     * @throws IndexOutOfBoundsException если элемента с указанным индексом не существует
     */
    private Node<T> getNode(int index) {
        if (index <= size / 2) {
            return getNodeByIncreasing(index);
        }
        return getNodeByReducing(index);
    }

    /**
     * Возвращает Node по переданному индексу начиная поиск с начала.
     * @param index индекс
     * @return элемент вложенного класса Node
     * @throws IndexOutOfBoundsException если элемента с указанным индексом не существует
     */
    private Node<T> getNodeByIncreasing(int index) {
        rangeCheck(index, size);
        Node<T> oldNode = first;
        for (int i = 0; i <= index; i++) {
            if (oldNode.next != null && index != i) {
                oldNode = oldNode.next;
            }
        }
        return oldNode;
    }

    /**
     * Возвращает Node по переданному индексу начиная поиск с конца.
     * @param index индекс
     * @return элемент вложенного класса Node
     * @throws IndexOutOfBoundsException если элемента с указанным индексом не существует
     */
    private Node<T> getNodeByReducing(int index) {
        rangeCheck(index, size);
        int newIndex = size - index - 1;
        Node<T> oldNode = last;
        for (int i = 0; i <= newIndex; i++) {
            if (oldNode.prev != null && newIndex != i) {
                oldNode = oldNode.prev;
            }
        }
        return oldNode;
    }

    @Override
    public void set(int index, T element) {
        getNode(index).item = element;
    }

    @Override
    public int size() {
        return size;
    }

    //выбрасывает IndexOutOfBoundsException: Индекс выходит за рамки допустимого диапазона
    @Override
    public MyNewList subList(int fromIndex, int lastIndex) {
        rangeCheck(lastIndex, size);
        Node<T> fromNode = getNode(fromIndex);
        int sizeSubList = lastIndex - fromIndex;
        MyNewList<T> newList = new MyNewLinkedList<>();
        for (int i = 0; i < sizeSubList; i++) {
            newList.add(fromNode.item);
            fromNode = fromNode.next;
        }

        return newList;
    }

    /**
     * Добавляет переданный элемент в конец списка.
     * @param element элемент, который нужно добавить
     */
    private void lastAdd(T element) {
        Node<T> oldNode = last;
        Node<T> newNode = new Node<>(oldNode, element, null);
        last = newNode;
        if (oldNode == null) {
            first = newNode;
        } else {
            oldNode.next = newNode;
        }
        size++;
    }

    /**
     * Добавляет переданный элемент в начало списка.
     * @param element элемент, который нужно добавить
     */
    private void firstAdd(T element) {
        Node<T> oldFirstNode = first;
        Node<T> newFirstNode = new Node<>(null, element, oldFirstNode);
        first = newFirstNode;
        if (oldFirstNode == null) {
            last = newFirstNode;
        } else {
            oldFirstNode.prev = newFirstNode;
        }
        size++;
    }

    /**
     *  Оборачивает переданный элементы. Имеет ссылку на предыдущий и следующий элемент обёрнутый этим же классом.
     *  Элементы добавленные в коллекцию MyNewLinkedList оборачиваются данным классом.
     * @param <T> типа параметра коллекции.
     */
    private static class Node<T> {
        /**
         * Сам элемент, который хранится в коллекции.
         */
        T item;

        /**
         * Ссылка на следующий элемент коллекции. Если следующего нет равен null.
         */
        Node<T> next;

        /**
         * Ссылка на предыдущий элемент коллекции. Если предыдущего нет равен null.
         */
        Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
