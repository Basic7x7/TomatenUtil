package de.tomatengames.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.PathfinderUtil;
import de.tomatengames.util.map.Int2HashMap;

class PathfinderTest {
	
	@Test
	void testFind() throws IOException {
		checkPathCost("line1.png", 50);
		checkPathCost("line2.png", 58);
		checkPathCost("diagonal1.png", 98);
		checkPathCost("diagonal2.png", 122);
		checkPathCost("choosediagonal.png", 104);
		checkPathCost("chooseline.png", 120);
		checkPathCost("mini1.png", 180);
		checkPathCost("mini2.png", 300);
	}
	
	public static void checkPathCost(String filename, double cost) throws IOException {
		Maze maze = readMazeImageFile("testdata/pathfinderutil/" + filename);
		MazePoint goal = PathfinderUtil.find(new MazePoint(null, 0, maze.startx, maze.starty), new MazeWorld(maze));
		assertEquals(cost, goal.getCost());
	}
	
	public static Maze readMazeImageFile(String file) throws IOException {
		BufferedImage img = ImageIO.read(new File(file));
		Maze maze = new Maze();
		maze.width = img.getWidth();
		maze.height = img.getHeight();
		maze.passable = new boolean[maze.width][maze.height];
		for (int x = 0; x < maze.width; x++) {
			for (int y = 0; y < maze.height; y++) {
				int rgb = img.getRGB(x, y);
				int r = (rgb >>> 16) & 0xFF;
				int g = (rgb >>> 8) & 0xFF;
				int b = rgb & 0xFF;
				maze.passable[x][y] = r > 0 || g > 0 || b > 0;
				if (b > r && b > g) {
					maze.startx = x;
					maze.starty = y;
				}
				if (r > g && r > b) {
					maze.goalx = x;
					maze.goaly = y;
				}
			}
		}
		return maze;
	}
	public static void writeMazeImageFile(Maze maze, Int2HashMap<Integer> specialColorsMap, String file) throws IOException {
		BufferedImage img = new BufferedImage(maze.width, maze.height, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < maze.width; x++) {
			for (int y = 0; y < maze.height; y++) {
				int rgb = maze.passable(x, y) ? 0xFFFFFFFF : 0xFF000000;
				Integer specialColor = specialColorsMap.get(x, y);
				if (specialColor != null)
					rgb = specialColor;
				img.setRGB(x, y, rgb);
			}
		}
		ImageIO.write(img, "PNG", new File(file));
	}
	
	public static class Maze {
		public int startx, starty, goalx, goaly;
		public int width, height;
		public boolean[][] passable;
		
		public boolean passable(int x, int y) {
			if (x < 0 || y < 0 || x >= width || y >= height)
				return false;
			return passable[x][y];
		}
	}
	
	public static class MazeWorld implements PathfinderUtil.World<MazePoint> {
		private final Maze maze;
		public MazeWorld(Maze maze) {
			this.maze = maze;
		}
		@Override
		public int positionHash(MazePoint p) {
			return p.x * maze.height + p.y;
		}
		@Override
		public boolean positionEqual(MazePoint p1, MazePoint p2) {
			return p1.x == p2.x && p1.y == p2.y;
		}
		private boolean addPassableNeighbor(MazePoint origin, int x, int y, double cost, Collection<MazePoint> neighbors) {
			x += origin.x;
			y += origin.y;
			if (!maze.passable(x, y))
				return false;
			neighbors.add(new MazePoint(origin, cost, x, y));
			return true;
		}
		@Override
		public void insertNeighbors(MazePoint p, Collection<MazePoint> neighbors) {
			boolean p10 = addPassableNeighbor(p, 1, 0, 10, neighbors);
			boolean p01 = addPassableNeighbor(p, 0, 1, 10, neighbors);
			boolean pm10 = addPassableNeighbor(p, -1, 0, 10, neighbors);
			boolean p0m1 = addPassableNeighbor(p, 0, -1, 10, neighbors);
			if (p10 && p01)
				addPassableNeighbor(p, 1, 1, 14, neighbors);
			if (pm10 && p01)
				addPassableNeighbor(p, -1, 1, 14, neighbors);
			if (p10 && p0m1)
				addPassableNeighbor(p, 1, -1, 14, neighbors);
			if (pm10 && p0m1)
				addPassableNeighbor(p, -1, -1, 14, neighbors);
		}
		@Override
		public double estimateRemainingCost(MazePoint p) {
			int dx = Math.abs(maze.goalx - p.x);
			int dy = Math.abs(maze.goaly - p.y);
			int dd = Math.min(dx, dy);
			return (dx + dy - dd * 2) * 10 + dd * 14;
		}
		@Override
		public boolean isGoal(MazePoint p) {
			return p.x == maze.goalx && p.y == maze.goaly;
		}
		@Override
		public boolean preferOrigin(MazePoint present, MazePoint proposal) {
			assertEquals(1, proposal.previous.size());
			assertFalse(present.previous.contains(proposal.previous.get(0)));
			present.previous.add(proposal.previous.get(0));
			assertTrue(present.previous.size() <= 8);
			return false;
		}
	}
	
	public static class MazePoint extends PathfinderUtil.PathNode {
		public final ArrayList<MazePoint> previous;
		public final int x, y;
		
		public MazePoint(MazePoint previous, double stepCost, int x, int y) {
			super(stepCost);
			this.previous = new ArrayList<>();
			if (previous != null)
				this.previous.add(previous);
			this.x = x;
			this.y = y;
		}
		
		public double getCost() {
			return super.cost;
		}
		
		@Override
		public String toString() {
			return "{" + x + "," + y + "}";
		}
		
	}
	
}
