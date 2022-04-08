package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Pathfinder {
    Map map;
    int xLength;
    int yLength;
    Terrain[][] terrain;
    int[][] graph;
    public static ArrayList<Point> foundPath = new ArrayList<>();
    public Node[][] visitedNodes;

    public static int[][] Distance;
    public int vCount = 0;

    public Pathfinder(Map map) {
        this.map = map;
        this.xLength = map.getTiles()[0].length;
        this.yLength = map.getTiles().length;
        visitedNodes = new Node[yLength][xLength];
        for (int i = 0; i < yLength; i++) {
            for (int j = 0; j < xLength; j++) {
                visitedNodes[i][j] = new Node();
            }
        }
    }

    public Point BFS(Soldier start, String side) {
        Point src = start.getPosition();
        Point end = null;
        fieldToGraph(start);
        int vMax = 0;

        Distance = new int[yLength][xLength];
        for (int i = 0; i < yLength; i++) {
            for (int j = 0; j < xLength; j++) {
                Distance[i][j] = Integer.MAX_VALUE;
                if (graph[i][j] != -1)
                    vMax++;
            }
        }
        System.out.println(vMax);

        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        boolean[][] visited = new boolean[yLength][xLength];

        PriorityQueue<Point> path = new PriorityQueue<>(Comparator.comparingInt(o -> Distance[o.y][o.x]));

        path.add(new Point(src.x, src.y));

        Distance[src.y][src.x] = 0;

        while (vCount != vMax || path.isEmpty()) {
            Point pos = path.remove();

            if (!map.getTiles()[pos.y][pos.x].getEntities().isEmpty() && map.getTiles()[pos.y][pos.x].getEntities().get(0).getType().equals("Castle") && map.getTiles()[pos.y][pos.x].getEntities().get(0).getSide().equals(side)) {


                if (end == null || Distance[pos.y][pos.x] < Distance[end.y][end.x]) {
                    end = new Point(pos);
                }
            }

            if (visited[pos.y][pos.x])
                continue;

            visited[pos.y][pos.x] = true;
            vCount++;

            //foundPath.add(pos);

            for (int i = 0; i < 4; i++) {
                if ((pos.x + dx[i] >= 0 && pos.y + dy[i] >= 0 && pos.x + dx[i] < xLength && pos.y + dy[i] < yLength) && !visited[pos.y + dy[i]][pos.x + dx[i]] && graph[pos.y + dy[i]][pos.x + dx[i]] > 0) {
                    int newDist = Distance[pos.y][pos.x] + graph[pos.y + dy[i]][pos.x + dx[i]];

                    if (newDist < Distance[pos.y + dy[i]][pos.x + dx[i]])
                        Distance[pos.y + dy[i]][pos.x + dx[i]] = newDist;

                    path.add(new Point(pos.x + dx[i], pos.y + dy[i]));
                }
            }
        }
        return end;
    }

    public void genPath(Soldier start, String side) {
        Point end = BFS(start, side);
        Point cur;
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        foundPath.add(end);
        if (end != null) {
            while (Distance[end.y][end.x] != 0) {
                cur = new Point(end);
                for (int i = 0; i < 4; i++) {
                    if ((cur.x + dx[i] >= 0 && cur.y + dy[i] >= 0 && cur.x + dx[i] < xLength && cur.y + dy[i] < yLength) && Distance[cur.y + dy[i]][cur.x + dx[i]] < Distance[end.y][end.x]) {
                        end = new Point(cur.x + dx[i], cur.y + dy[i]);
                    }
                }
                foundPath.add(end);
            }
        }

    }

    public void fieldToGraph(Soldier s) {
        graph = new int[yLength][xLength];
        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                if (s.getTerrains().contains(map.getTiles()[y][x].getType()) &&
                        !(map.getTiles()[y][x].getEntities().size() > 0 &&
                                "Castle Barracks Barricade Sniper Shotgun".contains(map.getTiles()[y][x].getEntities().get(0).getType()))) {

                    graph[y][x] = map.getTiles()[y][x].getSpeedMod();
                    if (map.getTiles()[y][x].getType().equals("Desert"))
                        graph[y][x] = 100;
                } else if (!map.getTiles()[y][x].getEntities().isEmpty() && map.getTiles()[y][x].getEntities().get(0).getType().equals("Castle")) {
                    graph[y][x] = 1;
                } else
                    graph[y][x] = -1;
            }
        }

    }

    public static class Node {
        public Point pos;
        public Point prev;
        public int dist;

        public Node() {
            pos = null;
            prev = null;
            dist = Integer.MAX_VALUE;
        }

        public Node(Point pos, Point prev, int dist) {
            this.pos = pos;
            this.prev = prev;
            this.dist = dist;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "pos=" + pos +
                    ", prev=" + prev +
                    ", dist=" + dist +
                    '}';
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
