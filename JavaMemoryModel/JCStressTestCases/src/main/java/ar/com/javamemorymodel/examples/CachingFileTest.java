package ar.com.javamemorymodel.examples;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Description;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.LL_Result;

@JCStressTest
@Description("Caching File Test")
@State
@Outcome(id = "Content, Close", expect = Expect.ACCEPTABLE, desc = "Thread 1 completed then Thread 2")
@Outcome(id = "CacheContent, Close", expect = Expect.ACCEPTABLE, desc = "Thread 2 flag to True without close then interleaved Thread 2")
@Outcome(id = "ReloadContent, Close", expect = Expect.ACCEPTABLE, desc = "Thread 2 flag to True and close then interleaved Thread 2")
@Outcome(id = "Invalid, Close", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Reordering Issue")
public class CachingFileTest {

    private boolean isClosed;

    private String fileContent;
    private String fileStatus = "Open";

    private String cacheContent = "CacheContent";

    @Actor
    public synchronized void thread1() {
        fileContent = getContent();
    }

    @Actor
    public void thread2() {
        isClosed = true;
        cacheContent = null;
        close();
    }

    private String getContent() {
        String content;
        if (!isClosed) {
            content = fileStatus.equals("Close") ? "Invalid" : "Content";
        } else {
            content = getCacheContent();
        }
        return content;
    }

    private String getCacheContent() {
        if (cacheContent == null) {
            cacheContent = "ReloadContent";
        }
        return cacheContent;
    }

    private void close() {
        fileStatus = "Close";
    }

    @Arbiter
    public void observe(LL_Result result) {
        result.r1 = fileContent;
        result.r2 = fileStatus;
    }
}