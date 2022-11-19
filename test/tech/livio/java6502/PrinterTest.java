package tech.livio.java6502;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrinterTest {

    private Memory memory;
    private Printer printer;

    @BeforeEach
    void init(){
        this.memory = new Memory();
        this.printer = new Printer(this.memory);
    }

    @Test
    void hexDumpMemTest(){

        this.memory.loadString((short)0x0000,"00 01 02 03 04 05 06 07 08 09 0a 0b 0c 0d 0e 0f");
        this.memory.loadString((short)0x0010,"10 11 12 13 14 15 16 17 18 19 1a 1b 1c 1d 1e 1f");
        this.memory.loadString((short)0x0050,"50 51 52 53 54 55 56 57 58 59 5a 5b 5c 5d 5e 5f");
        this.memory.loadString((short)0x8000,"48 65 6c 6c 6f 20 57 6f 72 6c 64 21 00 00 00 00");

        System.out.println(this.printer.hexDumpMem());


    }
}


