package ar.com.javamemorymodel.objectlayout;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class JOLSample_02_Alignment {

    public static void main(String[] args) {
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseClass(Holder.class).toPrintable());
    }

    public static class Holder {
        long x;
    }
}