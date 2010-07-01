package com.jrako.command;

import org.apache.commons.io.LineIterator;

public interface RakoResultResolver {

    RakoResult resolve(LineIterator lines);

}
