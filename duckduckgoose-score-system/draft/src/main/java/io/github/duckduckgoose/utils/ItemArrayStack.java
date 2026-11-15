package io.github.duckduckgoose.utils;

import io.github.duckduckgoose.items.AbstractItem;

import java.util.Arrays;

/**
 * Creates a stack which items for the hotbar are 'pushed to' and 'pulled from' accordingly
 */
public class ItemArrayStack {
    private ItemStack[] items;
    private int head;
    private int tail;
    private int size;

    public int getSize() {
        return size;
    }

    public AbstractItem getItem(int index) {
        if (index < items.length && index >= 0) {
            if (items[index] != null) {
                return items[index].getItem();
            }
        } return null;
    }

    public int getCount(int index) {
        if (index < items.length && index >= 0) {
            if (items[index] != null) {
                return items[index].getCount();
            }
        } return 0;
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    /**
     * Creates the basic structure of a stack
     *
     * @param arraySize the size of the array
     */
    public ItemArrayStack(int arraySize) {
        this.items = new ItemStack[arraySize];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    public void addItem(AbstractItem item) {

        // First check if there is an item stack that already exists
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                if (item.getName().equals(items[i].getItemName())) {
                    items[i].addItem(1);
                    return;
                }
            }
        }
        // If not, we add it to a null slot in the hotbar
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = new ItemStack(item, 1);
                return;
            }
        }
    }

    /**
     * Adds an item to the item stack.
     *
     * @param itemStack a specific stack item.
     */
    public void addItemStack(ItemStack itemStack) {
        // First check if there is an item stack that already exists
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                if (itemStack.getItemName().equals(items[i].getItemName())) {
                    items[i].addItem(itemStack.getCount());
                    return;
                }
            }
        }
        // If not, we add it to a null slot in the hotbar
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = itemStack;
                return;
            }
        }
    }

    /**
     * Removes an item from the stack.
     *
     * @param index the hotbar position to remove.
     */
    public void removeItem(int index) {
        items[index].removeItem();
        if (items[index].getCount() == 0) {
            items[index] = null;
        }
        size--;
    }

    /**
     * Clears all items from the stack.
     */
    public void clearAll() {
        Arrays.fill(items, null);
    }
}
