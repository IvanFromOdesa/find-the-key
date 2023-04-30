package ai;

import lombok.Getter;

public class Node {
    Node parent;
    @Getter
    int col, row;
    int gCost, hCost, fCost;
    boolean solid, open, checked;

    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }
}
