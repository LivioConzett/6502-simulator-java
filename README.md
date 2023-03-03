# 6502 Simulator
Java class that simulates a 6502 chip.  
Author: Livio Conzett  

## About
The package simulates a 6502 chip with 2<sup>16</sup> bytes of memory.  

## How to Start

```java
import tech.livio.java6502.Sim6502;

class Main() {

    public static void main(String[] args) {

        Sim6502 sim = new Sim6502();
        
        sim.run();

    }
}
```

## Methods of the Sim6502

| method                                                | short description                                          |
|-------------------------------------------------------|------------------------------------------------------------|
| [`hardReset()`](#hard-reset)                          | Resets the system including the loaded code and stack      |
| [`reset()`](#reset)                                   | Resets the system, but leaves the code loaded              |
| [`getByteAtAddress()`](#get-byte-at-address)          | Gets the value of a byte at an address                     |
| [`getProgramCounter()`](#get-current-program-counter) | Gets the value of the program counter                      |
| [`load()`](#load)                                     | Loads a program into memory                                |
| [`getMemoryInRange()`](#get-memory-in-range)          | Gets the memory bytes within a certain range               |
| [`step()`](#step)                                     | Steps one instruction in the program                       |
| [`run()`](#run)                                       | Runs the program loaded in memory                          |
| [`getRunningThread()`](#get-running-thread)           | Gets the Thread the 6502 program is running in             |
| [`waitForProgramEnd()`](#wait-for-program-end)        | Halts the main thread and waits for the 6502 thread to end |
| [`start()`](#start)                                   | Sets the run flag to true                                  |
| [`stop()`](#stop)                                     | Manually halts the 6502 program end ends its thread        |
| [`hexDump()`](#hexdump)                               | Creates a String of the Memory                             |
| [`setDoOnExt()`](#doonext)                            | Sets the callback for the Ext operation                    |
| [`setDoOnStackOverflow()`](#doonstackoverflow)        | Sets the callback for the stack overflow                   |
| [`setDoOnManualHalt()`](#doonmanualhalt)              | Sets the callback for the manual halt                      |
| [`interrupt()`](#interrupt)                           | Calls the external interrupt                               |
| [`nonMaskableInterrupt()`](#nonMaskableInterrupt)     | Calls the external non-maskable interrupt                  |

### Hard Reset
Resets the whole system. Clears the stack, memory and registers. Sets the program counter back to the
[start vector](#vectors). Sets the stack pointer back to 0x01ff.  
`void hardReset()`  

### Reset
Resets the System. Does the same as the [Hard Reset](#hard-reset) apart from clearing the memory.  
`void reset()`  

### Get Byte At Address
Gets the value of a byte at the specified location in memory.    
`byte getByteAtAddress(short address)`  
`short address`: Address of the byte to get.  
`return byte`: Byte at that address in memory.  

### Get Current Program Counter
Gets the value of the Program Counter.  
`short getProgramCounter()`  
`return short`: Value of the Program counter. ie: What location of memory the program is executing.  

### Load
Loads code into memory. Either from a byte array or a String.  
If loading from a String make sure that the values are all hex codes separated by a space eg: `"00 85 12 ff 45""`.  
`void load(String code)`  
`String code`: Code String to load into memory starting at address 0x0000.
  
`void load(short beginAddress, String code)`  
`short beginAddress`: Address where to begin loading code into.  
`String code`: Code String to load into memory.
  
`void load(byte[] code)`  
`byte[] code`: Byte Array of code that will be loaded into memory starting at address 0x0000.
  
`void load(short beginAddress, byte[] code)`  
`short beginAddress`: Address where to begin loading code into.  
`byte[] code`: Byte Array of code that will be loaded into memory starting at address 0x0000.

### Get Memory In Range
Gets the bytes in memory within a specified range. If the lowAddress is higher than the highAddress an empty byte Array
will be returned.  
`byte[] getMemoryInRange(short lowAddress, short highAddress)`  
`short lowAddress`: Lower bound of memory block to get (inclusive). 
`short highAddress`: Higher bound of memory block to get (inclusive).
`return byte[]`: Array of bytes within that range.  

### Step
Steps one step in the program. Will execute one op-code, not just one clock cycle. A lot of op-codes consist of multiple
clock cycles. Step will only run if the run flag is set. To set the run flag use the [`start()`](#start) method.   
`void step()`  

### Run
Will start a while-loop with the [`step()`](#step) method in it. As long as the run flag is true the program will step
through the program executing the op-codes.  
The run flag can be set to false in three situations:  
- The EXT op-code is encountered.
- Stack overflow
- Manual stop using the [`stop()`](#stop) method.
`void run()`  

### Get Running Thread
The [`run()`](#run) method will start a new thread where the 6502 program will run in. This is to allow the user to be able 
to manually stop the program. This method will return the thread.
`Thread getRunningThread()`  
`return Thread`: Thread where the 6502 program is running in.  

### Wait For Program End
**WARNING:** If this is called and the 6502 program is in an endless loop, then the main thread in which the
program is running will effectively also be in an endless loop.  
This will cause the main thread to wait for the 6502 thread to stop ie: for the 6502 program to finish.  
`void waitForProgramEnd()`  

### Stop
Will halt the 6502 program.  
`void stop()`

### Start
Sets the run flag to true.  
`void start()`  


### Hexdump


## Callbacks
The system has three callbacks that can be set by the user.  
All the three callbacks default to printing out the program counter when they get called.  
To override them call the according method and give them an instance of the `CallBack` class. Override the `run()` method
in the `Callback`. As a parameter `run()` will get the value of the program counter at the time of the method getting called.
### doOnExt
This method is called when the system comes across an EXT code (0x80). This will cause the program to stop.  
The method can be overridden using the `setDoOnExt()` method.
```java

class Main() {

    public static void main(String[] args) {

        Sim6502 sim = new Sim6502();
        
        sim.setDoOnExt(
          new Callback(){
              @Override
              public void run(short e) {
                  System.out.println("Method is overwritten");
              }
          }
        );

    }
}
```

### doOnStackOverflow
This method is called when the system encounters a Stack over- or underflow. ie: when the stack counter goes under 0x00 or
over 0xff.  
The method can be overridden using the `setDoOnStackOverflow()` method.

```java

class Main() {

    public static void main(String[] args) {

        Sim6502 sim = new Sim6502();
        
        sim.setDoOnStackOverflow(
          new Callback(){
              @Override
              public void run(short e) {
                  System.out.println("Method is overwritten");
              }
          }
        );

    }
}
```

### doOnManualHalt
This method is called when the system encounters a manual halt from the user via the `stop()` method.  
The method can be overridden using the `setDoOnManualHalt()` method.

```java

class Main() {

    public static void main(String[] args) {

        Sim6502 sim = new Sim6502();
        
        sim.setDoOnManualHalt(
          new Callback(){
              @Override
              public void run(short e) {
                  System.out.println("Method is overwritten");
              }
          }
        );

    }
}
```

### interrupt
Will call the external interrupt. Causes the program-counter to jump to the address specified in the [Break Vector](#vectors).  
`void interrupt()`

### nonMaskableInterrupt
Will call the external non-maskable interrupt. Causes the program-counter to jump to the address specified in the [NMI Vector](#vectors).  
`void nonMaskableInterrupt()`

## Instruction Set
The package simulates the full 56 instructions (without the illegal op-codes) plus one extra one specific to this package.  
The EXT (0x80) op code is used to halt the processor completely. This code only exists within the realms of this simulation.
On a real 6502 chip 0x80 is an illegal op-code that defaults to the NOP (0xEA) operation.  
For a description of the full instruction set got to:  
[6502.livio.tech/instructionset/](https://6502.livio.tech/instructionset/)

## Memory
The package simulates 2<sup>16</sup> bytes of memory. Everything is basically looked at as RAM. Every address can be read
from and written too. One can access by simply reading / writing to that address space. This means, that the user has to
safeguard themselves from accidentally writing into that space.

| Address Range   | What                        |
|-----------------|-----------------------------|
| 0x0000 - 0x00ff | First Page                  |
| 0x0100 - 0x01ff | Stack                       |
| 0x0200 - 0xfff9 | Memory                      |
| 0xfffa - 0xfffb | [NMI Vector](#vectors)      |
| 0xfffc - 0xfffd | [Start Up Vector](#vectors) |
| 0xfffe - 0xffff | [Break Vector](#vectors)    |


### Vectors
There are three special memory addresses at the tail end of the memory range witch are jumped to in special cases. Each
of these hold the address to go to when they get called.  
  
**NMI Vector**  
*None Maskable Interrupt*  
  
**Start Up Vector**  
The address stored here is where the Program jumps too on start up and after a reset. This is where you put the address 
of the first instruction of your program.  
  
**Break Vector**  
*Interrupt*

## TODO
What still needs to be done.  

1) compiler
