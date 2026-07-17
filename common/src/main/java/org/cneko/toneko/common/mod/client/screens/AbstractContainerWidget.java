package org.cneko.toneko.common.mod.client.screens;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;

/** Compatibility equivalent of the container widget added after 1.20.1. */
abstract class AbstractContainerWidget extends AbstractWidget implements ContainerEventHandler {
    private GuiEventListener focusedChild;
    private boolean dragging;

    protected AbstractContainerWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    public GuiEventListener getFocused() { return focusedChild; }
    public void setFocused(GuiEventListener listener) { this.focusedChild = listener; }
    public boolean isDragging() { return dragging; }
    public void setDragging(boolean dragging) { this.dragging = dragging; }
}
