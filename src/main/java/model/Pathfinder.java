package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Pathfinder {
    Map map;
    int xLength;
    int yLength;
    Terrain[][] terrain;
    int[][] graph;
    public static ArrayList<Point> foundPath = new ArrayList<>();
    public Node[][] visitedNodes;

    public Pathfinder(Map map) {
        this.map = map;
        this.xLength = map.getTiles()[0].length;
        this.yLength = map.getTiles().length;
        visitedNodes = new Node[yLength][xLength];
        for (int i = 0; i < yLength - 1; i++) {
            for (int j = 0; j < xLength - 1; j++) {
                visitedNodes[i][j] = new Node();
            }
        }
    }

    public Node BFS(Soldier start, String side) {
        Point src = start.getPosition();
        Point end = null;

        fieldToGraph(start);

        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        boolean[][] visited = new boolean[yLength][xLength];
        visited[src.y][src.x] = true;
        Queue<Point> path = new LinkedList<>();
        path.add(new Point(src.x, src.y));
        visitedNodes[0][0] = (new Node(src, null));
        while (!path.isEmpty()) {
            Point pos = path.remove();

            if (!map.getTiles()[pos.y][pos.x].getEntities().isEmpty() && map.getTiles()[pos.y][pos.x].getEntities().get(0).getType().equals("Castle") && map.getTiles()[pos.y][pos.x].getEntities().get(0).getSide().equals(side)) {

                return visitedNodes[pos.y][pos.x];
            }

            for (int i = 0; i < 4; i++) {
                //System.out.println(new Point(pos.x+dx[i],pos.y+dy[i]));
                if ((pos.x + dx[i] >= 0 && pos.y + dy[i] >= 0 && pos.x + dx[i] < xLength && pos.y + dy[i] < yLength) && !visited[pos.y + dy[i]][pos.x + dx[i]] && graph[pos.y + dy[i]][pos.x + dx[i]] > 0) {
                    path.add(new Point(pos.x + dx[i], pos.y + dy[i]));
                    visitedNodes[pos.y + dy[i]][pos.x + dx[i]] = new Node(new Point(pos.x + dx[i], pos.y + dy[i]), pos);
                    System.out.println(new Point(pos.x + dx[i], pos.y + dy[i]));
                    visited[pos.y + dy[i]][pos.x + dx[i]] = true;
                }
            }
        }
        return null;
    }

    public void genPath(Soldier start, String side) {
        Node n = BFS(start, side);
        while (n != null) {
            foundPath.add(n.prev);
            n = visitedNodes[n.prev.y][n.prev.x];
        }

    }

    public void fieldToGraph(Soldier s) {
        graph = new int[yLength][xLength];
        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                if (s.getTerrains().contains(map.getTiles()[y][x].getType()) &&
                        !(map.getTiles()[y][x].getEntities().size() > 0 &&
                                "Castle Barracks Barricade Sniper Shotgun".contains(map.getTiles()[y][x].getEntities().get(0).getType())))
                    graph[y][x] = map.getTiles()[y][x].getSpeedMod();
                else if (!map.getTiles()[y][x].getEntities().isEmpty() && map.getTiles()[y][x].getEntities().get(0).getType().equals("Castle")) {
                    graph[y][x] = 99;
                } else
                    graph[y][x] = -1;
            }
        }

    }

    public static class Node {
        public Point pos;
        public Point prev;

        public Node() {
            pos = null;
            prev = null;
        }

        public Node(Point pos, Point prev) {
            this.pos = pos;
            this.prev = prev;
        }
    }

    /*private void searchClosestCastlePiece(Soldier s, String side) {
        int x = s.getPosition().x;
        int y = s.getPosition().y;
        int minDist = Integer.MAX_VALUE;
        int minX = -1;
        int minY = -1;
        for (int i = 0; i < yLength; i++) {
            for (int j = 0; j < xLength; j++) {
                if (map.getTiles()[i][j].getEntities().size() > 0 &&
                        map.getTiles()[i][j].getEntities().get(0).getType().equals("Castle") && map.getTiles()[i][j].getEntities().get(0).getSide().equals(side)) {
                    int dist = Math.abs(i - y) + Math.abs(j - x);
                    if (dist < minDist) {
                        minDist = dist;
                        minX = j;
                        minY = i;
                    }
                }
            }
        }
        graph[minY][minX] = 0;
    }

    public static int minDistance(int[] distance, int n) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < n; i++) {
            if (distance[i] < min && distance[i] != Integer.MAX_VALUE) {
                min = distance[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    public ArrayList<Point> listToPath(int[] list) {
        ArrayList<Point> path = new ArrayList<>();
        for (int i = 0; i < list.length; ++i) {
            int posx = i % yLength;
            int posy = (i - i % yLength) / yLength;
            if (list[i] == 0) {
                path.add(new Point(posx, posy));
            }

        }
        return path;
    }*/


}
