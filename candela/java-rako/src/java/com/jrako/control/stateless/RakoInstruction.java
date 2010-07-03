package com.jrako.control.stateless;

import com.jrako.candela.RakoChannel;
import com.jrako.candela.RakoRoom;

public interface RakoInstruction {

    // Literal 'R'
    // #Bytes to follow
    // RoomH
    // RoomL
    // Channel
    // Instruction
    // 0-7 bytes
    // CRC

    RakoRoom getRoom();

    RakoChannel getChannel();

    RakoInstructionType getInstruction();

}
