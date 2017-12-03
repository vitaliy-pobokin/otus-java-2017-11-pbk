package org.examples.pbk.otus.l31homework;

import java.util.*;

public class MyArrayList<E> implements List<E> {
    private Object[] innerArray;
    private int size;

    private static final int INITIAL_INNER_ARRAY_SIZE = 10;
    private static final int GROWTH_RATE = 2;

    public MyArrayList() {
        this(INITIAL_INNER_ARRAY_SIZE);
    }

    public MyArrayList(int initialSize) {
        this.innerArray = new Object[initialSize];
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator<E>();
    }

    private class MyIterator<E> implements Iterator {
        private int cursor;

        private MyIterator() {
            this.cursor = -1;
        }

        @Override
        public boolean hasNext() {
            return cursor < size - 1;
        }

        @Override
        public Object next() {
            if (!hasNext()) throw new NoSuchElementException();
            cursor++;
            return (E) innerArray[cursor];
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(innerArray, size);
    }

    @Override
    public boolean add(E e) {
        if (size == innerArray.length - 1) {
            increaseInnerArray();
        }
        innerArray[size] = e;
        size++;
        return true;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) innerArray[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        E oldElem = (E) innerArray[index];
        innerArray[index] = element;
        return oldElem;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, innerArray[i])) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, innerArray[i])) index = i;
        }
        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new MyArrayListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        checkIndex(index);
        return new MyArrayListIterator(index);
    }

    private class MyArrayListIterator implements ListIterator<E> {
        private int cursor;

        private MyArrayListIterator() {
            this(-1);
        }

        private MyArrayListIterator(int index) {
            this.cursor = index;
        }

        @Override
        public boolean hasNext() {
            return cursor < size - 1;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            cursor++;
            return (E) innerArray[cursor];
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            cursor--;
            return (E) innerArray[cursor];
        }

        @Override
        public int nextIndex() {
            return cursor == size - 1 ? size : cursor + 1;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E e) {
            MyArrayList.this.set(cursor, e);
        }

        @Override
        public void add(E e) {
            MyArrayList.this.add(e);
        }
    }

    private void increaseInnerArray() {
        innerArray = Arrays.copyOf(innerArray, innerArray.length * GROWTH_RATE);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
