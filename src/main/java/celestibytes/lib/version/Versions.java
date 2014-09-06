/*
 * Copyright (C) 2014 Celestibytes
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */

package celestibytes.lib.version;

import java.util.Comparator;

/**
 * An {@link Comparator} that is used to "efficiently" compare two
 * {@link Version}s.
 *
 * @author PizzAna
 * @see Comparator
 * @see Version
 * @see Release
 * @see Snapshot
 */
public final class Versions implements Comparator<Version>
{
    /**
     * The single instance of the {@link Versions}.
     */
    public static final Versions INSTANCE = new Versions();

    /**
     *
     * Constructs a new {@link Versions}.
     *
     */
    private Versions()
    {

    }

    /**
     * Checks if two {@link Version}s are equal.
     * 
     * @param arg0
     *            the first {@link Version}.
     * @param arg1
     *            the second {@link Version}.
     * @return {@code true} if the two {@link Version}s are equal, otherwise
     *         {@code false}.
     */
    public boolean equals(Version arg0, Version arg1)
    {
        return arg0.equals(arg1) && compare(arg0, arg1) == 0;
    }

    /**
     * Compares two {@link Version}s together.
     *
     * @see Comparator#compare
     */
    @Override
    public int compare(Version arg0, Version arg1)
    {
        return arg0.compareTo(arg1); // TODO Not final
    }
}
