/*
 * Copyright (C) 2004 - 2014 Brian McCallister
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.skife.jdbi.v2;

import org.skife.jdbi.v2.tweak.StatementBuilder;
import org.skife.jdbi.v2.tweak.StatementBuilderFactory;

import java.sql.Connection;

/**
 *
 */
public class DefaultStatementBuilderFactory implements StatementBuilderFactory
{
    /**
     * Obtain a StatementBuilder, called when a new handle is opened
     */
    @Override
    public StatementBuilder createStatementBuilder(Connection conn)
    {
        return new DefaultStatementBuilder();
    }
}