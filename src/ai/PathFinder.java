package ai;

import lombok.Getter;
import main.GamePanel;

import java.util.ArrayList;
import java.util.List;

import static main.GamePanel.MAX_WORLD_COLUMN;
import static main.GamePanel.MAX_WORLD_ROW;

public class PathFinder {
    private final GamePanel gp;
    private Node[][] node;
    private final List<Node> openList;
    @Getter
    private final List<Node> pathList;
    private Node startNode, goalNode, currentNode;
    private boolean goalReached;
    private int step;

    public PathFinder(GamePanel gp) {
        this.gp = gp;
        openList = new ArrayList<>();
        pathList = new ArrayList<>();
        instantiateNodes();
    }

    private void instantiateNodes() {
        node = new Node[MAX_WORLD_COLUMN][MAX_WORLD_ROW];
        int col = 0, row = 0;
        while (col < MAX_WORLD_COLUMN && row < MAX_WORLD_ROW) {
            node[col][row] = new Node(col, row);
            col ++;
            if (col == MAX_WORLD_COLUMN) {
                col = 0;
                row ++;
            }
        }
    }

    private void resetNodes() {
        int col = 0, row = 0;
        while (col < MAX_WORLD_COLUMN && row < MAX_WORLD_ROW) {
            // Reset open, checked and solid state
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;
            col ++;
            if (col == MAX_WORLD_COLUMN) {
                col = 0;
                row ++;
            }
        }
        // Reset other settings
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();

        // Set Start and Goal nodes
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0, row = 0;

        while (col < MAX_WORLD_COLUMN && row < MAX_WORLD_ROW) {
            // Set solid node and check tiles
            int tilenum = gp.tileM.getMapTileNum()[col][row];
            if (gp.tileM.getTile()[tilenum].collision) {
                node[col][row].solid = true;
            }

            getCost(node[col][row]);

            col ++;
            if (col == MAX_WORLD_COLUMN) {
                col = 0;
                row ++;
            }
        }
    }

    private void getCost(Node node) {
        // G cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;
        // H cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;
        // F cost
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {
        while (!goalReached && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            // Check current node
            currentNode.checked = true;
            openList.remove(currentNode);

            // Open the up node
            if (row - 1 >= 0) {
                openNode(node[col][row - 1]);
            }

            // Open the left node
            if (col - 1 >= 0) {
                openNode(node[col - 1][row]);
            }

            // Open the down node
            if (row + 1 < MAX_WORLD_ROW) {
                openNode(node[row][row + 1]);
            }

            // Open the right node
            if (col + 1 < MAX_WORLD_COLUMN) {
                openNode(node[col + 1][row]);
            }

            // Find the best node
            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for (int i = 0; i < openList.size(); i ++) {
                // Check if this node's f cost is better
                Node olNode = openList.get(i);
                if (olNode.fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = olNode.fCost;
                }
                // If f Cost is equal check G cost
                else if (olNode.fCost == bestNodefCost) {
                    if (olNode.gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            // If no node in open list, break the loop
            if (openList.size() == 0) break;

            // After the loop, openList[bestNodeIndex] is the next step (= currentNode)
            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
            step ++;
        }

        return goalReached;
    }

    private void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    private void trackThePath() {
        Node currentNode = goalNode;

        while (currentNode != startNode) {
            pathList.add(0, currentNode);
            currentNode = currentNode.parent;
        }
    }
}
