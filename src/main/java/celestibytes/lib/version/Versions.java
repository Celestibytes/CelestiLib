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
import java.util.Comparator;

/**
 * An {@link Object} that contains {@link Version}-related utility methods.
 * <p/>
 * You should always use the methods in this class instead of methods in
 * {@link Version} or it's subtypes ({@link BigVersion}, {@link SemanticVersion}, {@link Snapshot}).
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
    public static final Comparator<Version> comparator = new Comparator<Version>()
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
                if (o1 instanceof BigVersion && o2 instanceof BigVersion)
                {
                    BigVersion b1 = (BigVersion) o1;
                    BigVersion b2 = (BigVersion) o2;
                    
                    if (b1.getPatch() != b2.getPatch())
                    {
                        return b1.getPatch() < b2.getPatch() ? -1 : 1;
                    }
                    
                    if (b1.build != b2.build)
                    {
                        return b1.build < b2.build ? -1 : 1;
                    }
                }
                else if (o1 instanceof SemanticVersion && o2 instanceof SemanticVersion)
                {
                    SemanticVersion s1 = (SemanticVersion) o1;
                    SemanticVersion s2 = (SemanticVersion) o2;
                    
                    if (s1.getPatch() != s2.getPatch())
                    {
                        return s1.getPatch() < s2.getPatch() ? -1 : 1;
                    }
                    
                    if (s1.isStable() && !s2.isStable())
                    {
                        return 1;
                    }
                    
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
                    
                    if (!s1.isStable() && s2.isStable())
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
                else if (o1 instanceof BigVersion && o2 instanceof SemanticVersion)
                {
                    BigVersion b = (BigVersion) o1;
                    SemanticVersion s = (SemanticVersion) o2;
                    
                    if (b.patch != s.patch)
                    {
                        return b.patch < s.patch ? -1 : 1;
                    }
                    
                    if (s.qualifier != null)
                    {
                        return 1;
                    }
                    
                    if (b.build > 0)
                    {
                        return 1;
                    }
                }
                else if (o1 instanceof SemanticVersion && o2 instanceof BigVersion)
                {
                    SemanticVersion s = (SemanticVersion) o1;
                    BigVersion b = (BigVersion) o2;
                    
                    if (s.patch != b.patch)
                    {
                        return s.patch < b.patch ? -1 : 1;
                    }
                    
                    if (s.qualifier != null)
                    {
                        return -1;
                    }
                    
                    if (b.build > 0)
                    {
                        return -1;
                    }
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
     * Parses a {@link Version} from the given {@link String}.
     *
     * @param s
     *            the {@link String} representing the {@link Version}.
     * @return the {@link Version} represented by the {@link String}.
     * @throws VersionFormatException
     *             if the {@link String} does not contain a parsable
     *             {@link Version}.
     */
    public static Version parse(String s)
    {
        String major = "";
        String minor = "";
        int dots = 0;
        boolean hyphen = false;
        
        String patch = "";
        String qualifier = "";
        String build = "";
        
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
        
        if (qualifier.equalsIgnoreCase(Versions.SNAPSHOT))
        {
            return new Snapshot(major, minor);
        }
        else if (qualifier.equalsIgnoreCase(""))
        {
            if (build.equalsIgnoreCase(""))
            {
                return new SemanticVersion(major, minor, patch);
            }
            else
            {
                return new BigVersion(major, minor, patch, build);
            }
        }
        else
        {
            if (build.equalsIgnoreCase(""))
            {
                return new SemanticVersion(major, minor, patch, qualifier);
            }
            else
            {
                return new SemanticVersion(major, minor, patch, qualifier, build);
            }
        }
    }
    
    /**
     * Parses a {@link Version} from the given {@link URL}.
     *
     * @param url
     *            the {@link URL}.
     * @return the {@link Version} represented by the {@link URL}.
     * @throws IOException
     *             if the {@link URL} cannot be read.
     * @throws VersionFormatException
     *             if the {@link URL} does not contain a parsable
     *             {@link Version}.
     */
    public static Version parseFromUrl(URL url) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        Version ret = parse(reader.readLine());
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
    public static Version parseFromUrl(String s) throws MalformedURLException, IOException
    {
        return parseFromUrl(new URL(s));
    }
    
    /**
     * Gives the hash code of the {@link Version}.
     *
     * @param v
     *            the {@link Version}.
     * @return the hash code of the {@link Version}.
     * @see Version#hashCode()
     * @see BigVersion#hashCode()
     * @see SemanticVersion#hashCode()
     * @see Snapshot#hashCode()
     * @see Object#hashCode()
     */
    public static int hashCode(Version v)
    {
        return v.hashCode();
    }
    
    /**
     * Checks if two {@link Version}s are equal.
     *
     * @param v
     *            the first {@link Version}.
     * @param v1
     *            the second {@link Version}.
     * @return {@code true} if the two {@link Version}s are equal, otherwise
     *         {@code false}.
     * @see Version#equals(Object)
     * @see BigVersion#equals(Object)
     * @see SemanticVersion#equals(Object)
     * @see Snapshot#equals(Object)
     * @see Object#equals(Object)
     */
    public static boolean equals(Version v, Version v1)
    {
        return v.equals(v1) && comparator.compare(v, v1) == 0 || v == null && v1 == null;
    }
    
    /**
     * Returns a {@link String} that represents the given {@link Version}.
     *
     * @param v
     *            the {@link Version}.
     * @return the {@link String} representing the {@link Version}.
     * @see Version#toString()
     * @see BigVersion#toString()
     * @see SemanticVersion#toString()
     * @see Snapshot#toString()
     * @see Object#toString()
     */
    public static String toString(Version v)
    {
        return v == null ? "" : v.toString();
    }
}
