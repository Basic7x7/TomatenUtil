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
	
	
	/**
	 * Runs the De-Casteljau algorithm to evaluate the cubic Bézier curve
	 * defined by the parameters {@code p1, c1, c2, p2} at {@code t}.
	 * <p>
	 * Note that this function only calculates one dimension of the Bézier curve.
	 * In general, Bézier curves are defined as 2-dimensional curves {@code P(t)=[P.x(t), P.y(t)]}
	 * with {@code p1=[p1.x, p1.y]}, etc.
	 * To calculate {@code P(t)}, this method can be called twice with the corresponding parameters
	 * of the respective dimension.
	 * <pre>
	 * P.x(t) = evalDeCasteljau(t, p1.x, c1.x, c2.x, p2.x)
	 * P.y(t) = evalDeCasteljau(t, p1.y, c1.y, c2.y, p2.y)
	 * </pre>
	 * @param t The point at which the curve is to be evaluated. Must be in the range from 0 to 1.
	 * {@code t=0} returns {@code p1} and {@code t=1} returns {@code p2}.
	 * @param p1 The start point of the curve.
	 * @param c1 The control point of {@code p1}.
	 * @param c2 The control point of {@code p2}.
	 * @param p2 The end point of the curve.
	 * @return The evaluated point on the curve.
	 */
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
	
	/**
	 * Evaluates the cubic Bézier curve defined by the parameters {@code p1, c1, c2, p2}
	 * at the specified {@code x} position.
	 * <p>
	 * This method first tries to calculate the parameter {@code t}
	 * for the specified {@code x} position numerically.
	 * If the calculated {@code t} implies a {@code x(t)} with {@code abs(x(t)-x) <= eps},
	 * this step is complete.
	 * Then {@code y(t)} is calculated and returned.
	 * <p>
	 * The parameters must meet the following requirements:
	 * <ul>
	 * <li>{@code p1} must be before {@code p2} ({@code p1x < p2x})
	 * <li>{@code c1x} must be in the range of {@code p1x} and {@code p2x} ({@code p1x <= c1x <= p2x})
	 * <li>{@code c2x} must be in the range of {@code p1x} and {@code p2x} ({@code p1x <= c2x <= p2x})
	 * <li>{@code x} must be in the range of {@code p1x} and {@code p2x} ({@code p1x <= x <= p2x})
	 * </ul>
	 * If any of these requirements is not met, the result is undefined.
	 * @param x The x position that should be evaluated.
	 * Must be in the range of {@code p1x} and {@code p2x}.
	 * @param eps The maximum error of {@code x} to abort numerical methods.
	 * Negative values have the same effect as {@code 0}.
	 * It is recommended to choose a value greater than {@code 0} to improve performance.
	 * Note that it is not guaranteed that the error is less than {@code eps}.
	 * In special cases, e.g. {@code eps=0}, if it is not possible or involves a disproportionate effort
	 * to find a fitting {@code t}, {@code eps} may be ignored.
	 * @param p1x The {@code x} position of the start point. Must be less than {@code p2x}.
	 * @param p1y The {@code y} position of the start point.
	 * @param c1x The {@code x} position of the control point of {@code p1}.
	 * Must be in the range of {@code p1x} and {@code p2x}.
	 * @param c1y The {@code y} position of the control point of {@code p1}.
	 * @param c2x The {@code x} position of the control point of {@code p2}.
	 * Must be in the range of {@code p1x} and {@code p2x}.
	 * @param c2y The {@code y} position of the control point of {@code p2}.
	 * @param p2x The {@code x} position of the end point. Must be greater than {@code p1x}.
	 * @param p2y The {@code y} position of the end point.
	 * @return The {@code y} position of the curve at the specified {@code x} position.
	 */
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
					double ft = xt - x;
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
			double prevT = 0.0; // t=0.5 in the first iteration
			for (int i = 0; i < MAX_BISECTION_ITERATIONS; i++) { // Prevents endless loop
				t = (max + min) * 0.5;
				double fx = evalDeCasteljau(t, p1x, c1x, c2x, p2x);
				
				// Abort if x(t) is near enough to x.
				// If t did not change, it cannot be more precise.
				double dx = fx - x;
				if (Math.abs(dx) <= eps || t == prevT) {
					break makeTMorePrecise;
				}
				
				if (dx < 0) {
					min = t;
				}
				else {
					max = t;
				}
				prevT = t;
			}
		}
		
		double y = evalDeCasteljau(t, p1y, c1y, c2y, p2y);
		return y;
	}
}
