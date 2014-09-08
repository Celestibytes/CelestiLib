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
 * <p/>
 * A {@link BigVersion} is always treated as a higher level version than a {@link SemanticVersion} with qualifier.
 * 
 * @author PizzAna
 * 
 * @see Versions
 * @see Version
 * @see Release
 */
public class BigVersion extends Version implements Release
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
     * Constructs a new {@link BigVersion}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     * @param patch
     *            the patch version number.
     */
    public BigVersion(String major, String minor, String patch)
    {
        this(Integer.parseInt(major), Integer.parseInt(minor), Integer.parseInt(patch));
    }
    
    /**
     *
     * Constructs a new {@link BigVersion}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     * @param patch
     *            the patch version number.
     */
    public BigVersion(int major, int minor, int patch)
    {
        this(major, minor, patch, 0);
    }
    
    /**
     *
     * Constructs a new {@link BigVersion}.
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
    public BigVersion(String major, String minor, String patch, String build)
    {
        this(Integer.parseInt(major), Integer.parseInt(minor), Integer.parseInt(patch), Integer.parseInt(build));
    }
    
    /**
     *
     * Constructs a new {@link BigVersion}.
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
    public BigVersion(int major, int minor, int patch, int build)
    {
        super(major, minor);
        this.patch = patch;
        this.build = build;
    }
    
    /**
     * Gives the patch version number of this {@link Release}.
     * 
     * @return the patch version number.
     */
    @Override
    public int getPatch()
    {
        return patch;
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
        
        if (!(o instanceof BigVersion))
        {
            // TODO Is this necessary?
        }
        
        if (patch != ((BigVersion) o).patch)
        {
            return patch < ((BigVersion) o).patch ? -1 : 1;
        }
        
        if (build != ((BigVersion) o).build)
        {
            return build < ((BigVersion) o).build ? -1 : 1;
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
        
        if (!(obj instanceof BigVersion))
        {
            return false;
        }
        
        BigVersion other = (BigVersion) obj;
        
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
