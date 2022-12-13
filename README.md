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

| method                                                | short description                                     |
|-------------------------------------------------------|-------------------------------------------------------|
| [`hardReset()`](#hard-reset)                          | Resets the system including the loaded code and stack |
| [`reset()`](#reset)                                   | Resets the system, but leaves the code loaded         |
| [`getByteAtAddress()`](#get byte at address)          | Gets the value of a byte at an address                |
| [`getProgramCounter()`](#get current program counter) | Gets the value of the program counter                 |
| [`load()`](#load)                                     | Loads a program into memory                           |
| [`getMemoryInRange()`](#get memory in range)          | Gets the memory bytes within a certain range          |
| [`step()`](#step)                                     | Steps one instruction in the program                  |
| [`run()`](#run)                                       | Runs the program loaded in memory                     |
| [`getRunningThread()`](#get running thread)           | Gets the Thread the 6502 program is running in        |


### Hard Reset

### Reset

### Get Byte At Address

### Get Current Program Counter

### Load

### Get Memory In Range

### Step

### Run

### Get Running Thread

## Instruction Set
The package simulates the full 56 instructions (without the illegal op-codes) plus one extra one specific to this package.  
The EXT (0x80) op code is used to halt the processor completely. This code only exists within the realms of this simulation.
On a real 6502 chip 0x80 is an illegal op-code that defaults to the NOP (0xEA) operation.  
For a description of the full instruction set got to:  
[6502.livio.tech/instructionset/](https://6502.livio.tech/instructionset/)  

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

## TODO
What still needs to be done.  

1) Handle the external interrupts