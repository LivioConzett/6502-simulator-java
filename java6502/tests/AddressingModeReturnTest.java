package java6502.tests;

import java6502.AddressingModeReturn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressingModeReturnTest {

    @Test
    public void tests(){

        AddressingModeReturn test = new AddressingModeReturn((byte)0x63,(short)0xf34e);

        Assertions.assertEquals((byte)0x63,test.getValue());
        Assertions.assertEquals((short)0xf34e,test.getAddress());

        test.setAddress((short)0x1234);
        Assertions.assertEquals((byte)0x63,test.getValue());
        Assertions.assertEquals((short)0x1234,test.getAddress());

        test.setValue((byte)0x69);
        Assertions.assertEquals((byte)0x69,test.getValue());
        Assertions.assertEquals((short)0x1234,test.getAddress());

        AddressingModeReturn test2 = new AddressingModeReturn();
        Assertions.assertEquals((byte)0x0,test2.getValue());
        Assertions.assertEquals((short)0x0,test2.getAddress());

        test2.set((byte)0x81,(short)0x436f);
        Assertions.assertEquals((byte)0x81,test2.getValue());
        Assertions.assertEquals((short)0x436f,test2.getAddress());

        test2.set((byte)0x69,(short)0x1234);
        Assertions.assertEquals(test2,test);

        Assertions.assertFalse(test.equals(null));

        AddressingModeReturn nullTest = null;

        Assertions.assertNotEquals(test,nullTest);
    }
}
