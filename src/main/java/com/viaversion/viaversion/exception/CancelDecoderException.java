package com.viaversion.viaversion.exception;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.exception.CancelDecoderException.1;
import io.netty.handler.codec.DecoderException;

public class CancelDecoderException extends DecoderException implements CancelCodecException {
   public static final CancelDecoderException CACHED = new 1("This packet is supposed to be cancelled; If you have debug enabled, you can ignore these");

   public CancelDecoderException() {
   }

   public CancelDecoderException(String message, Throwable cause) {
      super(message, cause);
   }

   public CancelDecoderException(String message) {
      super(message);
   }

   public CancelDecoderException(Throwable cause) {
      super(cause);
   }

   public static CancelDecoderException generate(Throwable cause) {
      return Via.getManager().isDebug() ? new CancelDecoderException(cause) : CACHED;
   }
}
