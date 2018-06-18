/*
 * Copyright © 2017 verizon and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.ps.odl.impl.impl;

import com.ps.odl.impl.utility.RESTHelper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.ovsdbnb.rev180618.OvsdbnbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OvsdbnbProvider implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(OvsdbnbProvider.class);

    private final DataBroker dataBroker;
    private final RpcProviderRegistry rpcProviderRegistry;
    private BindingAwareBroker.RpcRegistration<OvsdbnbService> serviceRegistration;

    public OvsdbnbProvider(final DataBroker dataBroker, RpcProviderRegistry rpcProviderRegistry) {
        this.dataBroker = dataBroker;
        this.rpcProviderRegistry = rpcProviderRegistry;
    }

    /**
     * Method called when the blueprint container is created.
     */
    public void init() {
        serviceRegistration = rpcProviderRegistry.addRpcImplementation(OvsdbnbService.class,new OvsdbnbImpl());
        LOG.info("OvsdbnbProvider Session Initiated");
        if (!addOvsHost()) {
            LOG.error("Failed to Add OVS HOST information at StartUp time");
        }
    }

    /**
     * Method called when the blueprint container is destroyed.
     */
    public void close() {
        LOG.info("OvsdbnbProvider Closed");
    }


    private boolean addOvsHost() {

        boolean status = false;
        try {
            CloseableHttpResponse httpResponse = RESTHelper.getAPIResponse("ADD_OVS_HOST");
            if (httpResponse.getStatusLine().getStatusCode() == 201 || httpResponse.getStatusLine().getStatusCode() == 200) {
                status = true;
            }
        } catch (Exception exp) {
            LOG.error("Exception While adding OVS-HOST-DATA : ", exp);
            status = false;
        }
        return status;
    }
}