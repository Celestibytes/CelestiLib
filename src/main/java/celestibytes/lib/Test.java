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

import celestibytes.lib.version.BigVersion;
import celestibytes.lib.version.SemanticVersion;
import celestibytes.lib.version.Versions;

public class Test
{
    public static void main(String[] args)
    {
        byte x = (byte) -6;
        
        System.out.println(x);
        System.out.println(Integer.toHexString(x));
        System.out.println(Integer.MIN_VALUE);
        
        System.out.println(Long.toBinaryString(1 << 63));
        System.out.println(Integer.MAX_VALUE);
        
        System.out.println(Versions.parse("2.3.1").toString());
        System.out.println(Versions.parse("2.3-SNAPSHOT").toString());
        System.out.println(Versions.parse("2.3.1-beta.5").toString());
        System.out.println(Versions.parse("2.3.1-alpha").toString());
        System.out.println(Versions.parse("2.3.1.5").toString());
        
        if (Versions.comparator.compare(Versions.parse("2.3.1.5"), Versions.parse("2.3.1-alpha")) > 0)
        {
            System.out.println("Hello World!");
        }
        
        if (Versions.comparator.compare(Versions.parse("1.2.4"), Versions.parse("1.2.3.4")) > 0)
        {
            System.out.println("Swag");
        }
        
        BigVersion b = (BigVersion) Versions.parse("2.2.3.4");
        SemanticVersion s = (SemanticVersion) Versions.parse("2.2.4");
        
        if (Versions.comparator.compare(s, b) > 0)
        {
            System.out.println("Yolo");
        }
        
        if (Versions.parse("1.2.3.4") instanceof BigVersion)
        {
            System.out.println("Pöö");
        }
        
        if (Versions.parse("1.2.3") instanceof SemanticVersion)
        {
            System.out.println("Höö");
        }
    }

}
