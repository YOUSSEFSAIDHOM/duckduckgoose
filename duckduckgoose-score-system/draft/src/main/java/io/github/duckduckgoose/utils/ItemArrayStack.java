package io.github.duckduckgoose.utils;

import io.github.duckduckgoose.items.AbstractItem;

import java.util.Arrays;

public class ItemArrayStack {
    private AbstractItem[] items;
    private int head;
    private int tail;
    private int size;

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    public ItemArrayStack(int arraySize) {
        this.items = new AbstractItem[arraySize];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    public void addItem(AbstractItem item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                size++;
                return;
            }
        }
    }

    public void removeItem(int index) {
        items[index] = null;
        size--;
    }

    public AbstractItem getItem(int index) {
        if (index < items.length && index >= 0) {
            return items[index];
        } return null;
    }

    public void clearAll() {
        Arrays.fill(items, null);
    }
}
