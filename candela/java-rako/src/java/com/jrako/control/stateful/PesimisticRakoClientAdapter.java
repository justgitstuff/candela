package com.jrako.control.stateful;

import org.apache.log4j.Logger;

import com.jrako.control.RakoException;
import com.jrako.control.stateful.command.RakoCommand;
import com.jrako.control.stateful.result.InvalidResult;
import com.jrako.control.stateful.result.RakoCommandResult;

/**
 * For me the first command always fails - this class implements retries.
 */
public class PesimisticRakoClientAdapter implements StatefulRakoClient {

    private static final Logger LOG = Logger.getLogger(PesimisticRakoClientAdapter.class);
    private static final int DEFAULT_RETRY_COUNT = 3;

    private final int retryCount = DEFAULT_RETRY_COUNT;
    private final StatefulRakoClient internalController;

    public PesimisticRakoClientAdapter(StatefulRakoClient internalController) {
        this.internalController = internalController;
    }

    @Override
    public RakoCommandResult execute(RakoCommand command) throws RakoException {
        RakoCommandResult result = pesimisticallyExecute(command);
        return result;
    }

    private RakoCommandResult pesimisticallyExecute(RakoCommand command) throws RakoException {
        RakoCommandResult result = InvalidResult.INSTANCE;
        for (int i = 0; i < retryCount; i++) {
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
