/*
 * Copyright © 2017 verizon and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.ps.odl.impl.utility;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InputdataforPost {
    private static final Logger LOG = LoggerFactory.getLogger(InputdataforPost.class);

    public static String getAddBridgeData(String nodeId, String bridgeName){
        String add_bridge_data = getPostData("add_bridge_data");
        return add_bridge_data.replaceAll("\\$nodeid",nodeId).replaceAll("\\$bridge",bridgeName);
    }


    public static String getOvsHostData(String nodeId, String ovsHostIp, String ovsHostPort) {
        String put_ovs_host_data = getPostData("add_ovs_host_data");
        return put_ovs_host_data.replaceAll("\\$nodeid",nodeId).replaceAll("\\$ovs_host_port",ovsHostPort).replaceAll("\\$ovs_host_ip",ovsHostIp);
    }

    public static String getAddPortData(String portName) {
       String add_port_data = getPostData("add_port_data");
       return add_port_data.replaceAll("\\$port_name",portName);
    }

    private static String getPostData(String templateName) {
        String fileName = "/"+templateName+".json";

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(InputdataforPost.class.getResourceAsStream(fileName)));
            StringBuffer contents = new StringBuffer();
            String text;
            while ((text = br.readLine()) != null) {
                contents.append(text).append(System.getProperty("line.separator"));
            }
            br.close();
            return String.valueOf(contents);

        } catch (Exception exp) {
            LOG.error("Exception while reading input data file : " , exp);
            return null;
        }
    }
}
