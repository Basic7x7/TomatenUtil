package de.tomatengames.util.test;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.PathfinderUtil;
import de.tomatengames.util.PathfinderUtil.PositionMap;
import de.tomatengames.util.map.Int2HashMap;
import de.tomatengames.util.test.PathfinderTest.Maze;
import de.tomatengames.util.test.PathfinderTest.MazePoint;
import de.tomatengames.util.test.PathfinderTest.MazeWorld;

class PathfinderUtilVisualize {
	
	@Test
	void testFind() throws IOException {
		String dir = "testdata/pathfinderutil/";
		String infilename = "mini2";
		String outfilename = infilename + "_solved";
		String fileext = ".png";
		Maze maze = PathfinderTest.readMazeImageFile(dir + infilename + fileext);
		MazeWorld mazeworld = new MazeWorld(maze);
		PositionMap<MazePoint> positionMap = new PositionMap<>(mazeworld, 0.75f, 10);
		MazePoint startnode = new MazePoint(null, 0, maze.startx, maze.starty);
		long t1 = System.currentTimeMillis();
		MazePoint goalnode = PathfinderUtil.find(startnode, true, positionMap, mazeworld);
		long t2 = System.currentTimeMillis();
		System.out.println("Found path in " + (t2 - t1) + " ms");
		System.out.println("Seen " + positionMap.size() + " positions");
		
		Int2HashMap<Integer> colorsMap = new Int2HashMap<>();
		positionMap.forEach(
				p -> colorsMap.put(p.x, p.y, interpolateARGB(0xFF0000FF, 0xFFFF0000, p.getCost() / goalnode.getCost())));
		PathfinderUtil.traverseAll(goalnode, p -> p.previous.iterator(),
				p -> colorsMap.put(p.x, p.y, interpolateARGB(0xFFFFFF00, 0xFF00FF00, p.getCost() / goalnode.getCost())));
		
		PathfinderTest.writeMazeImageFile(maze, colorsMap, dir + outfilename + fileext);
		System.out.println("Image written");
	}
	
	public static int interpolateARGB(int color1, int color2, double t) {
		int a1 = (color1 >>> 24) & 0xFF;
		int r1 = (color1 >>> 16) & 0xFF;
		int g1 = (color1 >>> 8) & 0xFF;
		int b1 = color1 & 0xFF;
		int a2 = (color2 >>> 24) & 0xFF;
		int r2 = (color2 >>> 16) & 0xFF;
		int g2 = (color2 >>> 8) & 0xFF;
		int b2 = color2 & 0xFF;
		if (t < 0)
			t = 0;
		if (t > 1)
			t = 1;
		int a = (int)(a1 + t * (a2 - a1));
		int r = (int)(r1 + t * (r2 - r1));
		int g = (int)(g1 + t * (g2 - g1));
		int b = (int)(b1 + t * (b2 - b1));
		return (a << 24) | (r << 16) | (g << 8) | b;
	}
	
}
