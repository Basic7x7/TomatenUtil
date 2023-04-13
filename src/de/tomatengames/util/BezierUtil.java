package de.tomatengames.util;

/**
 * Provides utilities to work with cubic Bézier curves.
 * 
 * @author Basic7x7
 * @version 2023-04-13
 * @since 1.2
 */
public class BezierUtil {
	private static final int MAX_NEWTON_ITERATIONS = 6;
	private static final int MAX_BISECTION_ITERATIONS = 10000;
	
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
	
	public static double eval(double x, double eps,
			double p1x, double p1y, double c1x, double c1y,
			double c2x, double c2y, double p2x, double p2y) {
		
		// Initial guess for t in [0,1]. t is assumed to be linear to x.
		double t = (x - p1x) / (p2x - p1x);
		
		makeTMorePrecise: {
			// Newton's method
			newton: {
				int i = 0;
				while (true) {
					double xt = evalDeCasteljau(t, p1x, c1x, c2x, p2x); // = x(t)
					
					// f(t) := x(t) − X
					double ft = x - xt;
					if (Math.abs(ft) <= eps) {
						break makeTMorePrecise;
					}
					
					if (++i > MAX_NEWTON_ITERATIONS) {
						break newton;
					}
					
					// f′(t) = x′(t)
					double t1 = 1-t;
					double dt_ft = -3*t1*t1*p1x + 3*t1*(1-3*t)*c1x + 3*t*(2-3*t)*c2x + 3*t*t*p2x; // = x'(t)
					
					// t_{i+1} := t_i − f(t) / f'(t)
					t = t - ft / dt_ft;
				}
			}
			
			// Use Bisection if Newton's method did not work.
			double min = 0.0, max = 1.0;
			for (int i = 0; i < MAX_BISECTION_ITERATIONS; i++) { // Prevents endless loop
				t = (max + min) * 0.5;
				double fx = evalDeCasteljau(t, p1x, c1x, c2x, p2x);
				if (Math.abs(fx) <= eps) {
					break makeTMorePrecise;
				}
				
				if (fx < x) {
					min = t;
				}
				else {
					max = t;
				}
			}
		}
		
		return evalDeCasteljau(t, p1y, c1y, c2y, p2y);
	}
}
