import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/* This class represents a Maze. Each maze has a graph, pathList, exit, and a given number of coins */
public class Maze {

    private Graph graph; /* The graph that will store the maze */
    private ArrayList<GraphNode> pathList; /* The list that will hold the nodes that lead to the exit */
    private GraphNode exit; /* The exit of the graph */
    private int coins; /* The number of coins that can be used in the maze */

    /* Constructor: reads from the input file and builds the maze using the graph */
    public Maze(String inputFile) throws IOException, GraphException, MazeException {

        BufferedReader in = new BufferedReader(new FileReader(inputFile));

        int scaleFactor = Integer.parseInt(in.readLine()); // This line isn't used
        int width = Integer.parseInt(in.readLine());
        int length = Integer.parseInt(in.readLine());
        coins = Integer.parseInt(in.readLine());

        int mazeSize = width * length;
        graph = new Graph(mazeSize);

        String mazeLine;
        String[][] boardArray = new String[length + (length-1)][]; // creates a 2d array that will store the contents of the input file
        int count = 0;
        while ((mazeLine = in.readLine()) != null) { // reads each line and adds it to the boardArray matrix
            String[] spaces = mazeLine.split("");
            if (spaces.length != width + (width-1)) throw new MazeException("Input file format is incorrect"); // throws an exception if the input file format is incorrect
            boardArray[count] = spaces;
            count++;
        }
        if (boardArray.length != length + (length-1)) throw new MazeException("Input file format is incorrect");

        numMaze(boardArray); // Calls to the numMaze function so that each node 'o' is replaced with its given number
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[i].length; j++) {
                if (i % 2 == 0 && j % 2 == 1) { // checks if this row contains graph nodes, makes sure graph nodes aren't being changed to edges. Horizontal edges/walls
                   if (boardArray[i][j].matches("[0-9]")) { // inserts the edge with the given type and label into the graph between node j-1 and node j+1
                       graph.insertEdge(graph.getNode(Integer.parseInt(String.valueOf(boardArray[i][j-1]))),
                               graph.getNode(Integer.parseInt(String.valueOf(boardArray[i][j+1]))),
                               Integer.parseInt(String.valueOf(boardArray[i][j])), "door");
                   }
                   else if (boardArray[i][j].equals("c")) { // inserts the edge with the given type  and label between node j-1 and node j+1 into the graph
                       graph.insertEdge(graph.getNode(Integer.parseInt(String.valueOf(boardArray[i][j-1]))),
                               graph.getNode(Integer.parseInt(String.valueOf(boardArray[i][j+1]))),
                               0, "corridor");
                   }
                }
                else if (i % 2 == 1) { // checks if this row doesn't contain graph nodes. Vertical edges/walls
                    if (boardArray[i][j].matches("[0-9]")) { // inserts the edge with the given type  and label between node i-1 and node i+1 into the graph
                        graph.insertEdge(graph.getNode(Integer.parseInt(String.valueOf(boardArray[i-1][j]))),
                                graph.getNode(Integer.parseInt(String.valueOf(boardArray[i+1][j]))),
                                Integer.parseInt(String.valueOf(boardArray[i][j])), "door");
                        }
                    else if (boardArray[i][j].equals("c")) { // inserts the edge with the given type  and label between node i-1 and node i+1 into the graph
                        graph.insertEdge(graph.getNode(Integer.parseInt(String.valueOf(boardArray[i-1][j]))),
                                graph.getNode(Integer.parseInt(String.valueOf(boardArray[i+1][j]))),
                                0, "corridor");
                    }
                }
            }
        }
    }

    /* Returns the graph that contains the maze. If the graph is null, a MazeException is thrown */
    public Graph getGraph() throws MazeException{
        if (graph == null) throw new MazeException("Graph is null");
        else return graph;
    }

    /* Solves the maze by finding the path from the start to the exit */
    public Iterator solve() throws GraphException {
        pathList = new ArrayList<GraphNode>();
        if (path(graph.getNode(0), exit, coins)) return pathList.iterator(); // if path returns true, an iterator of pathList is returned
        else return null;
    }

    /* Returns true if there is a path from u to e, false otherwise */
    private boolean path(GraphNode u, GraphNode e, int coins) throws GraphException {
        u.mark(true); // marks the node as true since it has been visited
        pathList.add(u); // adds it to the list
        if (u == e) return true; // if the end node is reached, true is returned
        else {
            Iterator edgesList = graph.incidentEdges(u);
            while (edgesList.hasNext()) { // loops for each of u's incident edges
                GraphEdge edge = (GraphEdge) edgesList.next();
                if (!edge.secondEndpoint().isMarked() && edge.firstEndpoint() == u) { // checks if the second endpoint is marked when u is the first endpoint
                    if (edge.getLabel().equals("corridor")) {
                        if (path(edge.secondEndpoint(), e, coins)) return true; // calls path but u is now the second endpoint
                    }
                    else if (edge.getLabel().equals("door")) {
                        int doorCost = edge.getType();
                        if (coins >= doorCost) { // if there are more coins then the cost for the door, path is called with reduced coins
                            if (path(edge.secondEndpoint(), e, coins - doorCost)) return true;
                        }
                    }
                }
                else if (!edge.firstEndpoint().isMarked() && edge.secondEndpoint() == u) { // other case, checks if the first endpoint is marked when u is the second endpoint
                    if (edge.getLabel().equals("corridor")) {
                        if (path(edge.firstEndpoint(), e, coins)) return true; // calls path but u is now the first endpoint
                    }
                    else if (edge.getLabel().equals("door")) {
                        int doorCost = edge.getType();
                        if (coins >= doorCost)  { // if there are more coins then the cost for the door, path is called with reduced coins
                            if (path(edge.firstEndpoint(), e, coins - doorCost)) return true;
                        }
                    }
                }
            }
            u.mark(false); // if the node doesn't lead to the path, u is unmarked and removed from the list. false is then returned
            pathList.remove(u);
            return false;
        }
    }

    /* Replaces each node character with the given number from 0, 1, 2, ... , n. Also looks for 'x' to mark the exit */
    private void numMaze(String[][] boardArray) throws GraphException {
        int count = 0; // count starts at zero since every graph starts at node zero
        boolean exitFound = false;
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[i].length; j++) {
                if (i % 2 == 0 && (i + j) % 2 == 0) { // makes sure every other row and every other column is changed
                    if (boardArray[i][j].equals("x")) exitFound = true; // if the string equals x, the exit has been found
                    boardArray[i][j] = Integer.toString(count); // the count is added to the boardArray as a string
                    if (exitFound) { // exit is set as the node at the given index and exitFound is set to false so the if statement doesn't run again
                        exit = graph.getNode(Integer.parseInt(boardArray[i][j]));
                        exitFound = false;
                    }
                    count++; // increase count so each nodes name is added to the boardArray
                }
            }
        }
    }
}
