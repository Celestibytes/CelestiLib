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
 *
 * @author PizzAna
 * 
 * @see Versions
 * @see Version
 * @see Release
 */
public class SemanticRelease extends Version implements Release
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
     * Constructs a new {@link SemanticRelease}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     * @param patch
     *            the patch version number.
     */
    public SemanticRelease(String major, String minor, String patch)
    {
        this(Integer.parseInt(major), Integer.parseInt(minor), Integer.parseInt(patch));
    }
    
    /**
     *
     * Constructs a new {@link SemanticRelease}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     * @param patch
     *            the patch version number.
     */
    public SemanticRelease(int major, int minor, int patch)
    {
        this(major, minor, patch, null, 0);
    }
    
    /**
     *
     * Constructs a new {@link SemanticRelease}.
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
    public SemanticRelease(String major, String minor, String patch, String qualifier)
    {
        this(Integer.parseInt(major), Integer.parseInt(minor), Integer.parseInt(patch), qualifier);
    }
    
    /**
     *
     * Constructs a new {@link SemanticRelease}.
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
    public SemanticRelease(int major, int minor, int patch, String qualifier)
    {
        this(major, minor, patch, qualifier, 0);
    }
    
    /**
     *
     * Constructs a new {@link SemanticRelease}.
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
    public SemanticRelease(String major, String minor, String patch, String qualifier, String build)
    {
        this(Integer.parseInt(major), Integer.parseInt(minor), Integer.parseInt(patch), qualifier, Integer
                .parseInt(build));
    }
    
    /**
     *
     * Constructs a new {@link SemanticRelease}.
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
    public SemanticRelease(int major, int minor, int patch, String qualifier, int build)
    {
        super(major, minor);
        this.patch = patch;
        this.qualifier = qualifier;
        this.build = build;
    }
    
    /**
     * Tells if the {@link SemanticRelease} is stable according to the data.
     *
     * @return {@code true} if the {@link SemanticRelease} represents a stable release,
     *         otherwise {@code false}.
     */
    public boolean isStable()
    {
        return qualifier == null;
    }
    
    /**
     * Tells if the {@link SemanticRelease} is beta according to the data.
     *
     * @return {@code true} if the {@link SemanticRelease} represents a beta release,
     *         otherwise {@code false}.
     */
    public boolean isBeta()
    {
        return !isStable() && qualifier.equalsIgnoreCase(Versions.BETA);
    }
    
    /**
     * Tells if the {@link SemanticRelease} is alpha according to the data.
     *
     * @return {@code true} if the {@link SemanticRelease} represents a alpha release,
     *         otherwise {@code false}.
     */
    public boolean isAlpha()
    {
        return !isStable() && qualifier.equalsIgnoreCase(Versions.ALPHA);
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
        
        if (!(o instanceof SemanticRelease))
        {
            // TODO Is this necessary?
        }
        
        if (patch != ((SemanticRelease) o).patch)
        {
            return patch < ((SemanticRelease) o).patch ? -1 : 1;
        }
        
        if (isStable() && !((SemanticRelease) o).isStable())
        {
            return 1;
        }
        
        if (isAlpha() && !((SemanticRelease) o).isAlpha())
        {
            return -1;
        }
        
        if (isBeta() && ((SemanticRelease) o).isAlpha())
        {
            return 1;
        }
        
        if (isBeta() && !((SemanticRelease) o).isBeta())
        {
            return -1;
        }
        
        if (!isStable() && ((SemanticRelease) o).isStable())
        {
            return -1;
        }
        
        if (qualifier == null && ((SemanticRelease) o).qualifier != null)
        {
            return 1;
        }
        
        if (qualifier.equals(((SemanticRelease) o).qualifier))
        {
            if (build != ((SemanticRelease) o).build)
            {
                return build < ((SemanticRelease) o).build ? -1 : 1;
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
        
        if (!(obj instanceof SemanticRelease))
        {
            return false;
        }
        
        SemanticRelease other = (SemanticRelease) obj;
        
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
