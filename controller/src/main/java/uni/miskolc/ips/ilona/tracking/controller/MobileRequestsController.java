package uni.miskolc.ips.ilona.tracking.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import uni.miskolc.ips.ilona.measurement.model.measurement.Measurement;
import uni.miskolc.ips.ilona.tracking.controller.model.MobileTransferDTO;
import uni.miskolc.ips.ilona.tracking.controller.model.UserSecurityDetails;

@Controller
@RequestMapping(value = "/tracking/mobile")
public class MobileRequestsController {
	private static final Logger LOG = LogManager.getLogger(MobileRequestsController.class);

	private UndirectedGraph<String, DefaultEdge> groundFloorGraph;
	private UndirectedGraph<String, DefaultEdge> firstFloorGraph;
	private UndirectedGraph<String, DefaultEdge> secondFloorGraph;
	
	
	public MobileRequestsController() {
		initializeGroundFloorGraph();
		initializeFirstFloorGraph();
		initializeSecondFloorGraph();
	}
	
	
	@RequestMapping(value = "/trackposition")
	@ResponseBody
	public MobileTransferDTO mobileSendMeasurementHandler(@RequestBody(required = false) MobileTransferDTO dto) {
		
		if(dto == null) {
			return new MobileTransferDTO();
		}
		
		UserSecurityDetails userDetails = (UserSecurityDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(userDetails.toString());
		LOG.info("trackPosition in MobileRequestsController. user:"+userDetails.toString());
		return dto;
	}
	
	@RequestMapping(value = "/proba", produces = "application/json")
	@ResponseBody
	public Measurement dummyHandlerDELETEDDONTUSERIT(@RequestBody(required = false) Measurement mes) {

		if (mes != null) {
			return mes;
		}
		// Authentication auth =
		// SecurityContextHolder.getContext().getAuthentication();
		
		UndirectedGraph<String, DefaultEdge> myGrapgh = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
		myGrapgh.addVertex("V1");
		myGrapgh.addVertex("V2");
		myGrapgh.addVertex("V3");
		myGrapgh.addVertex("V4");
		myGrapgh.addVertex("V5");
		myGrapgh.addVertex("V6");
		myGrapgh.addVertex("V7");
		myGrapgh.addVertex("V8");
		myGrapgh.addVertex("V9");
		myGrapgh.addVertex("V10");
		myGrapgh.addVertex("V11");
		myGrapgh.addVertex("V12");
		myGrapgh.addVertex("V13");
		myGrapgh.addVertex("V14");
		myGrapgh.addVertex("V15");
		myGrapgh.addVertex("V16");
		myGrapgh.addVertex("V17");
		myGrapgh.addVertex("V18");
		myGrapgh.addVertex("V19");
		myGrapgh.addVertex("V20");
		myGrapgh.addVertex("V21");
		myGrapgh.addVertex("V22");

		myGrapgh.addEdge("V1", "V2");
		myGrapgh.addEdge("V2", "V3");
		myGrapgh.addEdge("V3", "V4");
		myGrapgh.addEdge("V4", "V5");
		myGrapgh.addEdge("V5", "V6");
		myGrapgh.addEdge("V6", "V7");
		myGrapgh.addEdge("V7", "V8");
		myGrapgh.addEdge("V7", "V9");
		myGrapgh.addEdge("V5", "V10");
		myGrapgh.addEdge("V10", "V11");
		myGrapgh.addEdge("V10", "V12");
		myGrapgh.addEdge("V12", "V13");
		myGrapgh.addEdge("V13", "V14");
		myGrapgh.addEdge("V4", "V15");
		myGrapgh.addEdge("V15", "V16");
		myGrapgh.addEdge("V16", "V17");
		myGrapgh.addEdge("V16", "V18");
		myGrapgh.addEdge("V18", "V19");
		myGrapgh.addEdge("V19", "V20");
		myGrapgh.addEdge("V20", "V21");
		myGrapgh.addEdge("V20", "V22");

		DijkstraShortestPath<String, DefaultEdge> dij = new DijkstraShortestPath<String, DefaultEdge>(myGrapgh, "V22",
				"V14");

		DijkstraShortestPath<String, DefaultEdge> dij2 = new DijkstraShortestPath<String, DefaultEdge>(myGrapgh, "V14",
				"V22");
		List<DefaultEdge> path = dij.getPathEdgeList();
		List<DefaultEdge> path2 = dij.getPathEdgeList();

		// get the path

		List<String> thePath = new ArrayList<String>();
		String startNode = "V14";
		String endNode = "V22";
		String node1 = null;
		String node2 = null;
		String commonNode = null;
		Graph<String, DefaultEdge> grap = myGrapgh;

		for (DefaultEdge edge : path) {
			node1 = myGrapgh.getEdgeSource(edge);
			node2 = myGrapgh.getEdgeTarget(edge);
			
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
			
			if(node2.equals(startNode)) {
				if(thePath.size() == 0) {
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
			if(node1.equals(endNode)) {
				if(thePath.size() == 0) {
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
			
			if(node2.equals(endNode)) {
				if(thePath.size() == 0) {
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
			if(commonNode.equals(node1)) {
				thePath.add(node2);
				commonNode = node2;
				continue;
			}
			
			if(commonNode.equals(node2)) {
				thePath.add(node1);
				commonNode = node1;
				continue;
			}
				
		}

		System.out.println(path.toString());
		System.out.println(path2.toString());
		System.out.println(thePath.toString());
		
		return new Measurement();
	}
	
	private void initializeGroundFloorGraph() {
		this.groundFloorGraph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
	}
	
	private void initializeFirstFloorGraph() {
		this.groundFloorGraph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
	}

	private void initializeSecondFloorGraph() {
		this.secondFloorGraph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
	}
}
