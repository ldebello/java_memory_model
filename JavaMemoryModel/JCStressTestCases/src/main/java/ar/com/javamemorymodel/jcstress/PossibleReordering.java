package ar.com.javamemorymodel.jcstress;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

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
    public void observe(II_Result result) {
        result.r1 = x;
        result.r2 = y;
    }
}
