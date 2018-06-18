/*
 * Copyright © 2017 verizon and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.ps.odl.impl.utility;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

class PropReader {
    private Properties props = new Properties();
    private static final Logger LOG = LoggerFactory.getLogger(PropReader.class);
    PropReader() {
        try {
            props.load(getClass().getResourceAsStream("/config.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

     String getProperty(String key) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(props);
        return props.getProperty(key);
    }
}
