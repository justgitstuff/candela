package com.jrako.control.stateless.udp;

public enum UdpRakoResult {

    /* */
    OK("AOK"),
    /* */
    ERROR("AERROR");

    private final String resultString;

    private UdpRakoResult(String resultString) {
        this.resultString = resultString;
    }

    static UdpRakoResult parseResult(String result) {
        return null;
    }

}
