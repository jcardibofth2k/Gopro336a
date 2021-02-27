# Konas-Deobf-Remap
Konas Client

Deobfuscated and partialy remaped manually

This is a WIP and WILL get better with time.

Not a buildable src

Contributions to improve this will be accepted

```
1st, Deobfuscated

2nd, remped to mincraft mappings

3rd, field and method names renamed to generic and valid names, removing obfuscation via aggresive overloding

4th, Various tweaks, and manual remaping started

5th, Decompiled, using aproximatly 95% CFR, 4.5% Procyon, 0.5% Fernflower

6th, Loaded into gradle project, and Continued manual remap using refactoring tools in Intelij
```

# For Skids

Skid it.

Its not perfect so dont complain when you have errors skiding it into your shit oyvey base

If u can't instantly just copy and paste the module into ur oyvey base and have it work don't cry to us fix it urself (RIP Mint devs in this line)

It is NOT buildable.

As stated earlier, minecraft mappings have already been applied. Dont get the renamed "Field*****" names confused with the minecraft mapping names, or else you will look really dumb.

Skiding is cringe


# For Contributors

Any contributions to this deobf+remap will be GREATLY apperciated. This deobf has many, many, other classes, methods, and fields that still use the Gerneric names. Manually remapping an entire client like this is alot for 1 person to do (I have a life), and can become dulling. Contributions can consist of refactoring of Classes, Fields, and Methods to names that make sense. Intelij Ideas's refactoing tool works well for this.

The reason this is a gradle project even though it is not ment to be built is because that was the easiest way to setup an eviorment with all the dependencies. The deobfuscated files are excluded from the build process.

Read at contribution notes

# What is manual remaping?

Some obfuscators will rename classes, fields(variables), and methods to different names, in order to impede readablility of the code. Sometimes, obfuscators will even rename names to names that are illegal in java, but perfectly legal in bytecode (such as names starting in numbers). Sometimes obfuscators will use a feature called method overloading to overload unrelated methods in order to obfuscate. This can be overcome by renaming all classes, methods, and fields to generic, legal names. Once this is done, readability can be improved by "Manually remaping" these classes, methods, and fields to common sense names that make it look more like a client. Example: ClassXXX, contains string "AutoCrystal" in its super constructor, so the class is renamed to AutoCrystal, and moved to directory "me/darki/konas/module/combat"

# To Darkii, GL_DONT_CARE

No disrespect, but when theres obf, I gotta deobf. 

DMCA = cope, and it will just be reposted by others so no point.

DMCA if ur a pedophile, a registered sex offender, british and if you groom children :^)
