package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TextFieldTest extends JTextField implements MenuElement {
    @Override
    public void processMouseEvent(MouseEvent event, MenuElement[] path, MenuSelectionManager manager) {
        System.out.println("TEST");
    }

    @Override
    public void processKeyEvent(KeyEvent event, MenuElement[] path, MenuSelectionManager manager) {
        System.out.println("TEST");

    }

    @Override
    public void menuSelectionChanged(boolean isIncluded) {
        System.out.println("TEST");

    }

    @Override
    public MenuElement[] getSubElements() {
        System.out.println("TEST");

        return new MenuElement[0];
    }

    @Override
    public Component getComponent() {
        System.out.println("TEST");

        return null;
    }
}
