package com.viaversion.viaversion.api.protocol;

import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ProtocolPipeline extends SimpleProtocol {
   void add(Protocol var1);

   void add(Collection var1);

   boolean contains(Class var1);

   @Nullable
   Protocol getProtocol(Class var1);

   List pipes();

   boolean hasNonBaseProtocols();

   void cleanPipes();
}
