package tech.livio.java6502;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Sim6502Test {

    private Sim6502 sim;
    private int test;

    @BeforeEach
    void init(){
        this.sim = new Sim6502();
        this.test = 0;
    }

    @Test
    void loadMemoryFromStringTest(){
        String input = "12 33 ff f3 a1";

        byte[] output = {
                (byte) 0x12,
                (byte) 0x33,
                (byte) 0xff,
                (byte) 0xf3,
                (byte) 0xa1
        };

        sim.load(input);

        Assertions.assertArrayEquals(output,sim.getMemoryInRange((short)0x0000,(short)0x0004));

        input = "00 xx ff";

        output = new byte[] {
                (byte) 0x00,
                (byte) 0xff
        };

        sim.load(input);

        Assertions.assertArrayEquals(output,sim.getMemoryInRange((short)0x0000,(short)0x0001));
    }

    void test(short s){
        this.test = s;
    }

    @Test
    void stepTest(){

        sim.hardReset();

        sim.setDoOnExt(new ProgramCallBack() {
            @Override
            public void run(short programCounter) {
                test(programCounter);
                System.out.println("ended");
            }
        });


        sim.start();

        // Make the program counter to go to address 0x0000
        sim.load((short) 0xfffc,"00 00");

        String code = "ea ea ea 80";

        sim.load(code);

        Assertions.assertEquals((short) 0xfffc, sim.getProgramCounter());

        sim.step();

        Assertions.assertEquals((short) 0x00, sim.getProgramCounter());

        sim.step();
        sim.step();
        sim.step();
        sim.step();

        Assertions.assertEquals((short)0x03, sim.getProgramCounter());
        Assertions.assertEquals(0x03, this.test);

    }

    @Test
    void runTest() {

        sim.hardReset();
        Assertions.assertEquals((short) 0xfffc, sim.getProgramCounter());

        // Make the program counter to go to address 0x0000
        sim.load((short) 0xfffc,"00 00");
        String code = "ea ea ea 80";
        sim.load(code);

        sim.run();

        sim.waitForProgramEnd();

        Assertions.assertEquals((short)0x03, sim.getProgramCounter());
        Assertions.assertFalse(sim.getRunningThread().isAlive());


    }

    //@Disabled
    @Test
    void stopTest() {

        sim.hardReset();
        Assertions.assertEquals((short) 0xfffc, sim.getProgramCounter());

        String code = "";

        // create string with nothing but ea commands
        for(int i = 0; i < Math.pow(2,16); i++){
            code += "ea ";
        }

        // load whole memory with ea commands
        sim.load(code);
        // Make the program counter to go to address 0x0000
        // also add a jmp to 0000 right before the start vector so the programm loops
        sim.load((short) 0xffe9,"4c 00 00 00 00");

        // set the test into to 300, so we can then check if the doOnManualStop actually did something.
        sim.setDoOnManualHalt((e)->{
            test = 300;
            System.out.println("Programcounter: "+e);
        });

        sim.run();

        Assertions.assertTrue(sim.getRunningThread().isAlive());

        sim.stop();

        sim.waitForProgramEnd();

        Assertions.assertEquals(300,test);
        Assertions.assertFalse(sim.getRunningThread().isAlive());
    }

    @Test
    void interruptTest() {

        sim.hardReset();
        Assertions.assertEquals((short) 0xfffc, sim.getProgramCounter());

        String code = "";

        // create string with nothing but ea commands
        for(int i = 0; i < Math.pow(2,16); i++){
            code += "ea ";
        }

        // load whole memory with ea commands
        sim.load(code);

        //   NMI vector: 0x0000
        // start vector: 0x0000
        //   brk vector: 0xff00
        sim.load((short) 0xfffa,"00 00 00 00 00 ff");

        // add jump before the vectors
        sim.load((short) 0xf000, "4c 00 00");

        // end the program at program-counter 0xff00
        sim.load((short) 0xff00, "80");

        // clear the interrupt disable bit
        sim.load((short) 0x0000, "58");

        sim.run();

        Assertions.assertTrue(sim.getRunningThread().isAlive());

        sim.interrupt();

        sim.waitForProgramEnd();

        Assertions.assertFalse(sim.getRunningThread().isAlive());

        Assertions.assertEquals((short)0xff00, sim.getProgramCounter());
    }

    @Test
    void NMITestNoInterruptBit() {

        sim.hardReset();
        Assertions.assertEquals((short) 0xfffc, sim.getProgramCounter());

        String code = "";

        // create string with nothing but ea commands
        for(int i = 0; i < Math.pow(2,16); i++){
            code += "ea ";
        }

        // load whole memory with ea commands
        sim.load(code);

        //   NMI vector: 0xff00
        // start vector: 0x0000
        //   brk vector: 0x0000
        sim.load((short) 0xfffa,"00 ff 00 00 00 00");

        // add jump before the vectors
        sim.load((short) 0xf000, "4c 00 00");

        // end the program at program-counter 0xff00
        sim.load((short) 0xff00, "80");

        // clear the interrupt disable bit
        sim.load((short) 0x0000, "58");

        sim.run();

        Assertions.assertTrue(sim.getRunningThread().isAlive());

        sim.nonMaskableInterrupt();

        sim.waitForProgramEnd();

        Assertions.assertFalse(sim.getRunningThread().isAlive());

        Assertions.assertEquals((short)0xff00, sim.getProgramCounter());
    }

    @Test
    void NMITestInterruptBit() {

        sim.hardReset();
        Assertions.assertEquals((short) 0xfffc, sim.getProgramCounter());

        String code = "";

        // create string with nothing but ea commands
        for(int i = 0; i < Math.pow(2,16); i++){
            code += "ea ";
        }

        // load whole memory with ea commands
        sim.load(code);

        //   NMI vector: 0xff00
        // start vector: 0x0000
        //   brk vector: 0x0000
        sim.load((short) 0xfffa,"00 ff 00 00 00 00");

        // add jump before the vectors
        sim.load((short) 0xf000, "4c 00 00");

        // end the program at program-counter 0xff00
        sim.load((short) 0xff00, "80");

        // set the interrupt disable bit
        sim.load((short) 0x0000, "78");

        sim.run();

        Assertions.assertTrue(sim.getRunningThread().isAlive());

        sim.nonMaskableInterrupt();

        sim.waitForProgramEnd();

        Assertions.assertFalse(sim.getRunningThread().isAlive());

        Assertions.assertEquals((short)0xff00, sim.getProgramCounter());
    }

}
