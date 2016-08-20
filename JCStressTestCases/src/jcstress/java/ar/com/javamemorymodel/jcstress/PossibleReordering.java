package ar.com.javamemorymodel.jcstress;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Description;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.IntResult2;

@JCStressTest
@Description("Reordenamiento de read/writes")
@State
@Outcome(id = "[1, 1]", expect = Expect.ACCEPTABLE, desc = "Ambos threads observan los writes aplicados por el otro")
@Outcome(id = "[1, 0]", expect = Expect.ACCEPTABLE, desc = "El Thread 1 ve el write aplicado en Thread 2")
@Outcome(id = "[0, 1]", expect = Expect.ACCEPTABLE, desc = "El Thread 2 ve el write aplicado en Thread 1")
@Outcome(id = "[0, 0]", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Cuando sucede esto es por los Threads no ven los cambios aplicados por el otro")
public class PossibleReordering {
	
	private int a = 0, b = 0;
	private int x = 0, y = 0;
	
	@Actor
	public void thread1() {
		a = 1;
		x = b;
	}

	@Actor
	public void thread2() {
		b = 1;
		y = a;
	}
	
	@Arbiter
	public void observe(IntResult2 result) {
		result.r1 = x;
		result.r2 = y;
	}
}
