package com.jrako.controller;

import org.apache.log4j.Logger;

import com.jrako.command.RakoCommand;
import com.jrako.command.RakoResult;
import com.jrako.command.result.InvalidResult;

public class PesimisticRakoControllerAdapter implements RakoController {

    private static final Logger LOG = Logger.getLogger(PesimisticRakoControllerAdapter.class);
    private static final int DEFAULT_RETRY_COUNT = 3;

    private final int retryCount = DEFAULT_RETRY_COUNT;
    private final RakoController internalController;

    public PesimisticRakoControllerAdapter(RakoController internalController) {
        this.internalController = internalController;
    }

    @Override
    public RakoResult execute(RakoCommand command) throws RakoControllerException {
        RakoResult result = pesimisticallyExecute(command);
        return result;
    }

    private RakoResult pesimisticallyExecute(RakoCommand command) throws RakoControllerException {
        RakoResult result = InvalidResult.INSTANCE;
        for (int i = 0; i < retryCount; i++) {
            // TODO: THis blocks here for some reason
            result = internalController.execute(command);
            if (result != InvalidResult.INSTANCE) {
                LOG.debug("Result is: " + result);
                break;
            }
            LOG.debug("Trying again: " + retryCount);
        }
        return result;
    }

}
