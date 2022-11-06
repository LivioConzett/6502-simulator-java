package java6502;


import java6502.AddressingMode;
import java6502.AddressingModeReturn;
import java6502.Memory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddressingModeTest{

    private Memory memory;
    private AddressingMode addr;
    private AddressingModeReturn ans;

    @BeforeEach
    public void init(){
        this.memory = new Memory();
        this.addr = new AddressingMode(this.memory);
        this.ans = new AddressingModeReturn();
    }

    @Test
    public void immediateTest(){
        this.memory.setProgramCounter((short) 0x01);
        this.memory.setByteAtAddress((short) 0x02, (byte) 0x69);
        this.ans.set((byte)0x69,(short)0x02);
        Assertions.assertEquals(this.ans,this.addr.immediate());
        Assertions.assertEquals((short)0x02,this.memory.getProgramCounter());

        this.memory.setByteAtAddress((short) 0x03, (byte) 0xff);
        this.ans.set((byte)0xff,(short)0x03);
        Assertions.assertEquals(this.ans,this.addr.immediate());
        Assertions.assertEquals((short)0x03,this.memory.getProgramCounter());
    }

    @Test
    public void absoluteTest(){
        this.memory.setProgramCounter((short) 0x1000);
        this.memory.setByteAtAddress((short) 0x1001, (byte) 0x34);
        this.memory.setByteAtAddress((short) 0x1002, (byte) 0x12);
        this.memory.setByteAtAddress((short) 0x1234,(byte) 0xf1);

        this.ans.set((byte)0xf1,(short)0x1234);

        Assertions.assertEquals(ans,this.addr.absolute());
        Assertions.assertEquals((short)0x1002,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short) 0xfff0);

        this.memory.setByteAtAddress((short) 0xfff1, (byte) 0x4b);
        this.memory.setByteAtAddress((short) 0xfff2, (byte) 0xa1);
        this.memory.setByteAtAddress((short) 0xa14b,(byte) 0x03);

        this.ans.set((byte)0x03,(short)0xa14b);

        Assertions.assertEquals(ans,this.addr.absolute());
        Assertions.assertEquals((short)0xfff2,this.memory.getProgramCounter());
    }

    @Test
    public void zeroPageTest(){
        this.memory.setProgramCounter((short) 0x0020);
        this.memory.setByteAtAddress((short) 0x0021, (byte) 0x1f);
        this.memory.setByteAtAddress((short) 0x001f,(byte) 0xff);

        this.ans.set((byte)0xff,(short)0x001f);

        Assertions.assertEquals(ans,this.addr.zeroPage());
        Assertions.assertEquals((short)0x0021,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short) 0x00ff);
        this.memory.setByteAtAddress((short) 0x0100, (byte) 0xab);
        this.memory.setByteAtAddress((short) 0x00ab, (byte) 0xcd);

        this.ans.set((byte)0xcd,(short)0x00ab);

        Assertions.assertEquals(ans,this.addr.zeroPage());
        Assertions.assertEquals((short)0x0100,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short) 0x0fff);
        this.memory.setByteAtAddress((short) 0x1000, (byte) 0x30);
        this.memory.setByteAtAddress((short) 0x0030,(byte) 0xfe);

        this.ans.set((byte)0xfe,(short)0x0030);

        Assertions.assertEquals(ans,this.addr.zeroPage());
        Assertions.assertEquals((short)0x1000,this.memory.getProgramCounter());
    }

    @Test
    public void indexAbsoluteTest(){
        this.memory.setProgramCounter((short) 0x1000);
        this.memory.setByteAtAddress((short) 0x1001, (byte) 0x34);
        this.memory.setByteAtAddress((short) 0x1002, (byte) 0x12);
        this.memory.setByteAtAddress((short) 0x1237,(byte) 0xf1);

        this.ans.set((byte)0xf1,(short)0x1237);

        Assertions.assertEquals(ans,this.addr.absoluteIndex((byte)0x03));
        Assertions.assertEquals((short)0x1002,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short) 0xfff0);
        this.memory.setByteAtAddress((short) 0xfff1, (byte) 0x4b);
        this.memory.setByteAtAddress((short) 0xfff2, (byte) 0xa1);
        this.memory.setByteAtAddress((short) 0xa24a,(byte) 0x03);
        this.ans.set((byte)0x03,(short)0xa24a);

        Assertions.assertEquals(ans,this.addr.absoluteIndex((byte)0xff));
        Assertions.assertEquals((short)0xfff2,this.memory.getProgramCounter());

        this.memory.setRegisterX((byte) 0x80);

        this.memory.setProgramCounter((short) 0x8080);
        this.memory.setByteAtAddress((short) 0x8081, (byte) 0x80);
        this.memory.setByteAtAddress((short) 0x8082, (byte) 0x08);
        this.memory.setByteAtAddress((short) 0x0900,(byte) 0x44);
        this.ans.set((byte)0x44,(short)0x0900);

        Assertions.assertEquals(ans,this.addr.absoluteIndex_X());
        Assertions.assertEquals((short)0x8082,this.memory.getProgramCounter());

        this.memory.setRegisterY((byte) 0x92);

        this.memory.setProgramCounter((short) 0x0001);
        this.memory.setByteAtAddress((short) 0x0002, (byte) 0x32);
        this.memory.setByteAtAddress((short) 0x0003, (byte) 0xfe);
        this.memory.setByteAtAddress((short) 0xfec4,(byte) 0x09);
        this.ans.set((byte)0x09,(short)0xfec4);

        Assertions.assertEquals(ans,this.addr.absoluteIndex_Y());
        Assertions.assertEquals((short)0x0003,this.memory.getProgramCounter());
    }

    @Test
    public void indirectAbsoluteTest(){
        this.memory.setProgramCounter((short) 0x1000);
        this.memory.setByteAtAddress((short) 0x1001, (byte) 0x34);
        this.memory.setByteAtAddress((short) 0x1002, (byte) 0x12);
        this.memory.setByteAtAddress((short) 0x1234,(byte) 0xf1);
        this.memory.setByteAtAddress((short) 0x1235, (byte) 0x23);

        this.ans.set((byte)0x0,(short)0x23f1);

        Assertions.assertEquals(ans,this.addr.indirectAbsolute());
        Assertions.assertEquals((short)0x1002,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short) 0x0238);
        this.memory.setByteAtAddress((short) 0x0239, (byte) 0x00);
        this.memory.setByteAtAddress((short) 0x023a, (byte) 0xff);
        this.memory.setByteAtAddress((short) 0xff00,(byte) 0xff);
        this.memory.setByteAtAddress((short) 0xff01, (byte) 0x00);

        this.ans.set((byte)0x0,(short)0x00ff);

        Assertions.assertEquals(ans,this.addr.indirectAbsolute());
        Assertions.assertEquals((short)0x023a,this.memory.getProgramCounter());
    }

    @Test
    public void zeroPageIndexTest(){
        this.memory.setProgramCounter((short) 0x1000);
        this.memory.setByteAtAddress((short) 0x1001, (byte) 0x34);
        this.memory.setByteAtAddress((short) 0x0037,(byte) 0xf1);
        this.ans.set((byte)0xf1,(short)0x0037);

        Assertions.assertEquals(ans,this.addr.zeroPageIndex((byte)0x03));
        Assertions.assertEquals((short)0x1001,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short) 0xfff0);
        this.memory.setByteAtAddress((short) 0xfff1, (byte) 0x81);
        this.memory.setByteAtAddress((short) 0x0090,(byte) 0x03);
        this.ans.set((byte)0x03,(short)0x0090);

        Assertions.assertEquals(ans,this.addr.zeroPageIndex((byte)0x0f));
        Assertions.assertEquals((short)0xfff1,this.memory.getProgramCounter());

        this.memory.setRegisterX((byte) 0x80);

        this.memory.setProgramCounter((short) 0x8080);
        this.memory.setByteAtAddress((short) 0x8081, (byte) 0x01);
        this.memory.setByteAtAddress((short) 0x0081,(byte) 0x44);
        this.ans.set((byte)0x44,(short)0x0081);

        Assertions.assertEquals(ans,this.addr.zeroPageIndex_X());
        Assertions.assertEquals((short)0x8081,this.memory.getProgramCounter());

        this.memory.setRegisterY((byte) 0x31);

        this.memory.setProgramCounter((short) 0x0001);
        this.memory.setByteAtAddress((short) 0x0002, (byte) 0x32);
        this.memory.setByteAtAddress((short) 0x63,(byte) 0x09);
        this.ans.set((byte)0x09,(short)0x63);

        Assertions.assertEquals(ans,this.addr.zeroPageIndex_Y());
        Assertions.assertEquals((short)0x0002,this.memory.getProgramCounter());
    }


    @Test
    public void indexedIndirectTest(){

        this.memory.setProgramCounter((short)0x1234);
        this.memory.setRegisterX((byte)0x09);

        this.memory.setByteAtAddress((short)0x1235,(byte)0x05);
        this.memory.setByteAtAddress((short)0x000e,(byte)0x45);
        this.memory.setByteAtAddress((short)0x000f,(byte)0xff);
        this.memory.setByteAtAddress((short)0xff45,(byte)0x69);
        this.ans.set((byte)0x69,(short)0xff45);

        Assertions.assertEquals(ans,this.addr.indexedIndirect());
        Assertions.assertEquals((short)0x1235,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0x4feb);
        this.memory.setRegisterX((byte)0xf0);

        this.memory.setByteAtAddress((short)0x4fec,(byte)0x15);
        this.memory.setByteAtAddress((short)0x0005,(byte)0x30);
        this.memory.setByteAtAddress((short)0x0006,(byte)0x08);
        this.memory.setByteAtAddress((short)0x0830,(byte)0x7);
        this.ans.set((byte)0x7,(short)0x0830);

        Assertions.assertEquals(ans,this.addr.indexedIndirect());
        Assertions.assertEquals((short)0x4fec,this.memory.getProgramCounter());

    }

    @Test
    public void indirectIndexedTest(){

        this.memory.setProgramCounter((short)0x1234);
        this.memory.setRegisterY((byte)0x09);

        this.memory.setByteAtAddress((short)0x1235,(byte)0x05);
        this.memory.setByteAtAddress((short)0x0005,(byte)0x45);
        this.memory.setByteAtAddress((short)0x0006,(byte)0xff);
        this.memory.setByteAtAddress((short)0xff4e,(byte)0x69);
        this.ans.set((byte)0x69,(short)0xff4e);

        Assertions.assertEquals(ans,this.addr.indirectIndexed());
        Assertions.assertEquals((short)0x1235,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0x4feb);
        this.memory.setRegisterY((byte)0xf0);

        this.memory.setByteAtAddress((short)0x4fec,(byte)0x15);
        this.memory.setByteAtAddress((short)0x0015,(byte)0x30);
        this.memory.setByteAtAddress((short)0x0016,(byte)0x08);
        this.memory.setByteAtAddress((short)0x0920,(byte)0x7);
        this.ans.set((byte)0x7,(short)0x0920);

        Assertions.assertEquals(ans,this.addr.indirectIndexed());
        Assertions.assertEquals((short)0x4fec,this.memory.getProgramCounter());

    }

    @Test
    public void relativAddressingTest(){

        this.memory.setProgramCounter((short)0x0f3e);
        this.memory.setByteAtAddress((short)0x0f3f,(byte)0x2e);
        this.ans.set((byte)0x0,(short)0xf6d);

        Assertions.assertEquals(ans,this.addr.relative());
        Assertions.assertEquals((short)0x0f3f,this.memory.getProgramCounter());

        this.memory.setProgramCounter((short)0x0fff);
        this.memory.setByteAtAddress((short)0x1000,(byte)-36);
        this.ans.set((byte)0x0,(short)0xfdc);

        Assertions.assertEquals(ans,this.addr.relative());
        Assertions.assertEquals((short)0x1000,this.memory.getProgramCounter());
    }
}