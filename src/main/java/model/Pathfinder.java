package model;

import java.util.ArrayList;

public class Pathfinder {
    Map map;
    int x;
    int y;
    Terrain[][] terrain;
    int[][] graph;

    public Pathfinder(Map map) {
        this.map = map;
        this.x = map.getTiles()[0].length;
        this.y = map.getTiles().length;
        this.terrain = this.map.getTiles();
    }

    public static int[] dijkstra(int[][] graph, int source, int destination) {
        int n = graph.length;
        int[] distance = new int[n];
        int[] previous = new int[n];
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
            previous[i] = -1;
            visited[i] = false;
        }
        distance[source] = 0;
        previous[source] = source;
        for (int i = 0; i < n - 1; i++) {
            int u = minDistance(distance, visited);
            visited[u] = true;
            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] != 0 && distance[u] != Integer.MAX_VALUE && distance[u] + graph[u][v] < distance[v]) {
                    distance[v] = distance[u] + graph[u][v];
                    previous[v] = u;
                }
            }
        }
        return distance;
    }

    private static int minDistance(int[] distance, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < distance.length; i++) {
            if (!visited[i] && distance[i] <= min) {
                min = distance[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    //BFS
    public static int[] bfs(int[][] graph, int source, int destination) {
        int n = graph.length;
        int[] distance = new int[n];
        int[] previous = new int[n];
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
            previous[i] = -1;
            visited[i] = false;
        }
        distance[source] = 0;
        previous[source] = source;
        for (int i = 0; i < n - 1; i++) {
            int u = minDistance(distance, visited);
            visited[u] = true;
            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] != 0 && distance[u] != Integer.MAX_VALUE && distance[u] + graph[u][v] < distance[v]) {
                    distance[v] = distance[u] + graph[u][v];
                    previous[v] = u;

                }
            }
        }
        return distance;
    }

    public int[][] fieldToGraph(Soldier s) {
        ArrayList<String> goodTiles = s.getTerrains();
        int pcs = x * y;
        /*
        P,P,M,D,P
        P,P,M,D,P
        P,P,M,D,P
        P,P,M,D,P
        P,P,M,D,P
        */
        int a;
        graph = new int[pcs][pcs];
        for (int i = 0; i < y; ++i) {
            for (int j = 0; j < x; ++j) {
                //Up
                if (j != 0) {
                    int weight = terrain[i][j - 1].getSpeedMod();
                    if (!terrain[i][j - 1].getEntities().contains(Building.class) && goodTiles.contains(terrain[i][j - 1].getType()))
                        graph[j * y + i][(j - 1) * y + i] = weight;
                }
                //Right
                if (i != y - 1) {
                    int weight = terrain[i + 1][j].getSpeedMod();
                    if (!terrain[i + 1][j].getEntities().contains(Building.class) && goodTiles.contains(terrain[i + 1][j].getType()))
                        graph[i * y + j][i * y + j + 1] = weight;
                }
                //Down
                if (j != x - 1) {
                    int weight = terrain[i][j + 1].getSpeedMod();
                    if (!terrain[i][j + 1].getEntities().contains(Building.class) && goodTiles.contains(terrain[i][j + 1].getType()))
                        graph[j * y + i][(j + 1) * y + i] = weight;
                }
                //Left
                if (i != 0) {
                    int weight = terrain[i - 1][j].getSpeedMod();
                    if (!terrain[i - 1][j].getEntities().contains(Building.class) && goodTiles.contains(terrain[i - 1][j].getType()))
                        graph[j * y + i][j * y + (i - 1)] = weight;
                }
            }
        }
        return graph;
    }
}
