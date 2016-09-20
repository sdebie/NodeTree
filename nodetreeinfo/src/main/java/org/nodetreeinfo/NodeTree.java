/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nodetreeinfo;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
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

	private static final long serialVersionUID = -2707712944901661771L;
	private static mxGraph graph;

//	private static Database db;
	private static String MAINDB_USER = "vodacom";
	private static String MAINDB_PASS = "vodacom";

	public NodeTree()
	{

	}

	/**
	 *
	 */
	public static void InitTree()
	{

		graph = new mxGraph();
		mxCompactTreeLayout vLayout = new mxCompactTreeLayout(graph);
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		try {
			graph.insertVertex(parent, "1", "Kick", 1, 1, 80, 30);
			graph.insertVertex(parent, "2", "Ass", 1, 1, 80, 30);
			graph.insertVertex(parent, "3", "Node One", 1, 1, 80, 30);
			graph.insertVertex(parent, "4", "Node 2", 1, 1, 80, 30);
			graph.insertVertex(parent, "5", "Node 3", 1, 1, 80, 30);

			graph.insertVertex(parent, "6", "Level 3-One", 1, 1, 80, 30);
			graph.insertVertex(parent, "7", "Leverl 3-2", 1, 1, 80, 30);
			graph.insertVertex(parent, "8", "Leevl 3-3", 1, 1, 80, 30);

			mxGraphModel test = new mxGraphModel();
			int iIndex = 1;
			while (iIndex < 5) {
				Object cell1 = test.getChildAt(parent, 0);
				mxCell cell2 = (mxCell) test.getChildAt(parent, iIndex);
				if (cell2.getId().isEmpty()) {
					break;
				}//if
				graph.insertEdge(parent, "", "", cell1, cell2);
				iIndex++;
			}//while

			while (true) {
				Object cell1 = test.getChildAt(parent, 2);
				mxCell cell2 = (mxCell) test.getChildAt(parent, iIndex);
				if (cell2.getId().isEmpty()) {
					break;
				}//if
				graph.insertEdge(parent, "", "", cell1, cell2);
				iIndex++;
			}//while

			vLayout.setHorizontal(false);
			vLayout.setLevelDistance(10);
			vLayout.setNodeDistance(20);
			vLayout.setResetEdges(true);
			vLayout.setEdgeRouting(true);

			vLayout.execute(parent);

		} finally {
			graph.getModel().endUpdate();
		}

	}//InitTree

	public static void main(String[] args)
	{
		InitTree();
		JFrame frame = new JFrame();
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		frame.getContentPane().add(graphComponent);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	//----------------------------------------------------------[ getTestDB ]---
	public static String getTestDB()
	{
//		return "jdbc:oracle:thin:" + MAINDB_USER + "/" + MAINDB_PASS + "@proddb:1521:proddb";
		return "jdbc:oracle:thin:" + MAINDB_USER + "/" + MAINDB_PASS + "@oradev:1521:oradev";
	}//getOLTPTestDB

	//------------------------------------------------------[ switchContext ]---
	public static void switchContext(int pEnv) throws Exception
	{
		String vDatabaseUrl;

		vDatabaseUrl = getTestDB();

		try {
//			db = Database.getInstance(vDatabaseUrl);
		}//if
		catch (Exception Ex) {
			//Try again just in case it now works :)
//			db = Database.getInstance(vDatabaseUrl);
		}//catch
//		db.setContext("USER", "admin");
//		db.setDefault();
	}//switchContext
}//NodeTree
