/*
 * Copyright © 2017 verizon and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.ps.odl.impl.utility;

public class PropContainer {

    private static PropContainer propContainer = null;

    private String controllerIp;
    private String user;
    private String password;
    private String port;

    private String topologyId;
    private String nodeId;
    private String ovsHostIp;
    private String ovsHostport;

    private PropContainer() {
        PropReader propReader = new PropReader();
        controllerIp = propReader.getProperty("CONTROLLER_IP");
        user = propReader.getProperty("USER");
        password = propReader.getProperty("PASSWORD");
        port = propReader.getProperty("PORT");
        nodeId = propReader.getProperty("NODEID");
        topologyId = propReader.getProperty("TOPOLOGYID");
        ovsHostIp = propReader.getProperty("OVS_HOST_IP");
        ovsHostport = propReader.getProperty("OVS_HOST_PORT");
    }

    public static PropContainer getInstance() {
        if (propContainer == null) {
            propContainer = new PropContainer();
        }
        return propContainer;
    }


    public String getControllerIp() {
        return controllerIp;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getPort() {
        return port;
    }

    public String getTopologyId() {
        return topologyId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getOvsHostIp() {
        return ovsHostIp;
    }

    public String getOvsHostport() {
        return ovsHostport;
    }

    @Override
    public String toString() {
        return "PropContainer{" +
                "controllerIp='" + controllerIp + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", port='" + port + '\'' +
                ", topologyId='" + topologyId + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", ovsHostIp='" + ovsHostIp + '\'' +
                ", ovsHostport='" + ovsHostport + '\'' +
                '}';
    }
}
