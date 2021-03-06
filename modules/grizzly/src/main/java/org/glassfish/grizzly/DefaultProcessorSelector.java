/*
 * Copyright (c) 2008, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.glassfish.grizzly;

/**
 * Default {@link ProcessorSelector} implementation, which uses {@link Connection}'s {@link Processor} preferences. The
 * {@link DefaultProcessorSelector} first checks {@link Connection}'s associated {@link Processor}
 * ({@link Connection#getProcessor()}). If returned {@link Processor} is <tt>null</tt> - if delegates selection to
 * {@link Connection}'s {@link ProcessorSelector} ({@link Connection#getProcessorSelector()}).
 *
 * @author Alexey Stashok
 */
public class DefaultProcessorSelector implements ProcessorSelector {

    protected final Transport transport;

    public DefaultProcessorSelector(Transport transport) {
        this.transport = transport;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Processor select(IOEvent ioEvent, Connection connection) {

        Processor eventProcessor = connection.getProcessor();

        if (eventProcessor != null && eventProcessor.isInterested(ioEvent)) {
            return eventProcessor;
        }

        ProcessorSelector processorSelector = connection.getProcessorSelector();

        if (processorSelector != null) {
            return processorSelector.select(ioEvent, connection);
        }

        return null;
    }
}
