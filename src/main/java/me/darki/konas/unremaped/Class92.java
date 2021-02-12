package me.darki.konas.unremaped;

import me.darki.konas.RenderUtil2;

import java.awt.Color;

public class Class92
extends Class90 {
    public static String Field861 = null;

    public Class92() {
        super("Description", 0.0f, 0.0f, 0.0f, 0.0f);
        this.Method440(false);
    }

    @Override
    public void Method101(int n, int n2, float f) {
        block0: {
            super.Method101(n, n2, f);
            if (Field861 == null || Field861.isEmpty()) break block0;
            float f2 = Class548.Method1022(Field861) + 4.0f;
            float f3 = Class548.Method1023(Field861) + 4.0f;
            this.Method445(n);
            this.Method442((float)n2 - f3);
            this.Method918(f2);
            this.Method919(f3);
            RenderUtil2.Method1338(n, (float)n2 - f3, f2, f3, Color.BLACK.hashCode());
            Class548.Method1019(Field861, n + 2, (float)n2 - f3 + 2.0f, Color.WHITE.hashCode());
        }
    }
}
