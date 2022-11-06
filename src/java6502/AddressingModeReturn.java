package java6502;

/**
 * The class that the addressingmodes return.
 * It holds both the value and address that the addressingmodes return. <br>
 * This is needed because the instruction sets sometimes need tha value or the address.
 */
class AddressingModeReturn {

    private byte value;
    private short address;

    /**
     * Initializes the object with the given address and value.
     * @param value value at the address
     * @param address address
     */
    public AddressingModeReturn(byte value, short address){
        this.value = value;
        this.address = address;
    }

    /**
     * Initializes the object with value 0 and address 0
     */
    public AddressingModeReturn(){
        this((byte)0,(byte)0);
    }

    /**
     * Get the value
     * @return value set
     */
    public byte getValue(){
        return this.value;
    }

    /**
     * get the address
     * @return address set
     */
    public short getAddress(){
        return this.address;
    }

    /**
     * Set the value.
     * @param value value to set
     */
    public void setValue(byte value){
        this.value = value;
    }

    /**
     * Set the address.
     * @param address address to set.
     */
    public void setAddress(short address){
        this.address = address;
    }

    /**
     * Set both value and address
     * @param value value to set
     * @param address address to set
     */
    public void set(byte value, short address){
        this.setValue(value);
        this.setAddress(address);
    }

    /**
     * Check if this object has the same value and address as another AddressingModeReturn object.
     * @param obj AddressingModeReturn object to compare this one to.
     * @return True if the value and address are the same. Else false.
     */
    @Override
    public boolean equals(Object obj){
        // If the object is compared with itself then return true
        if (obj == this) return true;

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(obj instanceof AddressingModeReturn)) return false;

        AddressingModeReturn c = (AddressingModeReturn) obj;

        return(c.getValue() == this.value && c.getAddress() == this.address);
    }


}
