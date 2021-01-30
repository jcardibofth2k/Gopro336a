package com.konasclient.client;

import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

// $FF: renamed from: com.konasclient.client.5
public class class_113 {
   // $FF: renamed from: c kotlin.Lazy
   public static Lazy field_362;

   // $FF: renamed from: c () org.objectweb.asm.tree.InsnList
   public static InsnList method_433() {
      Lazy var0 = field_362;
      Object var1 = null;
      Object var2 = null;
      boolean var3 = false;
      return (InsnList)var0.getValue();
   }

   // $FF: renamed from: c (java.lang.String) byte[]
   @NotNull
   public static byte[] method_434(@NotNull String var0) {
      ClassNode var2 = new ClassNode();
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      var2.name = StringsKt.replace$default(var0, '.', '/', false, 4, (Object)null);
      var2.access = 1;
      var2.version = 52;
      var2.superName = "java/lang/Object";
      MethodNode[] var10001 = new MethodNode[1];
      MethodNode var7 = new MethodNode(9, "<clinit>", "()V", (String)null, (String[])null);
      byte var8 = 0;
      MethodNode[] var9 = var10001;
      MethodNode[] var10 = var10001;
      boolean var12 = false;
      boolean var13 = false;
      boolean var15 = false;
      var7.instructions = method_433();
      var9[var8] = var7;
      var2.methods = (List)CollectionsKt.arrayListOf(var10);
      ClassNode var1 = var2;
      ClassWriter var17 = new ClassWriter(2);
      var3 = false;
      var4 = false;
      var6 = false;
      var1.accept((ClassVisitor)var17);
      return var17.toByteArray();
   }

   static {
      field_362 = LazyKt.lazy((Function0)class_114.field_363);
   }
}
