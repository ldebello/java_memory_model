package ar.com.javamemorymodel.jcstress;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Description;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

@JCStressTest
@Description("Possible read/write reordering")
@State
@Outcome(id = "1, 1", expect = Expect.ACCEPTABLE, desc = "Thread 1 and Thread 2 interleaved")
@Outcome(id = "1, 0", expect = Expect.ACCEPTABLE, desc = "Thread 1 First - Thread 2 Second")
@Outcome(id = "0, 1", expect = Expect.ACCEPTABLE, desc = "Thread 2 First - Thread 1 Second")
@Outcome(id = "0, 0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Reordering / Memory Visibility")
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
