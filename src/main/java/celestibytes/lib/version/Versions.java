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
 * {@link Version} or it's subtypes ({@link BigVersion}, {@link SemanticVersion},
 * {@link Snapshot}).
 *
 * @author PizzAna
 * @see Version
 * @see BigVersion
 * @see SemanticVersion
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
            
            if (o1.isRelease() && o2.isRelease())
            {
                Release r1 = (Release) o1;
                Release r2 = (Release) o2;
                
                if (r1.getPatch() != r2.getPatch())
                {
                    return r1.getPatch() < r2.getPatch() ? -1 : 1;
                }
                
                if (r1.isStable() && !r2.isStable())
                {
                    return 1;
                }
                
                if (o1 instanceof BigVersion && o2 instanceof BigVersion)
                {
                    BigVersion b1 = (BigVersion) o1;
                    BigVersion b2 = (BigVersion) o2;
                    
                    if (b1.build != b2.build)
                    {
                        return b1.build < b2.build ? -1 : 1;
                    }
                }
                
                if (o1 instanceof SemanticVersion && o2 instanceof SemanticVersion)
                {
                    SemanticVersion s1 = (SemanticVersion) o1;
                    SemanticVersion s2 = (SemanticVersion) o2;
                    
                    if (s1.isAlpha() && !s2.isAlpha())
                    {
                        return -1;
                    }
                    
                    if (s1.isBeta() && s2.isAlpha())
                    {
                        return 1;
                    }
                    
                    if (s1.isBeta() && !s2.isBeta())
                    {
                        return -1;
                    }
                    
                    if (s1.qualifier == null && s2.qualifier != null)
                    {
                        return -1;
                    }
                    
                    if (s1.qualifier.equalsIgnoreCase(s2.qualifier))
                    {
                        if (s1.build != s2.build)
                        {
                            return s1.build < s2.build ? -1 : 1;
                        }
                    }
                }
                
                if (o1 instanceof BigVersion && o2 instanceof SemanticVersion)
                {
                    SemanticVersion s = (SemanticVersion) o2;
                    
                    if (s.qualifier != null)
                    {
                        return 1;
                    }
                }
                
                if (o1 instanceof SemanticVersion && o2 instanceof BigVersion)
                {
                    SemanticVersion s = (SemanticVersion) o1;
                    
                    if (s.qualifier != null)
                    {
                        return -1;
                    }
                }
                
                if (!r1.isStable() && r2.isStable())
                {
                    return -1;
                }
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
