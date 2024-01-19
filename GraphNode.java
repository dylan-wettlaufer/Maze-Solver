/* this class represents a Graph Node. Each node has a name and a mark */
public class GraphNode {

    private int name; /* Name of node */
    private boolean mark; /* Mark of node. Mark is either true of false */

    /* Constructor: creates the Graph Node with the given name */
    public GraphNode(int name) {
        this.name = name;
        this.mark = false;
    }

    /* Marks the Graph Node with the given boolean */
    public void mark(boolean mark) {
        this.mark = mark;
    }

    /* Checks is the graph is marked */
    public boolean isMarked() {
        return mark;
    }

    /* Getter for the Graph Node's name */
    public int getName() {
        return name;
    }

}
