/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.query.internal;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.query.Query;
import org.xwiki.query.QueryFilter;

/**
 * Changes the default escape character for LIKE clauses and escape parameters.
 * There are 2 use cases:
 * <ul>
 *   <li>when we need to handle parameters that contain the backslash character ('\') which is considered as the
 *       default escape character on MySQL</li>
 *   <li>to escape % and _ characters in parameter literals</li>
 * </ul>
 *
 * @version $Id$
 * @since 8.4.5
 * @since 9.3RC1
 */
@Component
@Named("escapeLikeParameters")
@Singleton
public class EscapeLikeParametersFilter implements QueryFilter
{
    @Override
    public Query filterQuery(Query query)
    {
        return new EscapeLikeParametersQuery(query);
    }

    @Override
    public String filterStatement(String statement, String language)
    {
        return statement;
    }

    @Override
    public List filterResults(List results)
    {
        return results;
    }
}
