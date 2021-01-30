package com.konasclient.client;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

// $FF: renamed from: com.konasclient.client.4
public class class_114 extends Lambda implements Function0 {
   // $FF: renamed from: c com.konasclient.client.4
   public static class_114 field_363 = new class_114();

   public Object invoke() {
      return this.method_435();
   }

   // $FF: renamed from: c () org.objectweb.asm.tree.InsnList
   @NotNull
   public InsnList method_435() {
      InsnList var1 = new InsnList();
      boolean var2 = false;
      boolean var3 = false;
      boolean var5 = false;
      var1.add((AbstractInsnNode)(new FieldInsnNode(178, "java/lang/System", "out", "Ljava/io/PrintStream;")));
      var1.add((AbstractInsnNode)(new LdcInsnNode("Nice try")));
      var1.add((AbstractInsnNode)(new MethodInsnNode(182, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)));
      var1.add((AbstractInsnNode)(new TypeInsnNode(187, "java/lang/Throwable")));
      var1.add((AbstractInsnNode)(new InsnNode(89)));
      var1.add((AbstractInsnNode)(new LdcInsnNode("owned")));
      var1.add((AbstractInsnNode)(new MethodInsnNode(183, "java/lang/Throwable", "<init>", "(Ljava/lang/String;)V", false)));
      var1.add((AbstractInsnNode)(new InsnNode(191)));
      return var1;
   }

   public class_114() {
      super(0);
   }
}
