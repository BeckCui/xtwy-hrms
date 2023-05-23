import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class TestApplication {



    @Test
    public void cc() {

        AtomicInteger atomicInteger =new AtomicInteger();

        atomicInteger.getAndAdd(1);

        System.out.println(atomicInteger.get());

        atomicInteger.getAndAdd(1);
        System.out.println(atomicInteger.get() & 15) ;
        atomicInteger.getAndAdd(1);
        System.out.println(atomicInteger.get() & 15);
        atomicInteger.getAndAdd(1);
        System.out.println(atomicInteger.get() & 15);

    }

}
