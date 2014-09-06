/*
 * Copyright (C) 2014 Celestibytes
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package celestibytes.lib.version;

import java.util.Comparator;

/**
 * An {@link Comparator} that is used to "efficiently" compare two {@link Version}s.
 * 
 * @author PizzAna
 * @see Comparator
 */
public final class VersionComparator implements Comparator<Version>
{
    public static final VersionComparator INSTANCE = new VersionComparator();
    
    @Override
    public int compare(Version arg0, Version arg1)
    {
        return arg0.compareTo(arg1); // TODO Not final
    }
    
}