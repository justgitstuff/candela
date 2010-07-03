package com.jrako.control.stateless.udp;

import com.jrako.candela.RakoChannel;
import com.jrako.candela.RakoRoom;
import com.jrako.control.stateless.RakoInstruction;
import com.jrako.control.stateless.RakoInstructionType;

public class UdpRakoMessage implements RakoInstruction {

    // Literal 'R'
    // #Bytes to follow
    // RoomH
    // RoomL
    // Channel
    // Instruction
    // 0-7 bytes
    // CRC

    public RakoRoom getRoom() {
        return null;
    }

    public RakoChannel getChannel() {
        return null;
    }

    public RakoInstructionType getInstruction() {
        return null;
    }

    byte[] toByteArray() {
        return null;
    }

}
