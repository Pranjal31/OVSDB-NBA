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

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.ovsdbnb.rev180618.*;

import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;

public class OvsdbnbImpl implements OvsdbnbService {
    private static final Logger LOG = LoggerFactory.getLogger(OvsdbnbImpl.class);

    @Override
    public Future<RpcResult<AddBridgeOutput>> addBridge(AddBridgeInput input) {

        AddBridgeOutputBuilder addBridgeOutputBuilder = new AddBridgeOutputBuilder();
        String bridgeName = input.getBridgeName();
        CloseableHttpResponse httpResponse = null;

        if (bridgeName == null || bridgeName.isEmpty()) {
            addBridgeOutputBuilder.setResponseCode("700");
            addBridgeOutputBuilder.setResponseMessage("Please provide a valid bridge name");
            return RpcResultBuilder.success(addBridgeOutputBuilder.build()).buildFuture();
        }

        try {
            httpResponse = RESTHelper.getAPIResponse("ADD_BRIDGE",bridgeName);

            if (httpResponse.getStatusLine().getStatusCode() == 201) {
                addBridgeOutputBuilder.setResponseCode("201");
                addBridgeOutputBuilder.setResponseMessage("successfully added bridge " + bridgeName + " on Open vSwitch instance");
                return RpcResultBuilder.success(addBridgeOutputBuilder.build()).buildFuture();
            }
        } catch (Exception exp) {
            LOG.error("Exception while adding bridge " + bridgeName, exp);
            addBridgeOutputBuilder.setResponseCode(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            addBridgeOutputBuilder.setResponseMessage("Exception while adding bridge "+ bridgeName + httpResponse.getStatusLine());
            return RpcResultBuilder.success(addBridgeOutputBuilder.build()).buildFuture();
        }
        addBridgeOutputBuilder.setResponseCode(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
        addBridgeOutputBuilder.setResponseMessage(String.valueOf(httpResponse.getStatusLine()));
        return RpcResultBuilder.success(addBridgeOutputBuilder.build()).buildFuture();
    }

    @Override
    public Future<RpcResult<DeleteBridgeOutput>> deleteBridge(DeleteBridgeInput input) {

        DeleteBridgeOutputBuilder delBridgeOutputBuilder = new DeleteBridgeOutputBuilder();
        String bridgeName = input.getBridgeName();
        CloseableHttpResponse httpResponse = null;

        if (bridgeName == null || bridgeName.isEmpty()) {
            delBridgeOutputBuilder.setResponseCode("700");
            delBridgeOutputBuilder.setResponseMessage("Please provide a valid bridge name");
            return RpcResultBuilder.success(delBridgeOutputBuilder.build()).buildFuture();
        }

        try {
            httpResponse = RESTHelper.getAPIResponse("DELETE_BRIDGE",bridgeName);
            if (httpResponse!= null && httpResponse.getStatusLine().getStatusCode() == 200) {
                delBridgeOutputBuilder.setResponseCode("200");
                delBridgeOutputBuilder.setResponseMessage("successfully deleted bridge " + bridgeName + " on Open vSwitch instance");
                return RpcResultBuilder.success(delBridgeOutputBuilder.build()).buildFuture();
            }

        } catch (Exception exp) {
            LOG.error("Exception while deleting bridge "+ bridgeName, exp);
            delBridgeOutputBuilder.setResponseCode(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            delBridgeOutputBuilder.setResponseMessage("Exception while deleting bridge " + bridgeName + httpResponse.getStatusLine());
            return RpcResultBuilder.success(delBridgeOutputBuilder.build()).buildFuture();
        }

        delBridgeOutputBuilder.setResponseCode(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
        delBridgeOutputBuilder.setResponseMessage(String.valueOf(httpResponse.getStatusLine()));
        return RpcResultBuilder.success(delBridgeOutputBuilder.build()).buildFuture();

    }

    @Override
    public Future<RpcResult<AddPortOutput>> addPort(AddPortInput input) {
        AddPortOutputBuilder addPortOutputBuilder = new AddPortOutputBuilder();
        String bridgeName = input.getBridgeName();
        String portName = input.getPortName();
        CloseableHttpResponse httpResponse = null;

        if (bridgeName == null || bridgeName.isEmpty() || portName == null || portName.isEmpty()) {
            addPortOutputBuilder.setResponseCode("700");
            addPortOutputBuilder.setResponseMessage("Please provide a valid bridge/port name");
            return RpcResultBuilder.success(addPortOutputBuilder.build()).buildFuture();
        }

        try {
            httpResponse = RESTHelper.getAPIResponse("ADD_PORT",bridgeName, portName);

            if (httpResponse.getStatusLine().getStatusCode() == 201){
                addPortOutputBuilder.setResponseCode("201");
                addPortOutputBuilder.setResponseMessage("successfully added port " + portName +" on bridge " + bridgeName + " on Open vSwitch instance");
                return RpcResultBuilder.success(addPortOutputBuilder.build()).buildFuture();
            }

        } catch (Exception exp) {
            LOG.error("Exception while adding port "+ portName + " on bridge " + bridgeName, exp);
            addPortOutputBuilder.setResponseCode(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            addPortOutputBuilder.setResponseMessage("Exception while adding port "+ portName +" on bridge " + bridgeName + httpResponse.getStatusLine());
            return RpcResultBuilder.success(addPortOutputBuilder.build()).buildFuture();
        }

        addPortOutputBuilder.setResponseCode(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
        addPortOutputBuilder.setResponseMessage(String.valueOf(httpResponse.getStatusLine()));
        return RpcResultBuilder.success(addPortOutputBuilder.build()).buildFuture();

    }

    @Override
    public Future<RpcResult<DeletePortOutput>> deletePort(DeletePortInput input) {
        DeletePortOutputBuilder delPortOutputBuilder = new DeletePortOutputBuilder();
        String bridgeName = input.getBridgeName();
        String portName = input.getPortName();
        CloseableHttpResponse httpResponse = null;

        if (bridgeName == null || bridgeName.isEmpty() || portName == null || portName.isEmpty()) {
            delPortOutputBuilder.setResponseCode("700");
            delPortOutputBuilder.setResponseMessage("Please provide a valid bridge/port name");
            return RpcResultBuilder.success(delPortOutputBuilder.build()).buildFuture();
        }

        try {
            httpResponse = RESTHelper.getAPIResponse("DELETE_PORT",bridgeName,portName);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                delPortOutputBuilder.setResponseCode("200");
                delPortOutputBuilder.setResponseMessage("successfully deleted port " + portName + " on bridge " + bridgeName+ " on Open vSwitch instance");
                return RpcResultBuilder.success(delPortOutputBuilder.build()).buildFuture();
            }

        } catch (Exception exp) {
            LOG.error("Exception while deleting port "+ portName + " on bridge " + bridgeName, exp);
            delPortOutputBuilder.setResponseCode(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            delPortOutputBuilder.setResponseMessage("Exception while deleting port "+ portName + " on bridge " + bridgeName + httpResponse.getStatusLine());
            return RpcResultBuilder.success(delPortOutputBuilder.build()).buildFuture();
        }

        delPortOutputBuilder.setResponseCode(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
        delPortOutputBuilder.setResponseMessage(String.valueOf(httpResponse.getStatusLine()));
        return RpcResultBuilder.success(delPortOutputBuilder.build()).buildFuture();
    }

}
