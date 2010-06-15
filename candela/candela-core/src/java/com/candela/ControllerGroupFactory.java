package com.candela;

import com.candela.control.ControllerGroup;

public interface ControllerGroupFactory {

    ControllerGroup newInstance() throws ConfigurationException;

}
