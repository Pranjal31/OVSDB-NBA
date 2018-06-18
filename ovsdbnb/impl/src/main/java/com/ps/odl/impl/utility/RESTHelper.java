/*
 * Copyright © 2017 verizon and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.ps.odl.impl.utility;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class RESTHelper {

    private static final Logger LOG = LoggerFactory.getLogger(RESTHelper.class);
    private static PropContainer propContainer = PropContainer.getInstance();
    private static final String authStr = propContainer.getUser() + ":" + propContainer.getPassword();
    private static String encodedAuthStr = Base64.encodeBase64String(authStr.getBytes());
    private static final String CONTENTTYPE_JSON = "application/json; charset=utf-8";
    private static final String URL_PREFIX = "http://" + propContainer.getControllerIp() + ":" + propContainer.getPort();


   public static CloseableHttpResponse getAPIResponse(String operationType, String ... args) {
        String ovsHostIp = propContainer.getOvsHostIp();
        String ovsHostPort = propContainer.getOvsHostport();
        String topologyId = propContainer.getTopologyId();
        String nodeId = propContainer.getNodeId();
        String bodyString;
        String bridgeName;
        String portName;

        switch (operationType){
            case "ADD_OVS_HOST":
                LOG.info("Proprerties Container : " + propContainer.toString());
                String ADD_NODEID_URL = URL_PREFIX + "/restconf/config/network-topology:network-topology/topology/" + topologyId + "/node/" + nodeId.replaceAll("\\/", "%2F");
                bodyString = InputdataforPost.getOvsHostData(nodeId, ovsHostIp, ovsHostPort);
                LOG.info("input post data for adding ovs host : " + bodyString + " and URL : " + ADD_NODEID_URL);
                return callAPI(ADD_NODEID_URL,bodyString,"PUT");

            case "ADD_BRIDGE":
                bridgeName = args[0];
                String ADD_BRIDGE_URL = URL_PREFIX + "/restconf/config/network-topology:network-topology/topology/" + topologyId + "/node/" + nodeId.replaceAll("\\/", "%2F") + "%2Fbridge%2F" + bridgeName;
                bodyString = InputdataforPost.getAddBridgeData(nodeId, bridgeName);
                LOG.info("input post data for adding bridge: " + bodyString + " and URL : " + ADD_BRIDGE_URL);
                return callAPI(ADD_BRIDGE_URL,bodyString,"PUT");

            case "DELETE_BRIDGE":
                bridgeName = args[0];
                String DEL_BRIDGE_URL = URL_PREFIX + "/restconf/config/network-topology:network-topology/topology/" + topologyId + "/node/" + nodeId.replaceAll("\\/", "%2F") + "%2Fbridge%2F" + bridgeName;
                LOG.info("Delete Bridge URL : " + DEL_BRIDGE_URL);
                return callAPI(DEL_BRIDGE_URL,null,"DELETE");

            case "ADD_PORT":
                bridgeName = args[0];
                portName = args[1];
                String ADD_PORT_URL = URL_PREFIX + "/restconf/config/network-topology:network-topology/topology/" + topologyId + "/node/" + nodeId.replaceAll("\\/", "%2F") + "%2Fbridge%2F" + bridgeName+"/termination-point/" + portName;
                bodyString = InputdataforPost.getAddPortData(portName);
                LOG.info("input post data for adding port: " + bodyString+ " and URL : " + ADD_PORT_URL);
                return callAPI(ADD_PORT_URL,bodyString,"PUT");

            case "DELETE_PORT":
                bridgeName = args[0];
                portName = args[1];
                String DEL_PORT_URL = URL_PREFIX + "/restconf/config/network-topology:network-topology/topology/" + topologyId + "/node/" + nodeId.replaceAll("\\/", "%2F") + "%2Fbridge%2F" + bridgeName+"/termination-point/" + portName;
                LOG.info("Delete port URL : " + DEL_PORT_URL);
                return callAPI(DEL_PORT_URL,null,"DELETE");

            default:
                LOG.error("Unknown Operation");
                throw new RuntimeException("Invalid Operation");
        }


    }


    public static CloseableHttpResponse callAPI(String URL, String bodyString, String protocolType) {
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        switch (protocolType.toUpperCase()){
            case "POST":
            case "PUT":
                try {
                    HttpPut httpPut = new HttpPut(URL);
                    httpPut.addHeader("Authorization", "Basic " + encodedAuthStr);
                    StringEntity inputBody = new StringEntity(bodyString);
                    inputBody.setContentType(CONTENTTYPE_JSON);
                    httpPut.setEntity(inputBody);
                    httpResponse = httpClient.execute(httpPut);
                    LOG.info("HTTP PUT Response Status: " + httpResponse.getStatusLine().toString());
                    LOG.info("HTTP PUT Response Code: " + httpResponse.getStatusLine().getStatusCode());
                } catch (IOException exp) {
                    LOG.error("Exception While Hitting API "+ URL +" : ", exp);
                }
                break;

            case "DELETE" :
                try {
                    HttpDelete httpDel = new HttpDelete(URL);
                    httpDel.addHeader("Authorization", "Basic " + encodedAuthStr);
                    httpResponse = httpClient.execute(httpDel);
                    LOG.info("HTTP DELETE Response Status: " + httpResponse.getStatusLine().toString());
                    LOG.info("HTTP DELETE Response Code: " + httpResponse.getStatusLine().getStatusCode());
                } catch (IOException exp) {
                    LOG.error("Exception While Deleting API "+ URL +" : ", exp);
                }
                break;

        }
        return httpResponse;
    }

}
