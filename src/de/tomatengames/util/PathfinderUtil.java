package de.tomatengames.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Provides methods to find optimal paths on graphs using the A* algorithm.
 * 
 * @author LukasE7x7
 * @version 2024-01-14
 * @since 1.5
 */
public class PathfinderUtil {
	
	private PathfinderUtil() {
	}
	
	/**
	 * Finds a goal from the provided startpoint, using the provided {@link World} to traverse the environment.
	 * @param <N> The specific type of PathNode used
	 * @param start The startpoint
	 * @param world The {@link World}
	 * @return A goal PathNode
	 * @see PathNode
	 * @see World
	 */
	public static <N extends PathNode> N find(N start, World<N> world) {
		return find(start, false, new PositionMap<>(world, 0.75f, 16), world);
	}
	
	/**
	 * Finds a goal from the provided startpoint, using the provided {@link World} to traverse the environment.
	 * Set the parameter <code>all</code> to true to consider all optimal paths, enabling {@link World#preferOrigin(PathNode, PathNode)}
	 * to choose from all the optimal paths.
	 * The provided {@link PositionMap} will be used to map any position to the cheapest PathNode
	 * that was yet found representing that position. An empty map should be provided here, but its contents may be interesting
	 * after this method returns (see {@link World#preferOrigin(PathNode, PathNode)}).
	 * @param <N> The specific type of PathNode used
	 * @param start The startpoint
	 * @param all whether to visit all optimal paths
	 * @param positionMap The {@link PositionMap}
	 * @param world The {@link World}
	 * @return A goal PathNode
	 */
	@SuppressWarnings("unchecked")
	public static <N extends PathNode> N find(N start, boolean all, PositionMap<N> positionMap, World<N> world) {
		LowestEstimateHeap open = new LowestEstimateHeap(10);
		positionMap.put(start);
		start.estimateFullCost = start.cost + world.estimateRemainingCost(start);
		open.add(start);
		ArrayList<N> neighborsBuf = new ArrayList<>();
		N goal = null;
		while (open.size > 0) {
			N node = (N)open.removeMin();
			if (all) {
				if (goal == null) {
					if (world.isGoal(node))
						goal = node;
				} else if (node.estimateFullCost > goal.estimateFullCost) {
					return goal;
				}
			} else if (world.isGoal(node)) {
				return node;
			}
			world.insertNeighbors(node, neighborsBuf);
			for (int size = neighborsBuf.size(), i = 0; i < size; i++) {
				N neighbor = neighborsBuf.get(i);
				N knownNeighbor = positionMap.get(neighbor);
				double neighborcost = node.cost + neighbor.cost;
				if (knownNeighbor == null || neighborcost < knownNeighbor.cost ||
						(neighborcost == knownNeighbor.cost && world.preferOrigin(knownNeighbor, neighbor))) {
					neighbor.cost = neighborcost;
					positionMap.put(neighbor);
					neighbor.estimateFullCost = neighborcost + world.estimateRemainingCost(neighbor);
					if (knownNeighbor == null || !open.contains(knownNeighbor))
						open.add(neighbor);
					else
						open.decrease(knownNeighbor, neighbor);
				}
			}
			neighborsBuf.clear();
		}
		return goal;
	}
	
	/**
	 * Utility to list the PathNodes from the startpoint to the provided PathNode.
	 * Typically, a PathNode knows their original PathNode (previous), which this method obtains using the provided function.
	 * @param <N> The specific type of PathNode
	 * @param node The furthest PathNode, typically the found goal
	 * @param previous The function used to obtain a PathNodes previous PathNode,
	 * or null if the list is complete, which typically means that the input PathNode is the startpoint.
	 * @return A list from the startpoint (first) up to the provided PathNode (last)
	 */
	public static <N> List<N> listPath(N node, Function<N, N> previous) {
		LinkedList<N> list = new LinkedList<>();
		while (node != null) {
			list.addFirst(node);
			node = previous.apply(node);
		}
		return list;
	}
	
	/**
	 * Utility to recursively find all PathNodes indirectly connected to the provided node.
	 * This may be useful for collecting all PathNodes involved in any path from start to goal when multiple possible paths were recorded.
	 * @param <N> The specific type of PathNode
	 * @param node The initial PathNode to start searching from
	 * @param neighbors The function providing the PathNodes directly connected to the input PathNode
	 * @param output The Consumer that all found PathNodes will be put into
	 */
	public static <N> void traverseAll(N node, Function<N, Iterator<N>> neighbors, Consumer<N> output) {
		HashSet<N> seen = new HashSet<>();
		Stack<N> stack = new Stack<>();
		seen.add(node);
		stack.add(node);
		while (!stack.isEmpty()) {
			N n = stack.pop();
			output.accept(n);
			Iterator<N> neighborsIt = neighbors.apply(n);
			while (neighborsIt.hasNext()) {
				N neighbor = neighborsIt.next();
				if (seen.add(neighbor))
					stack.add(neighbor);
			}
		}
	}
	
	/**
	 * Provides several functions used by the <code>find</code> methods.
	 * It should represent the graph and the goal to find.
	 * @param <N> The specific type of PathNode
	 */
	public static interface World<N extends PathNode> {
		/**
		 * Calculates a hash of the position (node in a graph) represented by the provided node.
		 * Similar to {@link Object#hashCode()}, but supposed to only consider the represented position.
		 * @param node The node containing the position to hash
		 * @return The hash
		 */
		int positionHash(N node);
		
		/**
		 * Determines whether the provided nodes represent the same position (node in a graph).
		 * Similar to {@link Object#equals(Object)}, but supposed to only consider the represented position.
		 * @param node1 A node
		 * @param node2 Another node
		 * @return whether the provided nodes represent the same position
		 */
		boolean positionEqual(N node1, N node2);
		
		/**
		 * Adds the neighbor nodes of the provided node to the provided Collection.
		 * The resulting PathNodes are typically linked to the input node as their "previous" PathNode.
		 * @param node The original node
		 * @param neighbors The Collection to put the original nodes neighbors into
		 */
		void insertNeighbors(N node, Collection<N> neighbors);
		
		/**
		 * Estimates the remaining cost to reach the goal from the provided node. This is the <i>heuristic function</i>.
		 * @param node The node to estimate the remaining cost from
		 * @return An estimate of the remaining cost to reach the goal
		 */
		double estimateRemainingCost(N node);
		
		/**
		 * Determines whether the provided node represents a goal position.
		 * This typically compares the provided node to one specific goal position.
		 * @param node The node to test
		 * @return whether the provided node represents a goal position
		 */
		boolean isGoal(N node);
		
		/**
		 * Chooses between two nodes with equal cost representing the same position.
		 * When this is implemented, visiting all optimal paths is typically desired
		 * by setting all=true in {@link PathfinderUtil#find(PathNode, boolean, PositionMap, World)}.
		 * This can also be used to record all available choices by merging the proposed node into the present node and returning false.
		 * To simply find one arbitrary optimal path, you do not need to implement or care about this.
		 * @param present The present node to be kept when false is returned
		 * @param proposal The proposed node to replace the present node with when true is returned
		 * @return false if the present node is to be kept, true if the proposed node should be used instead. Defaults to false.
		 * Note that when choosing to replace the present node (by returning true),
		 * PathNodes are no longer guaranteed to be created from their preferred origin PathNodes,
		 * possibly requiring you to retrieve every replaced node from
		 * the final {@link PositionMap} after {@link PathfinderUtil#find(PathNode, boolean, PositionMap, World)}
		 * to really find a path adhering to the defined preference.
		 */
		default boolean preferOrigin(N present, N proposal) {
			return false;
		}
	}
	
	/**
	 * A PathNode should represent a position (node in a graph) combined with the way to get there (edges) from the startpoint.
	 * Specifically, {@link World#positionEqual(PathNode, PathNode)} needs implementation and typically the taken path is kept
	 * to retrieve the resulting path rather than solely the goal position from the <code>find</code> methods.
	 * This class contains some private attributes used in the <code>find</code> methods, so its instances should be single-use.
	 * To find a path on a 2D grid, you would add attributes like <code>int x, y;</code> and <code>PathNode previous;</code>.
	 */
	public static abstract class PathNode {
		
		/**
		 * Initially the cost of the single step that separates this PathNode from its original PathNode.
		 * If this PathNode is not immediately discarded, the cost is updated to the total cost from the startpoint to this PathNode.
		 */
		protected double cost;
		/**
		 * Is set to the estimated total cost from the startpoint to the goal going over this PathNode.
		 * That is the cost from the startpoint to this PathNode + {@link World#estimateRemainingCost(PathNode)}.
		 */
		protected double estimateFullCost;
		
		int heapPosition;
		PathNode bucketNext;
		
		/**
		 * Creates a new PathNode with the provided step cost.
		 * @param cost the cost of reaching this PathNode from its original PathNode
		 */
		protected PathNode(double cost) {
			this.cost = cost;
		}
		
	}
	
	/**
	 * A Map-like structure specifically for {@link PathNode}s.
	 * They are compared using {@link World#positionHash(PathNode)} and {@link World#positionEqual(PathNode, PathNode)}.
	 * Rather than wrapping with additional objects, the contained {@link PathNode}s are used for linking directly,
	 * meaning that a {@link PathNode} must not be used in multiple {@link PositionMap}s.
	 * @param <N> The specific type of {@link PathNode} used
	 */
	public static class PositionMap<N extends PathNode> {
		private final World<PathNode> world;
		private final float loadfactor;
		private PathNode[] buckets;
		private int size;
		private int expansionSizeLimit;
		
		@SuppressWarnings("unchecked")
		public PositionMap(World<N> world, float loadfactor, int initialCapacity) {
			this.world = (World<PathNode>)world;
			this.loadfactor = loadfactor;
			int capacity = Integer.highestOneBit(initialCapacity);
			if (capacity < initialCapacity)
				capacity <<= 1;
			this.buckets = new PathNode[capacity];
			this.size = 0;
			this.expansionSizeLimit = (int)(capacity * this.loadfactor);
		}
		
		public int size() {
			return size;
		}
		@SuppressWarnings("unchecked")
		public void forEach(Consumer<N> out) {
			PathNode[] buckets = this.buckets;
			for (int i = 0; i < buckets.length; i++) {
				PathNode bucketNode = buckets[i];
				while (bucketNode != null) {
					out.accept((N)bucketNode);
					bucketNode = bucketNode.bucketNext;
				}
			}
		}
		public void clear() {
			PathNode[] buckets = this.buckets;
			for (int i = 0; i < buckets.length; i++)
				buckets[i] = null;
			this.size = 0;
		}
		public void put(N node) {
			World<PathNode> world = this.world;
			PathNode[] buckets = this.buckets;
			if (this.size >= this.expansionSizeLimit) {
				int newcapacity = buckets.length << 1;
				PathNode[] newbuckets = new PathNode[newcapacity];
				for (int i = 0; i < buckets.length; i++) {
					PathNode bucketNode = buckets[i];
					while (bucketNode != null) {
						PathNode bucketNext = bucketNode.bucketNext;
						putDirect(world, bucketNode, newbuckets);
						bucketNode = bucketNext;
					}
				}
				this.buckets = buckets = newbuckets;
				this.expansionSizeLimit = (int)(newcapacity * this.loadfactor);
			}
			if (putDirect(world, node, buckets) == null)
				this.size++;
		}
		private static PathNode putDirect(World<PathNode> world, PathNode node, PathNode[] buckets) {
			int idx = world.positionHash(node) & (buckets.length - 1);
			PathNode bucketNode = buckets[idx];
			if (bucketNode == null) {
				buckets[idx] = node;
				node.bucketNext = null;
				return null;
			}
			if (world.positionEqual(node, bucketNode)) {
				buckets[idx] = node;
				node.bucketNext = bucketNode.bucketNext;
				return bucketNode;
			}
			while (true) {
				PathNode bucketNext = bucketNode.bucketNext;
				if (bucketNext == null) {
					bucketNode.bucketNext = node;
					node.bucketNext = null;
					return null;
				}
				if (world.positionEqual(node, bucketNext)) {
					bucketNode.bucketNext = node;
					node.bucketNext = bucketNext.bucketNext;
					return bucketNext;
				}
				bucketNode = bucketNext;
			}
		}
		@SuppressWarnings("unchecked")
		public N get(N node) {
			World<PathNode> world = this.world;
			PathNode[] buckets = this.buckets;
			PathNode bucketNode = buckets[world.positionHash(node) & (buckets.length - 1)];
			while (bucketNode != null) {
				if (world.positionEqual(node, bucketNode))
					return (N)bucketNode;
				bucketNode = bucketNode.bucketNext;
			}
			return null;
		}
	}
	
	/**
	 * A Min-Heap ordered by {@link PathNode#estimateFullCost}.
	 * The position of PathNodes in this heap is recorded in the individual PathNodes,
	 * meaning a PathNode must not be used in multiple {@link LowestEstimateHeap}s.
	 * This mainly enables the {@link LowestEstimateHeap#decrease(PathNode, PathNode)} method to efficiently
	 * replace a node with another node of lower {@link PathNode#estimateFullCost}.
	 */
	private static class LowestEstimateHeap {
		private PathNode[] array;
		private int size;
		
		public LowestEstimateHeap(int initialCapacity) {
			this.array = new PathNode[initialCapacity];
			this.size = 0;
		}
		
		public boolean contains(PathNode element) {
			return element.heapPosition >= 0;
		}
		public void add(PathNode element) {
			int pos = this.size++;
			if (this.array.length == pos)
				System.arraycopy(this.array, 0, this.array = new PathNode[pos * 2], 0, pos);
			this.moveUpFrom(pos, element);
		}
		private void moveUpFrom(int pos, PathNode element) {
			PathNode[] array = this.array;
			while (true) {
				int parentpos;
				PathNode parentelement;
				if (pos == 0 || (parentelement = array[parentpos = (pos - 1) / 2]).estimateFullCost <= element.estimateFullCost) {
					array[pos] = element;
					element.heapPosition = pos;
					return;
				}
				array[pos] = parentelement;
				parentelement.heapPosition = pos;
				pos = parentpos;
			}
		}
		public void decrease(PathNode oldelement, PathNode newelement) {
			moveUpFrom(oldelement.heapPosition, newelement);
			oldelement.heapPosition = -1;
		}
		public PathNode removeMin() {
			PathNode[] array = this.array;
			int size = --this.size;
			if (size == 0) {
				PathNode first = array[0];
				array[0] = null;
				first.heapPosition = -1;
				return first;
			}
			PathNode first = array[0];
			PathNode last = array[size];
			array[size] = null;
			first.heapPosition = -1;
			int pos = 0;
			while (true) {
				int leftpos = 2 * pos + 1;
				int rightpos = leftpos + 1;
				PathNode leftelement, rightelement;
				if (leftpos >= size) {
					array[pos] = last;
					last.heapPosition = pos;
					return first;
				} else if (rightpos >= size) {
					leftelement = array[leftpos];
					if (last.estimateFullCost <= leftelement.estimateFullCost) {
						array[pos] = last;
						last.heapPosition = pos;
						return first;
					}
					array[pos] = leftelement;
					leftelement.heapPosition = pos;
					pos = leftpos;
				} else {
					leftelement = array[leftpos];
					rightelement = array[rightpos];
					if (last.estimateFullCost <= leftelement.estimateFullCost && last.estimateFullCost <= rightelement.estimateFullCost) {
						array[pos] = last;
						last.heapPosition = pos;
						return first;
					}
					int lesserchildpos;
					PathNode lesserchildelement;
					if (leftelement.estimateFullCost <= rightelement.estimateFullCost) {
						lesserchildpos = leftpos;
						lesserchildelement = leftelement;
					} else {
						lesserchildpos = rightpos;
						lesserchildelement = rightelement;
					}
					array[pos] = lesserchildelement;
					lesserchildelement.heapPosition = pos;
					pos = lesserchildpos;
				}
			}
		}
	}
	
}
