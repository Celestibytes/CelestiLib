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
 * An {@link Object} that contains {@link Version}-related utility methods.
 * <p/>
 * You should always use the methods in this class instead of methods in
 * {@link Version} or it's subtypes ({@link BigRelease}, {@link SemanticRelease},
 * {@link Snapshot}).
 *
 * @author PizzAna
 * @see Version
 * @see BigRelease
 * @see SemanticRelease
 * @see Snapshot
 */
public final class Versions
{
    /**
     * The {@link Version}'s alpha qualifier.
     */
    public static final String ALPHA = "alpha";
    
    /**
     * The {@link Version}'s beta qualifier.
     */
    public static final String BETA = "beta";
    
    /**
     * The {@link Version}'s snapshot qualifier.
     */
    public static final String SNAPSHOT = "SNAPSHOT";
    
    /**
     * An {@link Comparator} that is used to compare two {@link Version}s.
     * 
     * @see Comparator
     */
    public static Comparator<Version> comparator = new Comparator<Version>()
    {
        /**
         * Compares two {@link Version}s together.
         *
         * @see Comparator#compare(Object, Object)
         */
        @Override
        public int compare(Version o1, Version o2)
        {
            if (o1 == null && o2 != null)
            {
                return -1;
            }
            
            if (o1 != null && o2 == null)
            {
                return 1;
            }
            
            if (o1.major != o2.major)
            {
                return o1.major < o2.major ? -1 : 1;
            }
            
            if (o1.minor != o2.minor)
            {
                return o1.minor < o2.minor ? -1 : 1;
            }
            
            if (o1.isSnapshot() && !o2.isSnapshot())
            {
                return -1;
            }
            
            if (o1.isRelease() && !o2.isRelease())
            {
                return 1;
            }
            
            if (true)
            {
                // TODO Remove. This is here to prevent annoying errors.
            }
            
            return 0;
        }
    };
    
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
    public static boolean equals(Version arg0, Version arg1)
    {
        return (arg0.equals(arg1) && comparator.compare(arg0, arg1) == 0) || arg0 == null && arg1 == null;
    }
    
    /**
     * Returns a {@link String} that represents the given {@link Version}.
     * 
     * @param version
     *            the {@link Version}.
     * @return the {@link String} representing the {@link Version}.
     */
    public static String toString(Version version)
    {
        return version == null ? "" : version.toString();
    }
}
