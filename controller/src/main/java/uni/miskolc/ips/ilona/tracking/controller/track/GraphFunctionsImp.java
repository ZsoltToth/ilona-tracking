package uni.miskolc.ips.ilona.tracking.controller.track;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GraphFunctionsImp implements GraphFunctions {

	private UndirectedGraph<String, DefaultEdge> groundFloor = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
	private UndirectedGraph<String, DefaultEdge> firstFloor = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
	private UndirectedGraph<String, DefaultEdge> secondFloor = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);

	private Collection<String> groundFloorNodes = new ArrayList<String>();
	private Collection<String> firstFloorNodes = new ArrayList<String>();
	private Collection<String> secondFloorNodes = new ArrayList<String>();

	private Schema schema;

	public GraphFunctionsImp() {
		try {
			String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
			SchemaFactory schemaFac = SchemaFactory.newInstance(language);
			ClassLoader loader = getClass().getClassLoader();
			File file = new File(loader.getResource("graphShema.xsd").getFile());

			fillElementsIntoGraph(firstFloor, "graphFirstFloor.xml");
			fillElementsIntoGraph(groundFloor, "graphGroundFloor.xml");
			fillElementsIntoGraph(secondFloor, "graphSecondFloor.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Collection<String> generateShortestPath(String start, String end) {

		DijkstraShortestPath<String, DefaultEdge> dij = new DijkstraShortestPath<String, DefaultEdge>(firstFloor, start,
				end);
		List<DefaultEdge> path = dij.getPathEdgeList();

		List<String> thePath = new ArrayList<String>();
		String startNode = start;
		String endNode = end;
		String node1 = null;
		String node2 = null;
		String commonNode = null;

		for (DefaultEdge edge : path) {
			node1 = firstFloor.getEdgeSource(edge);
			node2 = firstFloor.getEdgeTarget(edge);

			/*
			 * Start node managing
			 */
			if (node1.equals(startNode)) {
				if (thePath.size() == 0) {
					thePath.add(node1);
					thePath.add(node2);
					commonNode = node2;
					continue;

				} else {
					thePath.add(node2);
					thePath.add(node1);
					commonNode = node2;
					continue;
				}
			}

			if (node2.equals(startNode)) {
				if (thePath.size() == 0) {
					thePath.add(node2);
					thePath.add(node1);
					commonNode = node1;
					continue;
				} else {
					thePath.add(node1);
					thePath.add(node2);
					commonNode = node1;
					continue;
				}
			}

			/*
			 * End node managing
			 */
			if (node1.equals(endNode)) {
				if (thePath.size() == 0) {
					thePath.add(node1);
					thePath.add(node2);
					commonNode = node2;
					continue;
				} else {
					thePath.add(node2);
					thePath.add(node1);
					commonNode = node2;
					continue;
				}
			}

			if (node2.equals(endNode)) {
				if (thePath.size() == 0) {
					thePath.add(node2);
					thePath.add(node1);
					commonNode = node1;
					continue;
				} else {
					thePath.add(node1);
					thePath.add(node2);
					commonNode = node1;
					continue;
				}
			}

			/*
			 * Inner node
			 */
			if (commonNode.equals(node1)) {
				thePath.add(node2);
				commonNode = node2;
				continue;
			}

			if (commonNode.equals(node2)) {
				thePath.add(node1);
				commonNode = node1;
				continue;
			}

		}

		return thePath;
	}

	public Floor calculateNodeFloorPosition(String nodeName) {
		if (groundFloorNodes.contains(nodeName)) {
			return Floor.GROUND_FLOOR;
		}
		if (firstFloorNodes.contains(nodeName)) {
			return Floor.FIRST_FLOOR;
		}
		if (secondFloorNodes.contains(nodeName)) {
			return Floor.SECOND_FLOOR;
		}
		return Floor.NONE;
	}

	private void fillElementsIntoGraph(UndirectedGraph<String, DefaultEdge> graph, String elementsFilePath)
			throws Exception {
		/*
		 * Load graph
		 */
		ClassLoader loader = getClass().getClassLoader();
		InputStream inputStream = loader.getResourceAsStream(elementsFilePath);
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);

		Element root = document.getDocumentElement();
		Element nodesElement = (Element) root.getElementsByTagName("graph:nodes").item(0);

		NodeList nodes = nodesElement.getElementsByTagName("graph:node");
		int length = nodes.getLength();
		for (int i = 0; i < length; i++) {
			Element item = (Element) nodes.item(i);
			graph.addVertex(item.getElementsByTagName("graph:name").item(0).getTextContent().trim());
		}

		Element edgesElement = (Element) root.getElementsByTagName("graph:edges").item(0);
		NodeList edges = edgesElement.getElementsByTagName("graph:edge");
		length = edges.getLength();
		for (int i = 0; i < length; i++) {
			Element edge = (Element) edges.item(i);
			graph.addEdge(edge.getElementsByTagName("graph:from").item(0).getTextContent().trim(),
					edge.getElementsByTagName("graph:to").item(0).getTextContent().trim());
		}
	}
}
