package Controller.DragAndDrop;

import GUI.InstanceReasoningScrollPane;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public class KeyDispatcher implements KeyEventDispatcher {

    KeyListener listener;

    public KeyDispatcher(InstanceReasoningScrollPane instanceReasoningScrollPane){
        this.listener = new DraggablePanelDeleteListener(instanceReasoningScrollPane);
    }

    public boolean dispatchKeyEvent(KeyEvent e) {
        if(e.getKeyCode() == 127){
            listener.keyTyped(e);
        }
        return false;
    }
}
