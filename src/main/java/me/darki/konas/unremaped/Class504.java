package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Class504 {
    public PriorityQueue<Class506> Field1370 = new PriorityQueue(Class504::Method1440);

    public void Method1436() {
        this.Field1370.clear();
    }

    public boolean Method1437(Class498 class498, float f) {
        return this.Field1370.add(new Class506(this, class498, f));
    }

    public PriorityQueue<Class506> Method1438() {
        return this.Field1370;
    }

    public Class498 Method1439() {
        return Class506.Method1358(this.Field1370.poll());
    }

    public static int Method1440(Class506 class506, Class506 class5062) {
        return Float.compare(Class506.Method1359(class506), Class506.Method1359(class5062));
    }

    public Class498[] Method1441() {
        Class498[] class498Array = new Class498[this.Field1370.size()];
        Iterator<Class506> iterator = this.Field1370.iterator();
        for (int i = 0; i < this.Field1370.size() && iterator.hasNext(); ++i) {
            class498Array[i] = Class506.Method1358(iterator.next());
        }
        return class498Array;
    }
}