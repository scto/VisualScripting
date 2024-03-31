package com.dark.visualscripting.NodeVIew;

import android.view.View;

public class Node {
    public String title;
    public float x;
    public float y; // Position of the node on the canvas
    public View nodeView;

    public Node(View nodeView, String title, int x, int y) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.nodeView = nodeView;
    }

    // Getters and setters


    public View getNodeView() {
        return nodeView;
    }

    public void setNodeView(View nodeView) {
        this.nodeView = nodeView;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.nodeView.setX(x);
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.nodeView.setY(y);
        this.y = y;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
