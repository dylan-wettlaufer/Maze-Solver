/* This class represents a Graph Edge. Each edge has two Graph Nodes u and v, a type, and a label */
public class GraphEdge {

    private GraphNode u, v; /* Represents the starting and endpoint of the edge */
    private int type; /* Represent the number of coins needed to traverse the edge */
    private String label; /* Represents the type of edge it is. Either door or corridor */

    /* Constructor: creates the Graph Node with the given parameters */
    public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
        this.u = u;
        this.v = v;
        this.type = type;
        this.label = label;
    }

    /* returns the edge's first endpoint */
    public GraphNode firstEndpoint() {
        return u;
    }

    /* Returns the edge's second endpoint */
    public GraphNode secondEndpoint() {
        return v;
    }

    /* Returns the type */
    public int getType() {
        return type;
    }

    /* Sets the type */
    public void setType(int newType) {
        this.type = newType;
    }

    /* Returns the label */
    public String getLabel() {
        return label;
    }

    /* Sets the label */
    public void setLabel(String newLabel) {
        this.label = newLabel;
    }

}
