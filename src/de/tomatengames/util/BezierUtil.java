package de.tomatengames.util;

/**
 * Provides utilities to work with cubic Bézier curves.
 * 
 * @author Basic7x7
 * @version 2023-04-13
 * @since 1.2
 */
public class BezierUtil {
	
	// Static class
	private BezierUtil() {
	}
	
	
	public static double evalDeCasteljau(double t,
			double p1, double c1, double c2, double p2) {
		
		double t1 = 1-t;
		double b11 = t1*p1 + t*c1;
		double b12 = t1*c1 + t*c2;
		double b13 = t1*c2 + t*p2;
		double b21 = t1*b11 + t*b12;
		double b22 = t1*b12 + t*b13;
		return t1*b21 + t*b22;
	}
	
}
