package model;

import java.awt.*;
import java.util.ArrayList;

public class Pathfinder {
    Map map;
    int xLength;
    int yLength;
    Terrain[][] terrain;
    int[][] graph;

    public Pathfinder(Map map) {
        this.map = map;
        this.xLength = map.getTiles()[0].length;
        this.yLength = map.getTiles().length;

        this.terrain = this.map.getTiles();
    }

    public static int[] dijkstra(int[][] graph, int start, int end) {
        int n = graph.length;
        int[] distance = new int[n];
        int[] path = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
            path[i] = -1;
        }
        distance[start] = 0;
        for (int i = 0; i < n - 1; i++) {
            int u = minDistance(distance, n);
            for (int v = 0; v < n; v++) {
                if (graph[u][v] != 0 && distance[u] != Integer.MAX_VALUE
                        && distance[u] + graph[u][v] < distance[v]) {
                    distance[v] = distance[u] + graph[u][v];
                    path[v] = u;
                }
            }
        }
        return path;
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
    }

    public int[][] fieldToGraph(Soldier s) {
        graph = new int[yLength][xLength];
        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                if (s.getTerrains().contains(map.getTiles()[y][x].getType()) && !(map.getTiles()[y][x].getEntities().size() > 0 && "Castle Barracks Barricade Sniper Shotgun".contains(map.getTiles()[y][x].getEntities().get(0).getType())))
                    graph[y][x] = map.getTiles()[y][x].getSpeedMod();
                else
                    graph[y][x] = -1;
            }
        }

        return graph;
    }
}
