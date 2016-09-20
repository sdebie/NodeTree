/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nodetreeinfo;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javax.swing.JFrame;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedMultigraph;

/**
 *
 * @author shawndebie
 */
public class NodeTree extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -2707712944901661771L;

	public NodeTree()
	{
		super("Hello, World!");

		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		try {
			Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
					30);
			Object v2 = graph.insertVertex(parent, null, "World!", 240, 150,
					80, 30);
			graph.insertEdge(parent, null, "Edge", v1, v2);
		} finally {
			graph.getModel().endUpdate();
		}

		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);
	}

	public static void main(String[] args)
	{
		NodeTree frame = new NodeTree();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 320);
		frame.setVisible(true);
	}

	public static void InitTree2()
	{
		ListenableGraph<String, DefaultEdge> g = new ListenableDirectedMultigraph<String, DefaultEdge>(DefaultEdge.class);
	}

	private static class ListenableDirectedMultigraph<V, E>
			extends DefaultListenableGraph<V, E>
			implements DirectedGraph<V, E> {

		private static final long serialVersionUID = 1L;

		ListenableDirectedMultigraph(Class<E> edgeClass)
		{
			super(new DirectedMultigraph<V, E>(edgeClass));
		}
	}
}//NodeTree
