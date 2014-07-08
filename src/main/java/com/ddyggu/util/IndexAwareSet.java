package com.ddyggu.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class IndexAwareSet<T> implements Set<T> {
  
  public LinkedHashSet<T> set = new LinkedHashSet<T>();

  public int size() {
    return this.set.size();
  }

  public boolean isEmpty() {
    return this.set.isEmpty();
  }

  public boolean contains(Object o) {
    return this.set.contains(o);
  }

  public Iterator<T> iterator() {
    return this.set.iterator();
  }

  public Object[] toArray() {
    return this.set.toArray();
  }

 @SuppressWarnings("unchecked")
public Object[] toArray(Object[] a) {
    return this.set.toArray(a);
  }

  public boolean add(Object e) {
    @SuppressWarnings("unchecked")
	T t = (T)e;
    return this.set.add(t);
  }

  public boolean remove(Object o)
  {
    return this.set.remove(o);
  }

  public boolean containsAll(@SuppressWarnings("rawtypes") Collection c)
  {
    return this.set.containsAll(c);
  }

  @SuppressWarnings("unchecked")
public boolean addAll(@SuppressWarnings("rawtypes") Collection c)
  {
    return this.set.addAll(c);
  }

  public boolean retainAll(@SuppressWarnings("rawtypes") Collection c)
  {
    return this.set.retainAll(c);
  }

  public boolean removeAll(@SuppressWarnings("rawtypes") Collection c)
  {
    return this.set.removeAll(c);
  }

  public void clear()
  {
    this.set.clear();
  }

  public int Indexof(T value) {
    int index = 0;
    for (Object element : this.set) {
      index++;
      if (element.equals(value)) {
        return index;
      }
    }
    return -1;
  }

  public static void main(String[] args)
  {
    IndexAwareSet<String> set = new IndexAwareSet<String>();
    set.add("one");
    set.add("two");
    set.add("three");
    set.add("four");
    set.add("five");
    set.add("six");
    set.add("seven");
    set.add("eight");
    set.add("nine");
    set.add("ten");
    set.add("eleven");
    set.add("twelve");
    set.add("thirteen");
    set.add("fourteen");
    set.add("fifteen");
    set.add("sixteen");
    set.add("seventeen");
    set.add("eighteen");
    set.add("nineteen");
    set.add("twenty");
    set.add("one");

    int size = set.size();
    int index = 0;
    for (String element : set) {
      index++;
      if (index == size)
        System.out.print(element);
      else {
        System.out.print(element + ", ");
      }
    }

    System.out.println();

    System.out.println(set.isEmpty());
    System.out.println(set.contains("twenty"));
  }
}
