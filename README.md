This program receives as input a file with a description of a maze and then solves the maze, if an exit exists. A maze has a set of rooms that are separated by corridors, some of which are blocked by doors that can only be opened by the given number of coins (0 to 9). 
Coins can only be used once and each maze comes with a given number of coins that can be usedThere are also walls which can't be crossed that separate some rooms. 
Each maze is created with an undirected graph that uses nodes and vertices to connect it. Each GraphNode has an integer name (a number from 0 to n) and a mark, which is a boolean variable. 
Each edge in the graph has two Graph Nodes, a start point and an endpoint. It has a type which represents the number of coins needed to traverse the edge. label represents if it is a door or a corridor.
The graph is implemented with an adjacency matrix that holds the GraphNodes in an array and the GraphEdges in a 2D array. 
The maze is created by reading from the input file and reading each character in the file. Each letter or number represents a door, corridor, wall, start, or exit. The maze is solved by returning an iterator of the GraphNodes needed to go from the start to the exit of the maze.
if no path is found from the start to the exit, null is returned instead of an iterator. With the iterator of GraphNodes needed to reach the exit, the edges are then drawn on the graph to visually display the path needed to reach the exit.
