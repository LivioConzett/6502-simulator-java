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

| method                                                | short description                                           |
|-------------------------------------------------------|-------------------------------------------------------------|
| [`hardReset()`](#hard-reset)                          | Resets the system including the loaded code and stack       |
| [`reset()`](#reset)                                   | Resets the system, but leaves the code loaded               |
| [`getByteAtAddress()`](#get-byte-at-address)          | Gets the value of a byte at an address                      |
| [`getProgramCounter()`](#get-current-program-counter) | Gets the value of the program counter                       |
| [`load()`](#load)                                     | Loads a program into memory                                 |
| [`getMemoryInRange()`](#get-memory-in-range)          | Gets the memory bytes within a certain range                |
| [`step()`](#step)                                     | Steps one instruction in the program                        |
| [`run()`](#run)                                       | Runs the program loaded in memory                           |
| [`getRunningThread()`](#get-running-thread)           | Gets the Thread the 6502 program is running in              |
| [`waitForProgramEnd()`](#wait-for-program-end)        | Halts the main thread and waits for the 6502 thread to end  |
| [`start()`](#start)                                   | Sets the run flag to true                                   |
| [`stop()`](#stop)                                     | Manually halts the 6502 program end ends its thread         |
| [`setDoOnExt()`](#doonext)                            | Sets the callback for the Ext operation                     |
| [`setDoOnStackOverflow()`](#doonstackoverflow)        | Sets the callback for the stack overflow                    |
| [`setDoOnManualHalt()`](#doonmanualhalt)              | Sets the callback for the manual halt                       |

### Hard Reset

### Reset

### Get Byte At Address

### Get Current Program Counter

### Load

### Get Memory In Range

### Step

### Run

### Get Running Thread

### Start

### Stop

### Wait For Program End
**WARNING:** If this is called and the 6502 program is in an endless loop, then the main thread in which the 
program is running will then effectively also be in an endless loop.  

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
| 0x0000 - 0x00ff | Frist Page                  |
| 0x0100 - 0x01ff | Stack                       |
| 0x0200 - 0xfff9 | Memory                      |
| 0xfffa - 0xfffb | [NMI Vector](#vectors)      |
| 0xfffc - 0xfffd | [Start Up Vector](#vectors) |
| 0xfffe - 0xffff | [Break Vector](#vectors)    |


### Vectors
There are three special memory addresses at the tail end of the memory range wich are jumped to in special cases. Each
of these hold the address to go to when they get called.  
  
**NMI Vector**  
*None Maskable Interrupt*
  
**Start Up Vector**  
The address stored here is where the Program jumps too on start up and after a reset. This is where you put the address 
of the first instruction of your program.
  
**Break Vector**  

## TODO
What still needs to be done.  

1) Handle the external interrupts