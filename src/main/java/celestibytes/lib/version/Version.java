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

/**
 * An {@link Object} that contains common version information.
 * <p/>
 * You should use methods in {@link Versions} instead of ones in this class or
 * subtypes.
 *
 * @author PizzAna
 *
 * @see Versions
 */
public abstract class Version implements Comparable<Version>
{
    /**
     * The major version number.
     */
    public final int major;
    
    /**
     * The minor version number.
     */
    public final int minor;
    
    /**
     *
     * Constructs a new {@link Version}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     */
    public Version(String major, String minor)
    {
        this(Integer.parseInt(major), Integer.parseInt(minor));
    }
    
    /**
     *
     * Constructs a new {@link Version}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     */
    public Version(int major, int minor)
    {
        this.major = major;
        this.minor = minor;
    }
    
    /**
     * Tells if the {@link Version} is a release according to the data.
     *
     * @return {@code true} if the {@link Version} represents a release,
     *         otherwise {@code false}.
     */
    public boolean isRelease()
    {
        return this instanceof Release;
    }
    
    /**
     * Tells if the {@link Version} is a snapshot according to the data.
     *
     * @return {@code true} if the {@link Version} represents a snapshot
     *         release, otherwise {@code false}.
     */
    public boolean isSnapshot()
    {
        return this instanceof Snapshot;
    }
    
    @Override
    public int compareTo(Version o)
    {
        if (o == null)
        {
            throw new NullPointerException();
        }
        
        if (major != o.major)
        {
            return major < o.major ? -1 : 1;
        }
        
        if (minor != o.minor)
        {
            return minor < o.minor ? -1 : 1;
        }
        
        if (isSnapshot() && !o.isSnapshot())
        {
            return -1;
        }
        
        if (isRelease() && !o.isRelease())
        {
            return -1;
        }
        
        return 0;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + major;
        result = prime * result + minor;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        
        if (obj == null)
        {
            return false;
        }
        
        if (!(obj instanceof Version))
        {
            return false;
        }
        
        Version other = (Version) obj;
        
        if (major != other.major)
        {
            return false;
        }
        
        if (minor != other.minor)
        {
            return false;
        }
        
        return true;
    }
    
    @Override
    public String toString()
    {
        return major + "." + minor;
    }
}
