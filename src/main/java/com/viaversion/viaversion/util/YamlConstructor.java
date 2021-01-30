package com.viaversion.viaversion.util;

import java.util.concurrent.ConcurrentSkipListMap;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;

public class YamlConstructor extends SafeConstructor {
   public YamlConstructor() {
      this.yamlClassConstructors.put(NodeId.mapping, new SafeConstructor.ConstructYamlMap());
      this.yamlConstructors.put(Tag.OMAP, new YamlConstructor.ConstructYamlOmap());
   }

   class ConstructYamlOmap extends SafeConstructor.ConstructYamlOmap {
      ConstructYamlOmap() {
         super();
      }

      public Object construct(Node node) {
         Object o = super.construct(node);
         return o instanceof com.viaversion.viaversion.util.YamlConstructor.Map && !(o instanceof ConcurrentSkipListMap) ? new ConcurrentSkipListMap((java.util.Map)o) : o;
      }
   }
}
