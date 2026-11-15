package io.github.duckduckgoose.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.duckduckgoose.items.AbstractItem;
import io.github.duckduckgoose.utils.ItemStack;
import io.github.duckduckgoose.utils.GlobalVariables;
import io.github.duckduckgoose.utils.ItemArrayStack;

/**
 * Contains all methods for drawing the hotbar and giving the item hotbar functionality
 */
public class Hotbar {
    ItemArrayStack itemArray;
    Table table;
    Cell<?>[] cells;
    Table[] slots;
    int size;
    int activeIndex; // index of the item that the player is holding

    TextureRegionDrawable slotBackground;
    TextureRegionDrawable selectedSlotBackground;
    Skin skin;

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

    public void addItemStack(ItemStack itemStack) {
        itemArray.addItemStack(itemStack);
        update();
    }

    public void removeItem(int index) {
        itemArray.removeItem(index); update();
    }

    /**
     * Constructor method for the hotbar.
     *
     * @param size value for how large the hotbar should be
     */
    public Hotbar(int size) {
        this.size = size;
        this.skin = GlobalVariables.skin;
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

    /**
     * Update the hotbar to display the correct items and sprites currently held by the player.
     */
    public void update() {
        for (int i = 0; i < size; i++) {
            slots[i].clearChildren();
            AbstractItem item = itemArray.getItem(i);
            if (item != null) {
                Image icon = new Image(new TextureRegionDrawable(item.getIcon()));
                int count = itemArray.getCount(i);
                // The instance of stack here is the LibGDX UI stack not to be confused with the data-type
                Stack stack = new Stack();
                stack.add(icon);

                Label countLabel = new Label(String.valueOf(count), skin);
                Table overlay = new Table();
                overlay.add(countLabel).expand().bottom().right().pad(2);
                stack.add(overlay);

                slots[i].add(stack).size(64);
            }
        }
    }

    /**
     * Draws each of the slots of the hotbar
     *
     * @param backgroundColour the background colour for the hotbar slots
     * @param borderColour the border colour for the hotbar slots
     * @return the slot to be drawn
     */
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

    /**
     * Removes all items from the hotbar.
     */
    public void clearItems() {
        itemArray.clearAll();
        update();
    }

}
