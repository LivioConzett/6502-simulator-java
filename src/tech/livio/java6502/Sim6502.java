package tech.livio.java6502;

/**
 * Class that can simulate a 6502 processor system.
 * @author Livio Conzett
 * @version 16.10.2022
 */
public class Sim6502 {

    private final Memory memory;
    private final Stack stack;
    private final Flags flags;
    private final InstructionSet is;
    private final AddressingMode am;
    private final Control control;

    /**
     * Initialize the 6502 simulator
     */
    public Sim6502(){
        this.control = new Control();
        this.memory = new Memory();
        this.stack = new Stack(this.memory);
        this.flags = new Flags();
        this.is = new InstructionSet(this.memory, this.stack, this.flags, this.control);
        this.am = new AddressingMode(this.memory);
    }

    /**
     * Takes a Byte and runs the corresponding instruction
     * @param instruction byte to run.
     */
    void runInstruction(byte instruction){

        switch (instruction) {
            case (byte) 0x69 -> is.adc(am.immediate());
            case (byte) 0x65 -> is.adc(am.zeroPage());
            case (byte) 0x75 -> is.adc(am.zeroPageIndexX());
            case (byte) 0x6D -> is.adc(am.absolute());
            case (byte) 0x7D -> is.adc(am.absoluteIndexX());
            case (byte) 0x79 -> is.adc(am.absoluteIndexY());
            case (byte) 0x61 -> is.adc(am.indexedIndirect());
            case (byte) 0x71 -> is.adc(am.indirectIndexed());

            case (byte) 0x29 -> is.and(am.immediate());
            case (byte) 0x25 -> is.and(am.zeroPage());
            case (byte) 0x35 -> is.and(am.zeroPageIndexX());
            case (byte) 0x2d -> is.and(am.absolute());
            case (byte) 0x3d -> is.and(am.absoluteIndexX());
            case (byte) 0x39 -> is.and(am.absoluteIndexY());
            case (byte) 0x21 -> is.and(am.indexedIndirect());
            case (byte) 0x31 -> is.and(am.indirectIndexed());

            case (byte) 0x0a -> is.asl();
            case (byte) 0x06 -> is.asl(am.zeroPage());
            case (byte) 0x16 -> is.asl(am.zeroPageIndexX());
            case (byte) 0x0e -> is.asl(am.absolute());
            case (byte) 0x1e -> is.asl(am.absoluteIndexX());

            case (byte) 0x90 -> is.bcc(am.relative());

            case (byte) 0xB0 -> is.bcs(am.relative());

            case (byte) 0xf0 -> is.beq(am.relative());

            case (byte) 0x24 -> is.bit(am.zeroPage());
            case (byte) 0x2c -> is.bit(am.absolute());

            case (byte) 0x30 -> is.bmi(am.relative());

            case (byte) 0xd0 -> is.bne(am.relative());

            case (byte) 0x10 -> is.bpl(am.relative());

            case (byte) 0x00 -> is.brk();

            case (byte) 0x50 -> is.bvc(am.relative());

            case (byte) 0x70 -> is.bvs(am.relative());

            case (byte) 0x18 -> is.clc();

            case (byte) 0xd8 -> is.cld();

            case (byte) 0x58 -> is.cli();

            case (byte) 0xb8 -> is.clv();

            case (byte) 0xc9 -> is.cmp(am.immediate());
            case (byte) 0xc5 -> is.cmp(am.zeroPage());
            case (byte) 0xd5 -> is.cmp(am.zeroPageIndexX());
            case (byte) 0xcd -> is.cmp(am.absolute());
            case (byte) 0xdd -> is.cmp(am.absoluteIndexX());
            case (byte) 0xd9 -> is.cmp(am.absoluteIndexY());
            case (byte) 0xc1 -> is.cmp(am.indexedIndirect());
            case (byte) 0xd1 -> is.cmp(am.indirectIndexed());

            case (byte) 0xe0 -> is.cpx(am.immediate());
            case (byte) 0xe4 -> is.cpx(am.zeroPage());
            case (byte) 0xec -> is.cpx(am.absolute());

            case (byte) 0xc0 -> is.cpy(am.immediate());
            case (byte) 0xc4 -> is.cpy(am.zeroPage());
            case (byte) 0xcc -> is.cpy(am.absolute());

            case (byte) 0xc6 -> is.dec(am.zeroPage());
            case (byte) 0xd6 -> is.dec(am.zeroPageIndexX());
            case (byte) 0xce -> is.dec(am.absolute());
            case (byte) 0xde -> is.dec(am.absoluteIndexX());

            case (byte) 0xca -> is.dex();

            case (byte) 0x88 -> is.dey();

            case (byte) 0x49 -> is.eor(am.immediate());
            case (byte) 0x45 -> is.eor(am.zeroPage());
            case (byte) 0x55 -> is.eor(am.zeroPageIndexX());
            case (byte) 0x4d -> is.eor(am.absolute());
            case (byte) 0x5d -> is.eor(am.absoluteIndexX());
            case (byte) 0x59 -> is.eor(am.absoluteIndexY());
            case (byte) 0x41 -> is.eor(am.indexedIndirect());
            case (byte) 0x51 -> is.eor(am.indirectIndexed());

            case (byte) 0xe6 -> is.inc(am.zeroPage());
            case (byte) 0xf6 -> is.inc(am.zeroPageIndexX());
            case (byte) 0xee -> is.inc(am.absolute());
            case (byte) 0xfe -> is.inc(am.absoluteIndexX());

            case (byte) 0xe8 -> is.inx();

            case (byte) 0xc8 -> is.iny();

            case (byte) 0x4c -> is.jmp(am.absolute());
            case (byte) 0x6c -> is.jmp(am.indirectAbsolute());

            case (byte) 0x20 -> is.jsr(am.absolute());

            case (byte) 0xa9 -> is.lda(am.immediate());
            case (byte) 0xa5 -> is.lda(am.zeroPage());
            case (byte) 0xb5 -> is.lda(am.zeroPageIndexX());
            case (byte) 0xad -> is.lda(am.absolute());
            case (byte) 0xbd -> is.lda(am.absoluteIndexX());
            case (byte) 0xb9 -> is.lda(am.absoluteIndexY());
            case (byte) 0xa1 -> is.lda(am.indexedIndirect());
            case (byte) 0xb1 -> is.lda(am.indirectIndexed());

            case (byte) 0xa2 -> is.ldx(am.immediate());
            case (byte) 0xa6 -> is.ldx(am.zeroPage());
            case (byte) 0xb6 -> is.ldx(am.zeroPageIndexY());
            case (byte) 0xae -> is.ldx(am.absolute());
            case (byte) 0xbe -> is.ldx(am.absoluteIndexY());

            case (byte) 0xa0 -> is.ldy(am.immediate());
            case (byte) 0xa4 -> is.ldy(am.zeroPage());
            case (byte) 0xB4 -> is.ldy(am.zeroPageIndexX());
            case (byte) 0xac -> is.ldy(am.absolute());
            case (byte) 0xbc -> is.ldy(am.absoluteIndexX());

            case (byte) 0x4a -> is.lsr();
            case (byte) 0x46 -> is.lsr(am.zeroPage());
            case (byte) 0x56 -> is.lsr(am.zeroPageIndexX());
            case (byte) 0x4e -> is.lsr(am.absolute());
            case (byte) 0x5e -> is.lsr(am.absoluteIndexX());

            case (byte) 0xea -> is.nop();

            case (byte) 0x09 -> is.ora(am.immediate());
            case (byte) 0x05 -> is.ora(am.zeroPage());
            case (byte) 0x15 -> is.ora(am.zeroPageIndexX());
            case (byte) 0x0d -> is.ora(am.absolute());
            case (byte) 0x1d -> is.ora(am.absoluteIndexX());
            case (byte) 0x19 -> is.ora(am.absoluteIndexY());
            case (byte) 0x01 -> is.ora(am.indexedIndirect());
            case (byte) 0x11 -> is.ora(am.indirectIndexed());

            case (byte) 0x48 -> is.pha();

            case (byte) 0x08 -> is.php();

            case (byte) 0x68 -> is.pla();

            case (byte) 0x28 -> is.plp();

            case (byte) 0x2a -> is.rol();
            case (byte) 0x26 -> is.rol(am.zeroPage());
            case (byte) 0x36 -> is.rol(am.zeroPageIndexX());
            case (byte) 0x2e -> is.rol(am.absolute());
            case (byte) 0x3e -> is.rol(am.absoluteIndexX());

            case (byte) 0x6a -> is.ror();
            case (byte) 0x66 -> is.ror(am.zeroPage());
            case (byte) 0x76 -> is.ror(am.zeroPageIndexX());
            case (byte) 0x6e -> is.ror(am.absolute());
            case (byte) 0x7e -> is.ror(am.absoluteIndexX());

            case (byte) 0x40 -> is.rti();

            case (byte) 0x60 -> is.rts();

            case (byte) 0xe9 -> is.sbc(am.immediate());
            case (byte) 0xe5 -> is.sbc(am.zeroPage());
            case (byte) 0xf5 -> is.sbc(am.zeroPageIndexX());
            case (byte) 0xed -> is.sbc(am.absolute());
            case (byte) 0xfd -> is.sbc(am.absoluteIndexX());
            case (byte) 0xf9 -> is.sbc(am.absoluteIndexY());
            case (byte) 0xe1 -> is.sbc(am.indexedIndirect());
            case (byte) 0xf1 -> is.sbc(am.indirectIndexed());

            case (byte) 0x38 -> is.sec();

            case (byte) 0xf8 -> is.sed();

            case (byte) 0x78 -> is.sei();

            case (byte) 0x85 -> is.sta(am.zeroPage());
            case (byte) 0x95 -> is.sta(am.zeroPageIndexX());
            case (byte) 0x8d -> is.sta(am.absolute());
            case (byte) 0x9d -> is.sta(am.absoluteIndexX());
            case (byte) 0x99 -> is.sta(am.absoluteIndexY());
            case (byte) 0x81 -> is.sta(am.indexedIndirect());
            case (byte) 0x91 -> is.sta(am.indirectIndexed());

            case (byte) 0x86 -> is.stx(am.zeroPage());
            case (byte) 0x96 -> is.stx(am.zeroPageIndexY());
            case (byte) 0x8e -> is.stx(am.absolute());

            case (byte) 0x84 -> is.sty(am.zeroPage());
            case (byte) 0x94 -> is.sty(am.zeroPageIndexX());
            case (byte) 0x8c -> is.sty(am.absolute());

            case (byte) 0xaa -> is.tax();

            case (byte) 0xa8 -> is.tay();

            case (byte) 0xba -> is.tsx();

            case (byte) 0x8a -> is.txa();

            case (byte) 0x9a -> is.txs();

            case (byte) 0x98 -> is.tya();

            // not an official instruction, but needed to end the program in the simulation.
            case (byte) 0x80 -> is.ext();

            default -> is.nop();
        }
    }

    /**
     * Gets the byte at a certain memory address.
     * @param address Address to get byte from.
     * @return value of byte at address.
     */
    public byte getByteAtAddress(short address){
        return this.memory.getByteAtAddress(address);
    }

    /**
     * Gets the value of the Program counter.
     * @return value of the program counter
     */
    public short getProgramCounter(){
        return this.memory.getProgramCounter();
    }

    /**
     * Load code from String into memory.<br>
     * String needs to be Hex codes formatted 2 chars at a time with spaces in between.<br>
     * eg: ff ab 12 34 dc<br>
     * Memory will be loaded starting by address 0x0000.
     * @param code String of hex code
     */
    public void loadFromString(String code){
        this.memory.loadString(code);
    }

    /**
     * Get a range of memory.<br>
     * If the highAddress is <= lowAddress the method will return an empty byte array.
     * @param lowAddress address to being at
     * @param highAddress address to end at
     * @return byte array of the values between the two addresses
     */
    public byte[] getMemoryInRange(short lowAddress, short highAddress){
        return this.memory.getMemoryRange(lowAddress, highAddress);
    }

    /**
     * Do one step in the program.<br>
     * Executes one instruction. Not just one clock cycle.
     */
    public void step(){
        // if the previous instruction has jumped to a memory address then don't increment the counter
        // because the instruction at the programm counter needs to be executed.
        if(this.control.getSkipNextIncrement()) {
            this.control.allowNextIncrement();
        } else {
            this.memory.incrementProgramCounter();
        }

        this.runInstruction(this.memory.getCurrentByte());
    }

}
