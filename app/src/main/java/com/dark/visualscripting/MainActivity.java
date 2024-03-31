package com.dark.visualscripting;

import android.app.ActionBar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dark.visualscripting.NodeVIew.Node;
import com.dark.visualscripting.NodeVIew.VisualScriptingView;

public class MainActivity extends AppCompatActivity {

    VisualScriptingView scriptingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scriptingView = findViewById(R.id.scriptView);


        Node node1 = new Node(new Button(this), "Node 1", 400, 400);
        Node node2 = new Node(new Button(this), "Node 2", 200, 600);
        Node node3 = new Node(new Button(this), "Node 3", 600, 600);

        scriptingView.addNode(node1);
        scriptingView.addNode(node2);
        scriptingView.addNode(node3);

        // Add a connection between nodes
        scriptingView.addConnection(node1, node2);
        scriptingView.addConnection(node2, node3);
        scriptingView.addConnection(node1, node3);

    }
}