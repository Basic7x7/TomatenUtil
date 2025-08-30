package de.tomatengames.util.test;

import static de.tomatengames.util.BezierUtil.eval;
import static de.tomatengames.util.BezierUtil.evalDeCasteljau;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BezierUtilTest {
	private static final double DELTA = 0.0000001;
	private static final double EPS = DELTA*0.01;
	
	@Test
	void testDeCasteljau() {
		assertEquals(0.0, evalDeCasteljau(0.0, 0.0, 1./3, 2./3, 1.0), DELTA);
		assertEquals(1.0, evalDeCasteljau(1.0, 0.0, 1./3, 2./3, 1.0), DELTA);
		assertEquals(0.5, evalDeCasteljau(0.5, 0.0, 1./3, 2./3, 1.0), DELTA);
		assertEquals(0.7, evalDeCasteljau(0.7, 0.0, 1./3, 2./3, 1.0), DELTA);
		
		assertEquals(0.5, evalDeCasteljau(0.5, 0.0, 1.0, 0.0, 1.0), DELTA);
		
		assertEquals(231.37184, evalDeCasteljau(0.09, 200, 319, 399, 700), DELTA);
		assertEquals(417.978752, evalDeCasteljau(0.09, 500, 140, 444, 100), DELTA);
		assertEquals(348.56408, evalDeCasteljau(0.42, 200, 319, 399, 700), DELTA);
		assertEquals(300.585344, evalDeCasteljau(0.42, 500, 140, 444, 100), DELTA);
		assertEquals(499.40512, evalDeCasteljau(0.73, 200, 319, 399, 700), DELTA);
		assertEquals(262.746496, evalDeCasteljau(0.73, 500, 140, 444, 100), DELTA);
		assertEquals(623.91076, evalDeCasteljau(0.91, 200, 319, 399, 700), DELTA);
		assertEquals(178.090048, evalDeCasteljau(0.91, 500, 140, 444, 100), DELTA);
	}
	
	@Test
	void testEval() {
		assertEquals(0.738, eval(0.738, EPS, 0, 0, 1./3, 1./3, 2./3, 2./3, 1, 1), DELTA);
		assertEquals(0.0, eval(0.0, EPS, 0, 0, 0, 0, 1, 1, 1, 1), DELTA);
		assertEquals(1.0, eval(1.0, EPS, 0, 0, 0, 0, 1, 1, 1, 1), DELTA);
		assertEquals(0.5, eval(0.5, EPS, 0, 0, 0, 0, 1, 1, 1, 1), DELTA);
		assertEquals(0.738, eval(0.738, EPS, 0, 0, 0, 0, 1, 1, 1, 1), DELTA);
		assertEquals(0.394, eval(0.394, EPS, 0, 0, 0, 0, 1, 1, 1, 1), DELTA);
		
		assertEquals(0.5, eval(0.5, EPS, 0, 0, 1, 0, 0, 1, 1, 1), DELTA);
		
		// eps=0.0
		assertEquals(390.382336, eval(245.00392, 0.0, 200, 500, 319, 140, 399, 444, 700, 100), DELTA);
		
		assertEquals(308.150528, eval(325.48736, EPS, 200, 500, 319, 140, 399, 444, 700, 100), DELTA);
		assertEquals(294.723712, eval(377.42704, EPS, 200, 500, 319, 140, 399, 444, 700, 100), DELTA);
		assertEquals(230.425984, eval(557.42488, EPS, 200, 500, 319, 140, 399, 444, 700, 100), DELTA);
		assertEquals(138.253568, eval(664.92416, EPS, 200, 500, 319, 140, 399, 444, 700, 100), DELTA);
	}
}
