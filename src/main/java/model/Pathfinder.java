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
    }

    public ArrayList<Point> dijkstra(Soldier s, String side) {
        fieldToGraph(s);
        searchClosestCastlePiece(s, side);

        return null;

    }


    private void searchClosestCastlePiece(Soldier s, String side) {
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
    }

    public void fieldToGraph(Soldier s) {
        graph = new int[yLength][xLength];
        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                if (s.getTerrains().contains(map.getTiles()[y][x].getType()) &&
                        !(map.getTiles()[y][x].getEntities().size() > 0 &&
                                "Castle Barracks Barricade Sniper Shotgun".contains(map.getTiles()[y][x].getEntities().get(0).getType())))
                    graph[y][x] = map.getTiles()[y][x].getSpeedMod();
                else
                    graph[y][x] = -1;
            }
        }

    }
}
