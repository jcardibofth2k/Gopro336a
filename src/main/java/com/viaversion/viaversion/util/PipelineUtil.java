package com.viaversion.viaversion.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PipelineUtil {
   private static Method DECODE_METHOD;
   private static Method ENCODE_METHOD;
   private static Method MTM_DECODE;

   public static List callDecode(ByteToMessageDecoder decoder, ChannelHandlerContext ctx, Object input) throws InvocationTargetException {
      ArrayList output = new ArrayList();

      try {
         DECODE_METHOD.invoke(decoder, ctx, input, output);
      } catch (IllegalAccessException var5) {
         var5.printStackTrace();
      }

      return output;
   }

   public static void callEncode(MessageToByteEncoder encoder, ChannelHandlerContext ctx, Object msg, ByteBuf output) throws InvocationTargetException {
      try {
         ENCODE_METHOD.invoke(encoder, ctx, msg, output);
      } catch (IllegalAccessException var5) {
         var5.printStackTrace();
      }

   }

   public static List callDecode(MessageToMessageDecoder decoder, ChannelHandlerContext ctx, Object msg) throws InvocationTargetException {
      ArrayList output = new ArrayList();

      try {
         MTM_DECODE.invoke(decoder, ctx, msg, output);
      } catch (IllegalAccessException var5) {
         var5.printStackTrace();
      }

      return output;
   }

   public static boolean containsCause(Throwable t, Class c) {
      while(t != null) {
         if (c.isAssignableFrom(t.getClass())) {
            return true;
         }

         t = t.getCause();
      }

      return false;
   }

   public static ChannelHandlerContext getContextBefore(String name, ChannelPipeline pipeline) {
      boolean mark = false;
      Iterator var3 = pipeline.names().iterator();

      while(var3.hasNext()) {
         String s = (String)var3.next();
         if (mark) {
            return pipeline.context(pipeline.get(s));
         }

         if (s.equalsIgnoreCase(name)) {
            mark = true;
         }
      }

      return null;
   }

   public static ChannelHandlerContext getPreviousContext(String name, ChannelPipeline pipeline) {
      String previous = null;

      String entry;
      for(Iterator var3 = pipeline.toMap().keySet().iterator(); var3.hasNext(); previous = entry) {
         entry = (String)var3.next();
         if (entry.equals(name)) {
            return pipeline.context(previous);
         }
      }

      return null;
   }

   static {
      try {
         DECODE_METHOD = ByteToMessageDecoder.class.getDeclaredMethod("decode", ChannelHandlerContext.class, ByteBuf.class, List.class);
         DECODE_METHOD.setAccessible(true);
      } catch (NoSuchMethodException var3) {
         var3.printStackTrace();
      }

      try {
         ENCODE_METHOD = MessageToByteEncoder.class.getDeclaredMethod("encode", ChannelHandlerContext.class, Object.class, ByteBuf.class);
         ENCODE_METHOD.setAccessible(true);
      } catch (NoSuchMethodException var2) {
         var2.printStackTrace();
      }

      try {
         MTM_DECODE = MessageToMessageDecoder.class.getDeclaredMethod("decode", ChannelHandlerContext.class, Object.class, List.class);
         MTM_DECODE.setAccessible(true);
      } catch (NoSuchMethodException var1) {
         var1.printStackTrace();
      }

   }
}
