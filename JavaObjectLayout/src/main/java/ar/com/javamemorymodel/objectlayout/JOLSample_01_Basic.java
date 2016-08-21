package ar.com.javamemorymodel.objectlayout;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/*
 * Este ejemplo se encuentra en el proyecto "jol-samples" del repositorio de JOL.
 */
public class JOLSample_01_Basic {
	
	/*
     * En este ejemplo podemos ver lo básico sobre como los fields son posicionados.
     * Algunas cosas a notar:
     * 	- Cuanto espacio es consumido por el encabezado(header) del objeto
     *  - La distribución de los fields
     *  - Como el requerimiento de alineación aumenta el size del objeto
     */
    public static void main(String[] args) throws Exception {
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseClass(Holder.class).toPrintable());
    }

    public static class Holder {
        boolean x;
    }
}