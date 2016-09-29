/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nodetreeinfo;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JFrame;
import org.tradeswitch.base.DBException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author shawndebie
 */
public class NodeTree extends JFrame {

	private static final long serialVersionUID = -2707712944901661771L;
	private static mxGraph graph;
    private static Connection conn;
    private static String DbSchemaName;

	public NodeTree()
	{

	}

	public static void InitTree() throws DBException, SQLException
    {

        graph = new mxGraph();
        mxHierarchicalLayout vLayout = new mxHierarchicalLayout(graph);
		Object parent = graph.getDefaultParent();

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
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        setMenu(frame);

        frame.setVisible(true);
        conn = connectToDatabaseOrDie();
//		InitTree();
//		JFrame frame = new JFrame();
//		mxGraphComponent graphComponent = new mxGraphComponent(graph);
//		graphComponent.setEnabled(false);
//        frame.getContentPane().add(graphComponent);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(800, 600);
//        frame.setVisible(true);

	}

	private static Connection connectToDatabaseOrDie()
    {
        try {
            String url = null;

            Class.forName("org.postgresql.Driver");
            if (DbSchemaName != null) {

            switch (DbSchemaName) {
                case "Bluetech":
                    url = "jdbc:postgresql://devpg:5432/qa?currentSchema=db";
                    conn = DriverManager.getConnection(url, "qa", "qa");
                    break;

                case "GloCell":
                    url = "jdbc:postgresql://devpg:5432/qa?currentSchema=db";
                    conn = DriverManager.getConnection(url, "qa", "qa");
                    break;

                case "Neotel":
                    url = "jdbc:postgresql://devpg:5432/qa?currentSchema=db";
                    conn = DriverManager.getConnection(url, "qa", "qa");
                    break;

                case "QA":
                    url = "jdbc:postgresql://devpg:5432/qa?currentSchema=db";
                    conn = DriverManager.getConnection(url, "qa", "qa");
                    break;

                case "VMSA":
                    url = "jdbc:postgresql://devpg:5432/qa?currentSchema=db";
                    conn = DriverManager.getConnection(url, "qa", "qa");
                    break;

                case "Vodacom":
                    url = "jdbc:postgresql://devpg:5432/qa?currentSchema=db";
                    conn = DriverManager.getConnection(url, "qa", "qa");
                    break;

                case "<None>":
                    JOptionPane.showMessageDialog(null, "Please select a Database");
                    break;
                }//switch
            }//if

        }//try
        catch (ClassNotFoundException e) {
			System.exit(1);
        }//catch
        catch (SQLException e) {
            System.exit(2);
        }//catch

		return conn;
    }

    private static void setMenu(JFrame pFrame) {
        JPanel vPanelToolbar = new JPanel();
        vPanelToolbar.setSize(400, 600);
        vPanelToolbar.add(new JLabel("Please Select a Database : "));

        final JComboBox vDbCombobox = new JComboBox();
        vDbCombobox.addItem("<None>");
        vDbCombobox.addItem("QA");
        vDbCombobox.addItem("Bluetech");
        vDbCombobox.addItem("GloCell");
        vDbCombobox.addItem("Neotel");
        vDbCombobox.addItem("VMSA");
        vDbCombobox.addItem("Vodacom");

        vDbCombobox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e) {
                DbSchemaName = vDbCombobox.getSelectedItem().toString();
                connectToDatabaseOrDie();
            }
        });

        vPanelToolbar.add(vDbCombobox);

        JPanel vButtonPanel = new JPanel();
        JButton vRefresh = new JButton();
        vRefresh.setText("Refresh");
        vRefresh.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                //clear the panel that has the tree

                vDbCombobox.setSelectedIndex(0);

            }
        });
        vButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        vButtonPanel.add(vRefresh);

        vPanelToolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
        pFrame.getContentPane().add(vPanelToolbar);
        pFrame.getContentPane().add(vButtonPanel);

    }

}//NodeTree
