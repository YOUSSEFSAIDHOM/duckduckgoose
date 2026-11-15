package io.github.duckduckgoose.utils;

import io.github.duckduckgoose.items.AbstractItem;

public class ItemStack {
    AbstractItem item;
    int count;

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
