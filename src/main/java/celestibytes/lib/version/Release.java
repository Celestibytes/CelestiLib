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
 * An {@code interface} that is implemented by every release type
 * {@link Version}s for easier comparison.
 * 
 * @author PizzAna
 * 
 * @see Version
 * @see Versions
 * @see SemanticVersion
 * @see BigVersion
 */
public interface Release
{
    /**
     * Gives the patch version number of this {@link Release}.
     * 
     * @return the patch version number.
     */
    public int getPatch();
    
    /**
     * Tells if the {@link Release} is stable according to the data.
     *
     * @return {@code true} if the {@link Release} represents a stable release,
     *         otherwise {@code false}.
     */
    public boolean isStable();
}
