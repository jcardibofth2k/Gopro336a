package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonDeserializationContext;
import com.viaversion.viaversion.libs.gson.JsonDeserializer;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.JsonSerializationContext;
import com.viaversion.viaversion.libs.gson.JsonSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.jetbrains.annotations.Nullable;

final class ComponentSerializerImpl implements JsonDeserializer, JsonSerializer {
   static final String TEXT = "text";
   static final String TRANSLATE = "translate";
   static final String TRANSLATE_WITH = "with";
   static final String SCORE = "score";
   static final String SCORE_NAME = "name";
   static final String SCORE_OBJECTIVE = "objective";
   static final String SCORE_VALUE = "value";
   static final String SELECTOR = "selector";
   static final String KEYBIND = "keybind";
   static final String EXTRA = "extra";
   static final String NBT = "nbt";
   static final String NBT_INTERPRET = "interpret";
   static final String NBT_BLOCK = "block";
   static final String NBT_ENTITY = "entity";
   static final String NBT_STORAGE = "storage";
   static final String SEPARATOR = "separator";

   public Component deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
      return this.deserialize0(json, context);
   }

   private BuildableComponent deserialize0(final JsonElement element, final JsonDeserializationContext context) throws JsonParseException {
      if (element.isJsonPrimitive()) {
         return Component.text(element.getAsString());
      } else if (element.isJsonArray()) {
         ComponentBuilder parent = null;
         Iterator var23 = element.getAsJsonArray().iterator();

         while(var23.hasNext()) {
            JsonElement childElement = (JsonElement)var23.next();
            BuildableComponent child = this.deserialize0(childElement, context);
            if (parent == null) {
               parent = child.toBuilder();
            } else {
               parent.append((Component)child);
            }
         }

         if (parent == null) {
            throw notSureHowToDeserialize(element);
         } else {
            return parent.build();
         }
      } else if (!element.isJsonObject()) {
         throw notSureHowToDeserialize(element);
      } else {
         JsonObject object = element.getAsJsonObject();
         Object component;
         if (object.has("text")) {
            component = Component.text().content(object.get("text").getAsString());
         } else {
            String nbt;
            if (object.has("translate")) {
               nbt = object.get("translate").getAsString();
               if (!object.has("with")) {
                  component = Component.translatable().key(nbt);
               } else {
                  JsonArray with = object.getAsJsonArray("with");
                  List args = new ArrayList(with.size());
                  int i = 0;

                  for(int size = with.size(); i < size; ++i) {
                     JsonElement argElement = with.get(i);
                     args.add(this.deserialize0(argElement, context));
                  }

                  component = Component.translatable().key(nbt).args((List)args);
               }
            } else if (object.has("score")) {
               JsonObject score = object.getAsJsonObject("score");
               if (!score.has("name") || !score.has("objective")) {
                  throw new JsonParseException("A score component requires a name and objective");
               }

               ScoreComponent.Builder builder = Component.score().name(score.get("name").getAsString()).objective(score.get("objective").getAsString());
               if (score.has("value")) {
                  component = builder.value(score.get("value").getAsString());
               } else {
                  component = builder;
               }
            } else if (object.has("selector")) {
               Component separator = this.deserializeSeparator(object, context);
               component = Component.selector().pattern(object.get("selector").getAsString()).separator(separator);
            } else if (object.has("keybind")) {
               component = Component.keybind().keybind(object.get("keybind").getAsString());
            } else {
               if (!object.has("nbt")) {
                  throw notSureHowToDeserialize(element);
               }

               nbt = object.get("nbt").getAsString();
               boolean interpret = object.has("interpret") && object.getAsJsonPrimitive("interpret").getAsBoolean();
               Component separator = this.deserializeSeparator(object, context);
               if (object.has("block")) {
                  BlockNBTComponent.Pos pos = (BlockNBTComponent.Pos)context.deserialize(object.get("block"), BlockNBTComponent.Pos.class);
                  component = ((BlockNBTComponent.Builder)nbt(Component.blockNBT(), nbt, interpret, separator)).pos(pos);
               } else if (object.has("entity")) {
                  component = ((EntityNBTComponent.Builder)nbt(Component.entityNBT(), nbt, interpret, separator)).selector(object.get("entity").getAsString());
               } else {
                  if (!object.has("storage")) {
                     throw notSureHowToDeserialize(element);
                  }

                  component = ((StorageNBTComponent.Builder)nbt(Component.storageNBT(), nbt, interpret, separator)).storage((Key)context.deserialize(object.get("storage"), Key.class));
               }
            }
         }

         if (object.has("extra")) {
            JsonArray extra = object.getAsJsonArray("extra");
            int i = 0;

            for(int size = extra.size(); i < size; ++i) {
               JsonElement extraElement = extra.get(i);
               ((ComponentBuilder)component).append((Component)this.deserialize0(extraElement, context));
            }
         }

         Style style = (Style)context.deserialize(element, Style.class);
         if (!style.isEmpty()) {
            ((ComponentBuilder)component).style(style);
         }

         return ((ComponentBuilder)component).build();
      }
   }

   @Nullable
   private Component deserializeSeparator(final JsonObject json, final JsonDeserializationContext context) {
      return json.has("separator") ? this.deserialize0(json.get("separator"), context) : null;
   }

   private static NBTComponentBuilder nbt(final NBTComponentBuilder builder, final String nbt, final boolean interpret, @Nullable final Component separator) {
      return builder.nbtPath(nbt).interpret(interpret).separator(separator);
   }

   public JsonElement serialize(final Component src, final Type typeOfSrc, final JsonSerializationContext context) {
      JsonObject object = new JsonObject();
      if (src.hasStyling()) {
         JsonElement style = context.serialize(src.style());
         if (style.isJsonObject()) {
            Iterator var6 = ((JsonObject)style).entrySet().iterator();

            while(var6.hasNext()) {
               Entry entry = (Entry)var6.next();
               object.add((String)entry.getKey(), (JsonElement)entry.getValue());
            }
         }
      }

      List children = src.children();
      if (!children.isEmpty()) {
         JsonArray extra = new JsonArray();
         Iterator var14 = children.iterator();

         while(var14.hasNext()) {
            Component child = (Component)var14.next();
            extra.add(context.serialize(child));
         }

         object.add("extra", extra);
      }

      if (src instanceof TextComponent) {
         object.addProperty("text", ((TextComponent)src).content());
      } else if (src instanceof TranslatableComponent) {
         TranslatableComponent tc = (TranslatableComponent)src;
         object.addProperty("translate", tc.key());
         if (!tc.args().isEmpty()) {
            JsonArray with = new JsonArray();
            Iterator var19 = tc.args().iterator();

            while(var19.hasNext()) {
               Component arg = (Component)var19.next();
               with.add(context.serialize(arg));
            }

            object.add("with", with);
         }
      } else if (src instanceof ScoreComponent) {
         ScoreComponent sc = (ScoreComponent)src;
         JsonObject score = new JsonObject();
         score.addProperty("name", sc.name());
         score.addProperty("objective", sc.objective());
         String value = sc.value();
         if (value != null) {
            score.addProperty("value", value);
         }

         object.add("score", score);
      } else if (src instanceof SelectorComponent) {
         SelectorComponent sc = (SelectorComponent)src;
         object.addProperty("selector", sc.pattern());
         this.serializeSeparator(context, object, sc.separator());
      } else if (src instanceof KeybindComponent) {
         object.addProperty("keybind", ((KeybindComponent)src).keybind());
      } else {
         if (!(src instanceof NBTComponent)) {
            throw notSureHowToSerialize(src);
         }

         NBTComponent nc = (NBTComponent)src;
         object.addProperty("nbt", nc.nbtPath());
         object.addProperty("interpret", nc.interpret());
         if (src instanceof BlockNBTComponent) {
            JsonElement position = context.serialize(((BlockNBTComponent)nc).pos());
            object.add("block", position);
            this.serializeSeparator(context, object, nc.separator());
         } else if (src instanceof EntityNBTComponent) {
            object.addProperty("entity", ((EntityNBTComponent)nc).selector());
         } else {
            if (!(src instanceof StorageNBTComponent)) {
               throw notSureHowToSerialize(src);
            }

            object.add("storage", context.serialize(((StorageNBTComponent)nc).storage()));
         }
      }

      return object;
   }

   private void serializeSeparator(final JsonSerializationContext context, final JsonObject json, @Nullable final Component separator) {
      if (separator != null) {
         json.add("separator", context.serialize(separator));
      }

   }

   static JsonParseException notSureHowToDeserialize(final Object element) {
      return new JsonParseException("Don't know how to turn " + element + " into a Component");
   }

   private static IllegalArgumentException notSureHowToSerialize(final Component component) {
      return new IllegalArgumentException("Don't know how to serialize " + component + " as a Component");
   }
}
