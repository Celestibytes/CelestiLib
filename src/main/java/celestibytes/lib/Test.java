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

package celestibytes.lib;

import celestibytes.lib.version.Version;

public class Test
{
    public static void main(String[] args)
    {
        System.out.println(Version.parse("2.3.1").toString());
        System.out.println(Version.parse("2.3-SNAPSHOT").toString());
        System.out.println(Version.parse("2.3.1-beta.5").toString());
        System.out.println(Version.parse("2.3.1-alpha").toString());
    }
    
}
