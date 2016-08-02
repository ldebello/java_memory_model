package ar.com.javamemorymodel.jcstress;

import java.util.BitSet;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Description;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.BooleanResult2;

/*
 * Para utilizar JCStress utilizamos distintas annotations:
 * 
 * @JCStressTest: Marca esta clase como un test
 * @Description: Para incluir una descripción para el test
 * @State: Marca esta clase para ser usada como clase de estado del test, las clases de estado deben tener constructor por defecto y todas las inicializaciones del constructor son vistas por los actores
 * @Actor: Cada método marcado con @Actor es ejecutado por un thread
 * @Outcome: Cada @Outcome indica posibles resultados
 * @Arbiter: Este método es invocado luego que todos los actores vieron el estado y a este punto todos los efectos en memoria son visibles
 */
@JCStressTest
@Description("Utilizando BitSet desde multiples threads")
@State
@Outcome(id = "[true, true]", expect = Expect.ACCEPTABLE, desc = "Los updates se ven de forma correcta")
@Outcome(id = "[true, false]", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Thread 2 sobrrescribre Thread 1")
@Outcome(id = "[false, true]", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Thread 1 sobrrescribre Thread 2")
public class BitSetConcurrencyTest {

	private BitSet sharedBitSet = new BitSet();

	@Actor
	public void thread1() {
		sharedBitSet.set(1);
	}

	@Actor
	public void thread2() {
		sharedBitSet.set(2);
	}

	@Arbiter
	public void observe(BooleanResult2 result) {
		result.r1 = sharedBitSet.get(1);
		result.r2 = sharedBitSet.get(2);
	}
}