package ar.com.javamemorymodel.objectlayout;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/*
 * Este ejemplo se encuentra en el proyecto "jol-samples" del repositorio de JOL.
 */
public class JOLSample_02_Alignment {
	
	/*
     * Este ejemplo es un poco mas avanzado.
     * 
     * Porque el hardware requiere alinear los accesos para mantener la performance y correctitud.
     * Es esperado que los fields estén alineados por su size. Para el tipo boolean esto no
     * importa pero para el tipo long esto es diferente, como podemos ver en el ejemplo
     * el field es alineado por lo cual hay un gap de algunos byte entre el header y el field.
     */
    public static void main(String[] args) throws Exception {
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseClass(Holder.class).toPrintable());
    }

    public static class Holder {
        long x;
    }
}