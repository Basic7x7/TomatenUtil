package de.tomatengames.util.test;

import static org.junit.jupiter.api.Assertions.*;
import static de.tomatengames.util.ReflectionUtil.*;

import java.util.function.DoubleSupplier;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.PathfinderUtil.PathNode;

class PathfinderHeapTest {
	
	@Test
	void testHeap() {
		Object heap = construct("de.tomatengames.util.PathfinderUtil$LowestEstimateHeap", 3);
		double[] costs = new double[] {123, 6, 12, 34, 1, 743};
		TestNode[] nodes = new TestNode[costs.length];
		for (int i = 0; i < nodes.length; i++)
			nodes[i] = new TestNode(costs[i]);
		for (TestNode node : nodes)
			run(heap, "add", node);
		assertEquals(costs.length, (int)get(heap, "size"));
		run(heap, "decrease", nodes[5], new TestNode(3));
		run(heap, "decrease", nodes[3], nodes[3]);
		DoubleSupplier min = () -> ((TestNode)run(heap, "removeMin")).cost;
		assertEquals(1, min.getAsDouble());
		assertEquals(3, min.getAsDouble());
		assertEquals(6, min.getAsDouble());
		assertEquals(12, min.getAsDouble());
		assertEquals(34, min.getAsDouble());
		assertEquals(123, min.getAsDouble());
		assertEquals(0, (int)get(heap, "size"));
		PathNode[] array = get(heap, "array");
		for (int i = 0; i < array.length; i++)
			assertNull(array[i]);
	}
	
	static class TestNode extends PathNode {
		private double cost;
		public TestNode(double cost) {
			super(cost);
			this.cost = cost;
			set(this, "estimateFullCost", cost);
		}
	}
}
