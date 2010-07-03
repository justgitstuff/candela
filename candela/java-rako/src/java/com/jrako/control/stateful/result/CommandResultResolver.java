package com.jrako.control.stateful.result;

import org.apache.commons.io.LineIterator;

public interface CommandResultResolver {

    RakoCommandResult resolve(LineIterator lines);

}
