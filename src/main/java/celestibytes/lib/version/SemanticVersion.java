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
 * A {@link Version} that supports "x.y.z(-qualifier(.build))" format.
 * <p/>
 * A {@link SemanticVersion} with a qualifier is treated as lower level version
 * that a {@link BigVersion}.
 *
 * @author PizzAna
 *
 * @see Versions
 * @see Version
 * @see Release
 */
public class SemanticVersion extends Version implements Release
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
     * Constructs a new {@link SemanticVersion}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     * @param patch
     *            the patch version number.
     */
    public SemanticVersion(String major, String minor, String patch)
    {
        this(Integer.parseInt(major), Integer.parseInt(minor), Integer.parseInt(patch));
    }
    
    /**
     *
     * Constructs a new {@link SemanticVersion}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     * @param patch
     *            the patch version number.
     */
    public SemanticVersion(int major, int minor, int patch)
    {
        this(major, minor, patch, null, 0);
    }
    
    /**
     *
     * Constructs a new {@link SemanticVersion}.
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
    public SemanticVersion(String major, String minor, String patch, String qualifier)
    {
        this(Integer.parseInt(major), Integer.parseInt(minor), Integer.parseInt(patch), qualifier);
    }
    
    /**
     *
     * Constructs a new {@link SemanticVersion}.
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
    public SemanticVersion(int major, int minor, int patch, String qualifier)
    {
        this(major, minor, patch, qualifier, 0);
    }
    
    /**
     *
     * Constructs a new {@link SemanticVersion}.
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
    public SemanticVersion(String major, String minor, String patch, String qualifier, String build)
    {
        this(Integer.parseInt(major), Integer.parseInt(minor), Integer.parseInt(patch), qualifier, Integer
                .parseInt(build));
    }
    
    /**
     *
     * Constructs a new {@link SemanticVersion}.
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
    public SemanticVersion(int major, int minor, int patch, String qualifier, int build)
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
     * Tells if the {@link SemanticVersion} is beta according to the data.
     *
     * @return {@code true} if the {@link SemanticVersion} represents a beta
     *         release, otherwise {@code false}.
     */
    public boolean isBeta()
    {
        return !isStable() && qualifier.equalsIgnoreCase(Versions.BETA);
    }
    
    /**
     * Tells if the {@link SemanticVersion} is alpha according to the data.
     *
     * @return {@code true} if the {@link SemanticVersion} represents a alpha
     *         release, otherwise {@code false}.
     */
    public boolean isAlpha()
    {
        return !isStable() && qualifier.equalsIgnoreCase(Versions.ALPHA);
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
        
        if (!(o instanceof SemanticVersion))
        {
            // TODO Is this necessary?
        }
        
        if (patch != ((SemanticVersion) o).patch)
        {
            return patch < ((SemanticVersion) o).patch ? -1 : 1;
        }
        
        if (isStable() && !((SemanticVersion) o).isStable())
        {
            return 1;
        }
        
        if (isAlpha() && !((SemanticVersion) o).isAlpha())
        {
            return -1;
        }
        
        if (isBeta() && ((SemanticVersion) o).isAlpha())
        {
            return 1;
        }
        
        if (isBeta() && !((SemanticVersion) o).isBeta())
        {
            return -1;
        }
        
        if (!isStable() && ((SemanticVersion) o).isStable())
        {
            return -1;
        }
        
        if (qualifier == null && ((SemanticVersion) o).qualifier != null)
        {
            return 1;
        }
        
        if (qualifier.equals(((SemanticVersion) o).qualifier))
        {
            if (build != ((SemanticVersion) o).build)
            {
                return build < ((SemanticVersion) o).build ? -1 : 1;
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
        result = prime * result + (qualifier == null ? 0 : qualifier.hashCode());
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
        
        if (!(obj instanceof SemanticVersion))
        {
            return false;
        }
        
        SemanticVersion other = (SemanticVersion) obj;
        
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
        return isStable() ? super.toString() + "." + patch : build != 0 ? super.toString() + "." + patch + "-"
                + qualifier + "." + build : super.toString() + "." + patch + "-" + qualifier;
    }
}
