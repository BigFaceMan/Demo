package Structural;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/*
    一个记录了删除历史的Set实现类
*/

class HistorySet<E> implements Set<E> {
    private final Set<E> delegate;
    private final List<E> removeList;

    HistorySet(Set<E> delegate) {
        this.delegate = delegate;
        this.removeList = new ArrayList<>();
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return delegate.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return delegate.iterator();
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return delegate.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return delegate.add(e);
    }

    @Override
    public boolean remove(Object o) {
        if (delegate.remove(o)) {
            removeList.add((E) o);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return delegate.toString() + " | Removed: " + removeList.toString();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
    }
}

public class DecoratorDemoSet {
    public static void main(String[] args) {
        Set<String> historySet = new HistorySet<>(new HashSet<>());
        Set<String> set = new HistorySet<>(historySet);
        set.add("1");
        set.add("2");
        set.add("3");
        set.add("4");
        set.remove("4");
        set.remove("4");
        set.remove("5");
        set.remove("1");
        System.out.println(set);
    }
}
