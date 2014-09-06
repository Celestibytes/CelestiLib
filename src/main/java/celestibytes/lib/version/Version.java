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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * An {@link Object} that contains common version information.
 *
 * @author PizzAna
 *
 */
public abstract class Version<V extends Version<V>> implements Comparable<V>
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
     * Parses a {@link Version} from the given {@link String}.
     *
     * @param s
     *            the {@link String} representing the {@link Version}.
     * @return the {@link Version} represented by the {@link String}.
     * @throws VersionFormatException
     *             if the {@link String} does not contain a parsable
     *             {@link Version}.
     */
    @SuppressWarnings("unused")
    public static Version<?> parse(String s) throws VersionFormatException
    {
        String major = "";
        String minor = "";
        int dots = 0;
        boolean hyphen = false;
        
        String patch = "";
        String qualifier = "";
        String build = "";
        
        label:
        for (Character c : s.toCharArray())
        {
            if (c.equals('.'))
            {
                dots = dots + 1;
            }
            else if (c.equals('-'))
            {
                hyphen = true;
            }
            else
            {
                switch (dots)
                {
                    case 0:
                    {
                        major = major + c.charValue();
                        break;
                    }
                    case 1:
                    {
                        if (hyphen)
                        {
                            qualifier = qualifier + c.toString();
                        }
                        else
                        {
                            minor = minor + c.toString();
                        }
                        break;
                    }
                    case 2:
                    {
                        if (hyphen)
                        {
                            qualifier = qualifier + c.toString();
                        }
                        else
                        {
                            patch = patch + c.toString();
                        }
                        break;
                    }
                    case 3:
                    {
                        build = build + c.toString();
                        break;
                    }
                }
            }
        }
        
        if (major.equals("") || minor.equals(""))
        {
            throw new VersionFormatException("Major or minor may not be null");
        }
        
        if (qualifier.equalsIgnoreCase(Snapshot.SNAPSHOT))
        {
            return new Snapshot(major, minor);
        }
        else if (qualifier.equalsIgnoreCase(""))
        {
            return new Release(major, minor, patch);
        }
        else
        {
            if (build.equalsIgnoreCase(""))
            {
                return new Release(major, minor, patch, qualifier);
            }
            else
            {
                return new Release(major, minor, patch, qualifier, build);
            }
        }
    }
    
    /**
     * Parses a {@link Version} from the given {@link URL}.
     * 
     * @param url
     *            the {@link URL}.
     * @return the {@link Version} represented by the {@link URL}.
     * @throws MalformedURLException
     *             if the {@link URL} is invalid.
     * @throws IOException
     *             if the {@link URL} cannot be read.
     * @throws VersionFormatException
     *             if the {@link URL} does not contain a parsable
     *             {@link Version}.
     */
    public static Version<?> parseFromUrl(URL url) throws VersionFormatException, MalformedURLException, IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        Version<?> ret = parse(reader.readLine());
        reader.close();
        return ret;
    }
    
    /**
     * Parses a {@link Version} from the given url.
     * 
     * @param s
     *            the url.
     * @return the {@link Version} represented by the url.
     * @throws MalformedURLException
     *             if the url is invalid.
     * @throws IOException
     *             if the url cannot be read.
     * @throws VersionFormatException
     *             if the url does not contain a parsable {@link Version}.
     */
    public static Version<?> parseFromUrl(String s) throws VersionFormatException, MalformedURLException, IOException
    {
        return parseFromUrl(new URL(s));
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
    public int compareTo(V o)
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
    
    @SuppressWarnings("rawtypes")
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
