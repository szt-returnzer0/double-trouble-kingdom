package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Pathfinder {
    private static Map map = null;
    private final int xLength;
    private final int yLength;
    private final int[] dx = {-1, 1, 0, 0};
    private final int[] dy = {0, 0, -1, 1};
    private int[][] graph;
    private int[][] Distance;

    public Pathfinder() {
        this.xLength = map.getTiles()[0].length;
        this.yLength = map.getTiles().length;
    }

    public static void setMap(Map map) {
        Pathfinder.map = map;
    }

    public Point Dijkstra(Soldier start, String side, Building b) {
        Point src = start.getPosition();
        Point end = null;
        fieldToGraph(start, b);

        int vCount = 0;
        int vMax = 0;
        Distance = new int[yLength][xLength];
        for (int i = 0; i < yLength; i++) {
            for (int j = 0; j < xLength; j++) {
                Distance[i][j] = Integer.MAX_VALUE;
                if (graph[i][j] != -1)
                    vMax++;
            }
        }

        boolean[][] visited = new boolean[yLength][xLength];
        PriorityQueue<Point> path = new PriorityQueue<>(Comparator.comparingInt(o -> Distance[o.y][o.x]));
        path.add(new Point(src.x, src.y));
        Distance[src.y][src.x] = 0;


        while (vCount != vMax || path.isEmpty()) {
            if (path.size() == 0) return null;

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

    public void fieldToGraph(Soldier s, Building b) {
        graph = new int[yLength][xLength];
        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                if (s.getTerrains().contains(map.getTiles()[y][x].getType()) &&
                        !(map.getTiles()[y][x].getEntities().size() > 0 &&
                                "Castle Barracks Barricade Sniper Shotgun".contains(map.getTiles()[y][x].getEntities().get(0).getType()))) {
                    graph[y][x] = map.getTiles()[y][x].getSpeedMod();
                } else if (!map.getTiles()[y][x].getEntities().isEmpty() && map.getTiles()[y][x].getEntities().get(0).getType().equals("Castle")) {
                    graph[y][x] = 1;
                    if (map.getTiles()[y][x].getEntities().get(0).getSide().equals(s.getSide())) graph[y][x] = -1;
                } else
                    graph[y][x] = -1;
                if (b != null && x >= b.getPosition().x && x < b.getPosition().x + b.getSize().width && y >= b.getPosition().y && y < b.getPosition().y + b.getSize().height)
                    graph[y][x] = -1;
            }
        }
    }

    /*public Point Dijkstra2(Soldier start, String side, Building b) {
        Point src = start.getPosition();
        Point end = null;
        fieldToGraph2(start,b);

        int vCount = 0;
        int vMax = 0;
        Distance = new int[yLength][xLength];
        for (int i = 0; i < yLength; i++) {
            for (int j = 0; j < xLength; j++) {
                Distance[i][j] = Integer.MAX_VALUE;
                if (graph[i][j] != -1)
                    vMax++;
            }
        }

        boolean[][] visited = new boolean[yLength][xLength];
        PriorityQueue<Point> path = new PriorityQueue<>(Comparator.comparingInt(o -> Distance[o.y][o.x]));
        path.add(new Point(src.x, src.y));
        Distance[src.y][src.x] = 0;


        while (vCount != vMax || path.isEmpty()) {
            if (path.size() == 0) return null;

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
    }*/

    /*public void fieldToGraph2(Soldier s, Building b) {
        graph = new int[yLength][xLength];
        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                if (s.getTerrains().contains(map.getTiles()[y][x].getType()) &&
                        !(map.getTiles()[y][x].getEntities().size() > 0 &&
                                "Castle Barracks Barricade Sniper Shotgun".contains(map.getTiles()[y][x].getEntities().get(0).getType()))) {
                    graph[y][x] = map.getTiles()[y][x].getSpeedMod();
                } else if (!map.getTiles()[y][x].getEntities().isEmpty() && map.getTiles()[y][x].getEntities().get(0).getType().equals("Castle")) {
                    graph[y][x] = 1;
                    if (map.getTiles()[y][x].getEntities().get(0).getSide().equals(s.getSide())) graph[y][x] = -1;
                } else
                    graph[y][x] = -1;
                if(x>=b.getPosition().x && x<b.getPosition().x+b.getSize().width && y>=b.getPosition().y && y<b.getPosition().y+b.getSize().height){
                    graph[y][x] = -1;
                }
            }
        }
    }*/

    public ArrayList<Point> genPath(Soldier start, String side, Building b, String mode) {
        Point end = Dijkstra(start, side, b);
        Point cur;

        ArrayList<Point> foundPath = new ArrayList<>();

        //foundPath.add(end);
        Point dir = new Point(0, 0);
        if (end != null) {
            while (Distance[end.y][end.x] != 0) {
                cur = new Point(end);
                for (int i = 0; i < 4; i++) {
                    if ((cur.x + dx[i] >= 0 && cur.y + dy[i] >= 0 && cur.x + dx[i] < xLength && cur.y + dy[i] < yLength) && Distance[cur.y + dy[i]][cur.x + dx[i]] < Distance[end.y][end.x]) {
                        end = new Point(cur.x + dx[i], cur.y + dy[i]);
                        dir = new Point(dx[i] * -1, dy[i] * -1);
                    }
                }
                if (mode.equals("rel"))
                    foundPath.add(dir);
                else
                    foundPath.add(end);
            }
        }
        Collections.reverse(foundPath);
        return foundPath;
    }

    /*public ArrayList<Point> genPathAbs(Soldier start, String side,Building b) {
        Point end = Dijkstra2(start, side,b);
        Point cur;

        ArrayList<Point> foundPath = new ArrayList<>();

        //foundPath.add(end);
        Point dir = new Point(0, 0);
        if (end != null) {
            while (Distance[end.y][end.x] != 0) {
                cur = new Point(end);
                for (int i = 0; i < 4; i++) {
                    if ((cur.x + dx[i] >= 0 && cur.y + dy[i] >= 0 && cur.x + dx[i] < xLength && cur.y + dy[i] < yLength) && Distance[cur.y + dy[i]][cur.x + dx[i]] < Distance[end.y][end.x]) {
                        end = new Point(cur.x + dx[i], cur.y + dy[i]);
                        dir = new Point(dx[i] * -1, dy[i] * -1);
                    }
                }
                foundPath.add(end);
            }
        }
        Collections.reverse(foundPath);
        return foundPath;
    }*/
}
