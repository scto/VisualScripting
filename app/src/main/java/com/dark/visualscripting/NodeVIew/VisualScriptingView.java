package com.dark.visualscripting.NodeVIew;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;

import com.dark.visualscripting.R;

import java.util.ArrayList;
import java.util.List;

public class VisualScriptingView extends RelativeLayout {
    private List<Node> nodes;
    private List<Pair<Node, Node>> connections; // Store connections between nodes
    private Paint nodePaint;
    private Paint linePaint;
    private Node draggedNode;
    private float offsetX, offsetY;

    public VisualScriptingView(Context context) {
        super(context);
        init();
    }

    public VisualScriptingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VisualScriptingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VisualScriptingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        nodes = new ArrayList<>();
        connections = new ArrayList<>();

        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

        nodePaint = new Paint();
        nodePaint.setColor(Color.BLUE);
        nodePaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(5); // Set line width as needed
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw connections
        for (Pair<Node, Node> connection : connections) {
            Node node1 = connection.first;
            Node node2 = connection.second;

            // Determine control points for the Bezier curve
            float startX = node1.getX() + (float) node1.getNodeView().getWidth() / 2;
            float startY = node1.getY() + (float) node1.getNodeView().getHeight() / 2;
            float endX = node2.getX() + (float) node2.getNodeView().getWidth() / 2;
            float endY = node2.getY() + (float) node2.getNodeView().getHeight() / 2;

            float controlX1 = startX + (endX - startX) / 2;
            float controlY1 = startY;
            float controlX2 = endX - (endX - startX) / 2;
            float controlY2 = endY;

            // Draw Bezier curve
            Path path = new Path();
            path.moveTo(startX, startY);
            path.cubicTo(controlX1, controlY1, controlX2, controlY2, endX, endY);
            canvas.drawPath(path, linePaint);
        }

        // Draw nodes
//        for (Node node : nodes) {
//            canvas.drawCircle(node.getX(), node.getY(), 50, nodePaint);
//            canvas.drawText(node.getTitle(), node.getX(), node.getY(), linePaint);
//        }
    }

    private boolean isTouchInsideNode(float x, float y, Node node) {
        // Check if touch event coordinates are within node boundaries

        if (x >= node.getX() && x <= node.getX() + node.getNodeView().getWidth() && y >= node.getY() && y <= node.getY() + node.getNodeView().getHeight()) {
            Log.d("Drag", "Start");
            return true;
        } else {
            Log.d("Drag", "Null");
            return false;
        }
    }

    public void addNode(Node node) {
        nodes.add(node);

        // Set the text of the button
        ((Button) node.getNodeView()).setText(node.getTitle());


        node.nodeView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Check if touch event is inside the node
                        if (!isTouchInsideNode(event.getX(), event.getY(), node)) {
                            draggedNode = node;
                            offsetX = event.getX() - node.getX();
                            offsetY = event.getY() - node.getY();
                            // Return true to consume the touch event
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (draggedNode != null) {
                            // Update node position according to touch position
                            draggedNode.setX((int) (event.getX() - offsetX));
                            draggedNode.setY((int) (event.getY() - offsetY));
                            invalidate(); // Redraw canvas
                            // Return true to consume the touch event
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        draggedNode = null; // Reset dragged node
                        break;
                }
                return false; // Return false to allow further handling of the touch event
            }
        });


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        node.getNodeView().setLayoutParams(params);
        node.nodeView.setX(node.x);
        node.nodeView.setY(node.y);

        addView(node.getNodeView());
        invalidate(); // Redraw the canvas
    }

    // Method to add a connection between two nodes
    public void addConnection(Node node1, Node node2) {
        connections.add(new Pair<>(node1, node2));
        invalidate(); // Redraw the canvas
    }

}

