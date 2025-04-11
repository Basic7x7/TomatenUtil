public class FailInitializer {
	static {
		boolean t = true;
		if (t)
			throw new RuntimeException("FailInitializer cannot init");
	}
}
