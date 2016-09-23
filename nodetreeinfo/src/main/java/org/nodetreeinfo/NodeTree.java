/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nodetreeinfo;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JFrame;
import org.tradeswitch.base.DBException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

/**
 *
 * @author shawndebie
 */
public class NodeTree extends JFrame {

	private static final long serialVersionUID = -2707712944901661771L;
	private static mxGraph graph;
	private static Connection conn;

	public NodeTree()
	{

	}

	public static void InitTree() throws DBException, SQLException
	{

		graph = new mxGraph();
		mxHierarchicalLayout vLayout = new mxHierarchicalLayout(graph);
		Object parent = graph.getDefaultParent();
		mxGraphModel vModel = new mxGraphModel();
		LinkedHashMap<Integer, Integer> relasions = new LinkedHashMap<>();

		graph.getModel().beginUpdate();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from NODES order by NDS_IRN");

			while (rs.next()) {
				String vNodeName = rs.getString("NDS_NAME");
				Long vNodeID = rs.getLong("NDS_IRN");
				graph.insertVertex(parent, String.valueOf(vNodeID), vNodeName, 1, 1, 80, 30);//, "fillColor=green");

			}//while

			vLayout.setResizeParent(false);
			vLayout.setMoveParent(false);
			vLayout.setParentBorder(0);

			vLayout.setIntraCellSpacing(30);
			vLayout.setInterRankCellSpacing(50);
			vLayout.setInterHierarchySpacing(10);
			vLayout.setParallelEdgeSpacing(10);

			vLayout.setOrientation(mxConstants.DIRECTION_MASK_WEST);

			vLayout.setFineTuning(true);
			//vLayout.
			vLayout.setDisableEdgeStyle(false);

			vLayout.execute(parent);

		} finally {
			graph.getModel().endUpdate();
		}

	}//InitTree

	public static void main(String[] args) throws Exception
	{
		conn = connectToDatabaseOrDie();
		InitTree();
		JFrame frame = new JFrame();
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		graphComponent.setEnabled(false);
		frame.getContentPane().add(graphComponent);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	private static Connection connectToDatabaseOrDie()
	{
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://devpg:5432/qa?currentSchema=db";
			conn = DriverManager.getConnection(url, "qa", "qa");
		} catch (ClassNotFoundException e) {
			System.exit(1);
		} catch (SQLException e) {
			System.exit(2);
		}
		return conn;
	}

}//NodeTree
