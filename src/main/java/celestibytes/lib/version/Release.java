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
 * An {@link Object}
 * 
 * @author PizzAna
 *
 */
public class Release extends Version<Release>
{
    /**
     * The patch version number.
     */
    public final int patch;
    
    /**
     * The version qualifier.
     */
    public final String qualifier;
    
    /**
     * The version's build.
     */
    public final int build;
    
    /**
     *
     * Constructs a new {@link Release}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     * @param patch
     *            the patch version number.
     */
    public Release(String major, String minor, String patch)
    {
        this(Integer.parseInt(major), Integer.parseInt(minor), Integer.parseInt(patch));
    }
    
    /**
     *
     * Constructs a new {@link Release}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     * @param patch
     *            the patch version number.
     */
    public Release(int major, int minor, int patch)
    {
        this(major, minor, patch, null, 0);
    }
    
    /**
     *
     * Constructs a new {@link Release}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     * @param patch
     *            the patch version number.
     * @param qualifier
     *            the version qualifier.
     */
    public Release(String major, String minor, String patch, String qualifier)
    {
        this(Integer.parseInt(major), Integer.parseInt(minor), Integer.parseInt(patch), qualifier);
    }
    
    /**
     *
     * Constructs a new {@link Release}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     * @param patch
     *            the patch version number.
     * @param qualifier
     *            the version qualifier.
     */
    public Release(int major, int minor, int patch, String qualifier)
    {
        this(major, minor, patch, qualifier, 0);
    }
    
    /**
     *
     * Constructs a new {@link Release}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     * @param patch
     *            the patch version number.
     * @param qualifier
     *            the version qualifier.
     * @param build
     *            the version's build.
     */
    public Release(String major, String minor, String patch, String qualifier, String build)
    {
        this(Integer.parseInt(major), Integer.parseInt(minor), Integer.parseInt(patch), qualifier, Integer
                .parseInt(build));
    }
    
    /**
     *
     * Constructs a new {@link Release}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     * @param patch
     *            the patch version number.
     * @param qualifier
     *            the version qualifier.
     * @param build
     *            the version's build.
     */
    public Release(int major, int minor, int patch, String qualifier, int build)
    {
        super(major, minor);
        this.patch = patch;
        this.qualifier = qualifier;
        this.build = build;
    }
    
    /**
     * Tells if the {@link Release} is stable according to the data.
     *
     * @return {@code true} if the {@link Release} represents a stable release,
     *         otherwise {@code false}.
     */
    public boolean isStable()
    {
        return qualifier == null;
    }
    
    /**
     * Tells if the {@link Release} is beta according to the data.
     *
     * @return {@code true} if the {@link Release} represents a beta release,
     *         otherwise {@code false}.
     */
    public boolean isBeta()
    {
        return qualifier.equalsIgnoreCase("beta");
    }
    
    /**
     * Tells if the {@link Release} is alpha according to the data.
     *
     * @return {@code true} if the {@link Release} represents a alpha release,
     *         otherwise {@code false}.
     */
    public boolean isAlpha()
    {
        return qualifier.equalsIgnoreCase("alpha");
    }
    
    @Override
    public int compareTo(Release o)
    {
        if (o == null)
        {
            throw new NullPointerException();
        }
        
        if (super.compareTo(o) != 0)
        {
            return super.compareTo(o);
        }
        
        if (patch != o.patch)
        {
            return patch < o.patch ? -1 : 1;
        }
        
        if (isStable() && !o.isStable())
        {
            return 1;
        }
        
        if (isAlpha() && o.isSnapshot())
        {
            return 1;
        }
        
        if (isAlpha() && !o.isAlpha())
        {
            return -1;
        }
        
        if (isBeta() && o.isSnapshot())
        {
            return 1;
        }
        
        if (isBeta() && o.isAlpha())
        {
            return 1;
        }
        
        if (isBeta() && !o.isBeta())
        {
            return -1;
        }
        
        if (!isStable() && o.isStable())
        {
            return -1;
        }
        
        if (qualifier.equals(o.qualifier))
        {
            if (build != o.build)
            {
                return build < o.build ? -1 : 1;
            }
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
        result = prime * result + ((qualifier == null) ? 0 : qualifier.hashCode());
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
        
        if (!(obj instanceof Release))
        {
            return false;
        }
        
        Release other = (Release) obj;
        
        if (build != other.build)
        {
            return false;
        }
        
        if (patch != other.patch)
        {
            return false;
        }
        
        if (qualifier == null)
        {
            if (other.qualifier != null)
            {
                return false;
            }
        }
        else if (!qualifier.equals(other.qualifier))
        {
            return false;
        }
        
        return true;
    }
    
    @Override
    public String toString()
    {
        return isStable() ? major + "." + minor + "." + patch : (build != 0 ? major + "." + minor + "." + patch + "-"
                + qualifier + "." + build : major + "." + minor + "." + patch + "-" + qualifier);
    }
}
