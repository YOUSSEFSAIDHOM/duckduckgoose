package io.github.duckduckgoose.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.duckduckgoose.items.AbstractItem;
import io.github.duckduckgoose.utils.ItemArrayStack;

public class Hotbar {
    ItemArrayStack itemArray;
    Table table;
    Cell<?>[] cells;
    Table[] slots;
    int size;
    int activeIndex; // index of the item that the player is holding

    TextureRegionDrawable slotBackground;
    TextureRegionDrawable selectedSlotBackground;

    public int getSize() { return size; }

    public Table getTable() { return table; }

    public AbstractItem getItem(int index) {
        slots[activeIndex].setBackground(slotBackground);
        activeIndex = index;
        slots[activeIndex].setBackground(selectedSlotBackground);

        return itemArray.getItem(index);
    }

    public void addItem(AbstractItem item) {
        itemArray.addItem(item);
        update();
    }

    public void removeItem(int index) {
        itemArray.removeItem(index); update();
    }

    public Hotbar(int size) {
        this.size = size;
        itemArray = new ItemArrayStack(size);
        cells = new Cell[size];
        table = new Table();
        table.setFillParent(true);
        table.bottom().padBottom(12);

        // Slots
        // Parameter order is R, G, B and alpha (opacity)
        slotBackground = makeSlotDrawable(
                new Color(0, 0, 0,1f),
                new Color(0.6f, 0.6f, 0.6f, 1f)
        );

        selectedSlotBackground = makeSlotDrawable(
                new Color(1f, 1f, 1f, 0.15f),
                new Color (1f, 0.85f, 0.2f, 1f)
        );

        slots = new Table[size];

        for (int i = 0; i < size; i++) {
            slots[i] = new Table();
            slots[i].setBackground(slotBackground);
            cells[i] = table.add(slots[i]).size(64).pad(4);
        }
    }

    public void update() {
        for (int i = 0; i < size; i++) {
            slots[i].clearChildren();
            AbstractItem item = itemArray.getItem(i);
            if (item != null) {
                slots[i].add(new Image(new TextureRegionDrawable(item.getIcon()))).size(64);
            }
        }
    }

    private TextureRegionDrawable makeSlotDrawable(Color backgroundColour, Color borderColour) {
        int size = 64; // GlobalVariables?
        // Parameter order is width height and colour
        Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        pixmap.fill();
        pixmap.setColor(borderColour);

        /*
        This for loop is doing 3 calls, first one is drawing the very edge, the second is drawing the outline 1px inside
        and then the last call is drawing the outline 2px inside.
         */

        for (int i = 0; i < 3; i++) {
            // move more inwards per call (x, y) and then the size is going to be reduced by 2 * i pixels each call
            pixmap.drawRectangle(i, i, size - 2 * i, size - 2 * i);
        }
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        return drawable;

    }

    public void clearItems() {
        itemArray.clearAll();
        update();
    }

}
