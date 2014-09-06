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
public class Snapshot extends Version
{
    /**
     * The snapshot Maven qualifier.
     */
    public static final String SNAPSHOT = "SNAPSHOT";
    
    /**
     *
     * Constructs a new {@link Snapshot}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     */
    public Snapshot(String major, String minor)
    {
        this(Integer.parseInt(major), Integer.parseInt(minor));
    }
    
    /**
     *
     * Constructs a new {@link Snapshot}.
     *
     * @param major
     *            the major version number.
     * @param minor
     *            the minor version number.
     */
    public Snapshot(int major, int minor)
    {
        super(major, minor);
    }
    
    @Override
    public String toString()
    {
        return super.toString() + "-SNAPSHOT";
    }
}
