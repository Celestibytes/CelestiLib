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
 * A {@link Version} that supports "x.y.z.build" format.
 * 
 * @author PizzAna
 * 
 * @see Versions
 * @see Version
 * @see Release
 */
public class BigRelease extends Version implements Release
{
    /**
     * The patch version number.
     */
    public final int patch;
    
    /**
     * The version's build.
     */
    public final int build;
    
    /**
     *
     * Constructs a new {@link BigRelease}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     * @param patch
     *            the patch version number.
     */
    public BigRelease(String major, String minor, String patch)
    {
        this(Integer.parseInt(major), Integer.parseInt(minor), Integer.parseInt(patch));
    }
    
    /**
     *
     * Constructs a new {@link BigRelease}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     * @param patch
     *            the patch version number.
     */
    public BigRelease(int major, int minor, int patch)
    {
        this(major, minor, patch, 0);
    }
    
    /**
     *
     * Constructs a new {@link BigRelease}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     * @param patch
     *            the patch version number.
     * @param build
     *            the version build number.
     */
    public BigRelease(String major, String minor, String patch, String build)
    {
        this(Integer.parseInt(major), Integer.parseInt(minor), Integer.parseInt(patch), Integer.parseInt(build));
    }
    
    /**
     *
     * Constructs a new {@link BigRelease}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     * @param patch
     *            the patch version number.
     * @param build
     *            the version build number.
     */
    public BigRelease(int major, int minor, int patch, int build)
    {
        super(major, minor);
        this.patch = patch;
        this.build = build;
    }
    
    /**
     * Tells if the {@link BigRelease} is stable according to the data.
     *
     * @return {@code true} if the {@link BigRelease} represents a stable
     *         release, otherwise {@code false}.
     */
    public boolean isStable()
    {
        return build == 0;
    }
    
    @Override
    public int compareTo(Version o)
    {
        if (o == null)
        {
            throw new NullPointerException();
        }
        
        if (super.compareTo(o) != 0)
        {
            return super.compareTo(o);
        }
        
        if (!(o instanceof BigRelease))
        {
            // TODO Is this necessary?
        }
        
        if (patch != ((BigRelease) o).patch)
        {
            return patch < ((BigRelease) o).patch ? -1 : 1;
        }
        
        if (isStable() && !((BigRelease) o).isStable())
        {
            return 1;
        }
        
        if (!isStable() && ((BigRelease) o).isStable())
        {
            return -1;
        }
        
        if (build != ((BigRelease) o).build)
        {
            return build < ((BigRelease) o).build ? -1 : 1;
        }
        
        return 0;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + build;
        result = prime * result + patch;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        
        if (!super.equals(obj))
        {
            return false;
        }
        
        if (!(obj instanceof BigRelease))
        {
            return false;
        }
        
        BigRelease other = (BigRelease) obj;
        
        if (build != other.build)
        {
            return false;
        }
        
        if (patch != other.patch)
        {
            return false;
        }
        
        return true;
    }
    
    @Override
    public String toString()
    {
        return super.toString() + "." + patch + "." + build;
    }
}
