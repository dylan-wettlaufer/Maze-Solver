import java.util.ArrayList;
import java.util.Iterator;

/* This class represents a Graph. Each graph has a node array and an edge matrix */
public class Graph implements GraphADT{

    private GraphEdge[][] edgeAdjMatrix; /* Matrix that holds the edges of the graph */
    private GraphNode[] nodeAdjMatrix; /* Array that holds the nodes of the graph */

    /* Constructor: creates the graph with the given size n */
    public Graph(int n) {
        edgeAdjMatrix = new GraphEdge[n][n]; //initializes the size of the matrix
        nodeAdjMatrix = new GraphNode[n]; // initializes the size of the array
        for (int i = 0; i < n; i++) { // creates a new Graph node with the given name and adds it to the node array
            GraphNode node = new GraphNode(i);
            nodeAdjMatrix[i] = node;
        }
    }

    /* Inserts an edge into the graph. Takes the given parameters to create an edge then it is added to the edge matrix */
    @Override
    public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) throws GraphException {
        GraphEdge edge = new GraphEdge(nodeu, nodev, type, label);
        // if the nodes name is out of bounds the node doesn't exist in the graph
        if ((nodeu.getName() < 0 || nodeu.getName() >= nodeAdjMatrix.length) || (nodev.getName() < 0 || nodev.getName() >= nodeAdjMatrix.length)) throw new GraphException("Nodes not in graph");
        if (edgeAdjMatrix[nodeu.getName()][nodev.getName()] != null) throw new GraphException("Edge already in graph");
        else { // edge is added to both locations in the matrix
            edgeAdjMatrix[nodeu.getName()][nodev.getName()] = edge;
            edgeAdjMatrix[nodev.getName()][nodeu.getName()] = edge;
        }
    }

    /* Gets the node with the given name u */
    @Override
    public GraphNode getNode(int u) throws GraphException {
        if ((u < 0 || u >= nodeAdjMatrix.length)) throw new GraphException("Node not in graph"); // checks if the number is in bounds for the graph
        else return nodeAdjMatrix[u];
    }

    /* Returns an iterator that contains all incident edges of the given graph node u */
    @Override
    public Iterator incidentEdges(GraphNode u) throws GraphException {
        if ((u.getName() < 0 || u.getName() >= nodeAdjMatrix.length)) throw new GraphException("Node not in graph"); // checks if the node is in the graph
        ArrayList<GraphEdge> edges = new ArrayList<GraphEdge>();
        for (int i = 0; i < nodeAdjMatrix.length; i++) { // checks the row of the given nodes name. If an index isn't null, a edge exists and it is added to the array list
            if (edgeAdjMatrix[u.getName()][i] != null) edges.add(edgeAdjMatrix[u.getName()][i]);
        }
        Iterator<GraphEdge> edgesIterator = edges.iterator();
        if (edgesIterator.hasNext()) return edgesIterator; // if iterator isn't empty, then the iterator is returned
        else return null;
    }

    /* Gets the edge of the given two graph nodes */
    @Override
    public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
        // checks if the nodes are in the graph
        if ((u.getName() < 0 || u.getName() >= nodeAdjMatrix.length) || (v.getName() < 0 || v.getName() >= nodeAdjMatrix.length)) throw new GraphException("Node not in graph");
        if (edgeAdjMatrix[u.getName()][v.getName()] != null) return edgeAdjMatrix[u.getName()][v.getName()]; // if the index in the matrix isn't null, the edge is returned
        else throw new GraphException("Edge not in graph");
    }

    /* Returns true if the two Graph Nodes are adjacent, false otherwise. */
    @Override
    public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
        // checks if the nodes are in the graph
        if ((u.getName() < 0 || u.getName() >= nodeAdjMatrix.length) || (v.getName() < 0 || v.getName() >= nodeAdjMatrix.length)) throw new GraphException("Node not in graph");
        return edgeAdjMatrix[u.getName()][v.getName()] != null; // if the two nodes have an edge connecting them, true is returned
    }
}
