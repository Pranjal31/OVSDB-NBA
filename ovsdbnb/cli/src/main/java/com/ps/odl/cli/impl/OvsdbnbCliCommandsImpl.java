/*
 * Copyright © 2017 Pranjal Sharma and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.ps.odl.cli.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ps.odl.cli.api.OvsdbnbCliCommands;

public class OvsdbnbCliCommandsImpl implements OvsdbnbCliCommands {

    private static final Logger LOG = LoggerFactory.getLogger(OvsdbnbCliCommandsImpl.class);
    private final DataBroker dataBroker;

    public OvsdbnbCliCommandsImpl(final DataBroker db) {
        this.dataBroker = db;
        LOG.info("OvsdbnbCliCommandImpl initialized");
    }

    @Override
    public Object testCommand(Object testArgument) {
        return "This is a test implementation of test-command";
    }
}