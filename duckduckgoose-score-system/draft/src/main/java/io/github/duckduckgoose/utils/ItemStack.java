package io.github.duckduckgoose.utils;

import io.github.duckduckgoose.items.AbstractItem;

/**
 * Establishes the attributes and methods of a given item within the item stack.
 */
public class ItemStack {
    AbstractItem item;
    int count;

    /**
     * Constructor method for the stack item.
     *
     * @param item the specific item object
     * @param count the amount of the given item
     */
    public ItemStack(AbstractItem item, int count) {
        this.item = item;
        this.count = count;
    }

    public void addItem(int items) { count += items; }

    public void removeItem() { count--; }

    public int getCount() { return count;}

    public String getItemName() { return item.getName(); }

    public AbstractItem getItem() { return item; }

}
