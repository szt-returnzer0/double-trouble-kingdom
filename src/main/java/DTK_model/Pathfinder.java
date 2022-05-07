package DTK_model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Pathfinder {
    /**
     * The map that the pathfinder is working on
     */
    private static Map map = null;
    /**
     * The width of the map
     */
    private final int xLength;
    /**
     * The height of the map
     */
    private final int yLength;
    /**
     * The horizontal directions.
     */
    private final int[] horizontalDirections = {-1, 1, 0, 0};
    /**
     * The vertical directions.
     */
    private final int[] verticalDirections = {0, 0, -1, 1};
    /**
     * The graph of routes.
     */
    private int[][] graph;
    /**
     * The distance from the start to the end.
     */
    private int[][] distance;
    /**
     * The waypoint to factor in.
     */
    private Point wayPoint;

    /**
     * Constructs a Pathfinder instance.
     */
    public Pathfinder() {
        this.xLength = map.getTiles()[0].length;
        this.yLength = map.getTiles().length;
    }

    /**
     * Sets the map that the pathfinder is working on.
     *
     * @param map The map that the pathfinder is working on.
     */
    public static void setMap(Map map) {
        Pathfinder.map = map;
    }

    /**
     * Finds the shortest path from the start to the end.
     *
     * @param start The soldier to move.
     * @param side  The side of the end point.
     * @param b     The building to check when searching path at building phase.
     * @return The shortest path from the start to the end.
     */
    public Point Dijkstra(Soldier start, Sides side, Building b) {
        Point src = start.getPosition();
        Point end = null;
        fieldToGraph(start, b);

        int vCount = 0;
        int vMax = 0;
        distance = new int[yLength][xLength];
        for (int i = 0; i < yLength; i++) {
            for (int j = 0; j < xLength; j++) {
                distance[i][j] = Integer.MAX_VALUE;
                if (graph[i][j] != -1)
                    vMax++;
            }
        }

        boolean[][] visited = new boolean[yLength][xLength];
        PriorityQueue<Point> path = new PriorityQueue<>(Comparator.comparingInt(o -> distance[o.y][o.x]));
        path.add(new Point(src.x, src.y));
        distance[src.y][src.x] = 0;


        while (vCount != vMax || path.isEmpty()) {
            if (path.size() == 0) return null;

            Point pos = path.remove();

            if (side.equals(Sides.WAYPOINT)) {
                if (pos.x == wayPoint.x && pos.y == wayPoint.y) {
                    if (end == null || distance[pos.y][pos.x] < distance[end.y][end.x]) {
                        end = new Point(pos);
                    }
                }
            } else {
                if (!map.getTiles()[pos.y][pos.x].getEntities().isEmpty() && map.getTiles()[pos.y][pos.x].getEntities().get(0).getType().text.equals("Castle") &&
                        map.getTiles()[pos.y][pos.x].getEntities().get(0).getSide().equals(side)) {
                    if (end == null || distance[pos.y][pos.x] < distance[end.y][end.x]) {
                        end = new Point(pos);
                    }
                }
            }

            if (visited[pos.y][pos.x])
                continue;

            visited[pos.y][pos.x] = true;
            vCount++;

            for (int i = 0; i < 4; i++) {
                if ((pos.x + horizontalDirections[i] >= 0 && pos.y + verticalDirections[i] >= 0 && pos.x + horizontalDirections[i] < xLength && pos.y + verticalDirections[i] < yLength) &&
                        !visited[pos.y + verticalDirections[i]][pos.x + horizontalDirections[i]] && graph[pos.y + verticalDirections[i]][pos.x + horizontalDirections[i]] > 0) {
                    int newDist = distance[pos.y][pos.x] + graph[pos.y + verticalDirections[i]][pos.x + horizontalDirections[i]];

                    if (newDist < distance[pos.y + verticalDirections[i]][pos.x + horizontalDirections[i]])
                        distance[pos.y + verticalDirections[i]][pos.x + horizontalDirections[i]] = newDist;

                    path.add(new Point(pos.x + horizontalDirections[i], pos.y + verticalDirections[i]));
                }
            }
        }
        System.out.println(end);
        return end;
    }

    /**
     * Converts the map to a graph with routes.
     *
     * @param s The soldier that is moving.
     * @param b The building to check when searching path at building phase.
     */
    private void fieldToGraph(Soldier s, Building b) {
        System.out.println("Converting map to graph...");
        graph = new int[yLength][xLength];
        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                if (s.getTerrains().contains(map.getTiles()[y][x].getType()) &&
                        !(map.getTiles()[y][x].getEntities().size() > 0 &&
                                "Castle Barracks Barricade Sniper Shotgun".contains(map.getTiles()[y][x].getEntities().get(0).getType().text))) {
                    graph[y][x] = map.getTiles()[y][x].getSpeedMod();
                } else if (!map.getTiles()[y][x].getEntities().isEmpty() && map.getTiles()[y][x].getEntities().get(0).getType().text.equals("Castle")) {
                    graph[y][x] = 1;
                    if (map.getTiles()[y][x].getEntities().get(0).getSide().equals(s.getSide())) graph[y][x] = -1;
                } else
                    graph[y][x] = -1;
                if (b != null && x >= b.getPosition().x && x < b.getPosition().x + b.getSize().width && y >= b.getPosition().y && y < b.getPosition().y + b.getSize().height)
                    graph[y][x] = -1;
            }
        }
    }

    /**
     * Generates a path from start to end.
     *
     * @param start The soldier to move.
     * @param side  The side of the enemy.
     * @param b     The building to check when searching path at building phase.
     * @param mode  The return mode.
     * @return The path.
     */
    public ArrayList<Point> genPath(Soldier start, Sides side, Building b, String mode) {
        Point end;

        if (!start.getWayPoints().isEmpty() && start.getPosition().equals(start.getWayPoints().get(0).getLocation()))
            start.getWayPoints().remove(0);

        if (!start.getWayPoints().isEmpty()) {
            wayPoint = start.getWayPoints().get(0);
            end = Dijkstra(start, Sides.WAYPOINT, null);
        } else {
            end = Dijkstra(start, side, null);
        }

        ArrayList<Point> foundPath = new ArrayList<>();

        Point cur;
        Point dir = new Point(0, 0);
        if (end != null) {
            while (distance[end.y][end.x] != 0) {
                cur = new Point(end);
                for (int i = 0; i < 4; i++) {
                    if ((cur.x + horizontalDirections[i] >= 0 && cur.y + verticalDirections[i] >= 0 && cur.x + horizontalDirections[i] < xLength && cur.y + verticalDirections[i] < yLength) &&
                            distance[cur.y + verticalDirections[i]][cur.x + horizontalDirections[i]] < distance[end.y][end.x]) {
                        end = new Point(cur.x + horizontalDirections[i], cur.y + verticalDirections[i]);
                        dir = new Point(horizontalDirections[i] * -1, verticalDirections[i] * -1);
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
}
