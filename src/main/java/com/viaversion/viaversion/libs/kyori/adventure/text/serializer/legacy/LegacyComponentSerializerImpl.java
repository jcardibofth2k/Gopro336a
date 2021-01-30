package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.flattener.ComponentFlattener;
import com.viaversion.viaversion.libs.kyori.adventure.text.flattener.FlattenerListener;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.NamedTextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextFormat;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializerImpl.1;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializerImpl.Cereal.StyleState;
import com.viaversion.viaversion.libs.kyori.adventure.util.Services;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class LegacyComponentSerializerImpl implements LegacyComponentSerializer {
   static final Pattern DEFAULT_URL_PATTERN = Pattern.compile("(?:(https?)://)?([-\\w_.]+\\.\\w{2,})(/\\S*)?");
   static final Pattern URL_SCHEME_PATTERN = Pattern.compile("^[a-z][a-z0-9+\\-.]*:");
   private static final TextDecoration[] DECORATIONS = TextDecoration.values();
   private static final char LEGACY_BUNGEE_HEX_CHAR = 'x';
   private static final List FORMATS;
   private static final String LEGACY_CHARS;
   private static final Optional SERVICE;
   static final Consumer BUILDER;
   private final char character;
   private final char hexCharacter;
   @Nullable
   private final TextReplacementConfig urlReplacementConfig;
   private final boolean hexColours;
   private final boolean useTerriblyStupidHexFormat;
   private final ComponentFlattener flattener;

   LegacyComponentSerializerImpl(final char character, final char hexCharacter, @Nullable final TextReplacementConfig urlReplacementConfig, final boolean hexColours, final boolean useTerriblyStupidHexFormat, final ComponentFlattener flattener) {
      this.character = character;
      this.hexCharacter = hexCharacter;
      this.urlReplacementConfig = urlReplacementConfig;
      this.hexColours = hexColours;
      this.useTerriblyStupidHexFormat = useTerriblyStupidHexFormat;
      this.flattener = flattener;
   }

   @Nullable
   private LegacyComponentSerializerImpl.FormatCodeType determineFormatType(final char legacy, final String input, final int pos) {
      if (pos >= 14) {
         int expectedCharacterPosition = pos - 14;
         int expectedIndicatorPosition = pos - 13;
         if (input.charAt(expectedCharacterPosition) == this.character && input.charAt(expectedIndicatorPosition) == 'x') {
            return LegacyComponentSerializerImpl.FormatCodeType.BUNGEECORD_UNUSUAL_HEX;
         }
      }

      if (legacy == this.hexCharacter && input.length() - pos >= 6) {
         return LegacyComponentSerializerImpl.FormatCodeType.KYORI_HEX;
      } else {
         return LEGACY_CHARS.indexOf(legacy) != -1 ? LegacyComponentSerializerImpl.FormatCodeType.MOJANG_LEGACY : null;
      }
   }

   @Nullable
   static LegacyFormat legacyFormat(final char character) {
      int index = LEGACY_CHARS.indexOf(character);
      if (index != -1) {
         TextFormat format = (TextFormat)FORMATS.get(index);
         if (format instanceof NamedTextColor) {
            return new LegacyFormat((NamedTextColor)format);
         }

         if (format instanceof TextDecoration) {
            return new LegacyFormat((TextDecoration)format);
         }

         if (format instanceof LegacyComponentSerializerImpl.Reset) {
            return LegacyFormat.RESET;
         }
      }

      return null;
   }

   @Nullable
   private LegacyComponentSerializerImpl.DecodedFormat decodeTextFormat(final char legacy, final String input, final int pos) {
      LegacyComponentSerializerImpl.FormatCodeType foundFormat = this.determineFormatType(legacy, input, pos);
      if (foundFormat == null) {
         return null;
      } else {
         if (foundFormat == LegacyComponentSerializerImpl.FormatCodeType.KYORI_HEX) {
            TextColor parsed = tryParseHexColor(input.substring(pos, pos + 6));
            if (parsed != null) {
               return new LegacyComponentSerializerImpl.DecodedFormat(foundFormat, parsed, (1)null);
            }
         } else {
            if (foundFormat == LegacyComponentSerializerImpl.FormatCodeType.MOJANG_LEGACY) {
               return new LegacyComponentSerializerImpl.DecodedFormat(foundFormat, (TextFormat)FORMATS.get(LEGACY_CHARS.indexOf(legacy)), (1)null);
            }

            if (foundFormat == LegacyComponentSerializerImpl.FormatCodeType.BUNGEECORD_UNUSUAL_HEX) {
               StringBuilder foundHex = new StringBuilder(6);

               for(int i = pos - 1; i >= pos - 11; i -= 2) {
                  foundHex.append(input.charAt(i));
               }

               TextColor parsed = tryParseHexColor(foundHex.reverse().toString());
               if (parsed != null) {
                  return new LegacyComponentSerializerImpl.DecodedFormat(foundFormat, parsed, (1)null);
               }
            }
         }

         return null;
      }
   }

   @Nullable
   private static TextColor tryParseHexColor(final String hexDigits) {
      try {
         int color = Integer.parseInt(hexDigits, 16);
         return TextColor.color(color);
      } catch (NumberFormatException var2) {
         return null;
      }
   }

   private static boolean isHexTextColor(final TextFormat format) {
      return format instanceof TextColor && !(format instanceof NamedTextColor);
   }

   private String toLegacyCode(TextFormat format) {
      if (isHexTextColor((TextFormat)format)) {
         TextColor color = (TextColor)format;
         if (this.hexColours) {
            String hex = String.format("%06x", color.value());
            if (!this.useTerriblyStupidHexFormat) {
               return this.hexCharacter + hex;
            }

            StringBuilder legacy = new StringBuilder(String.valueOf('x'));
            char[] var5 = hex.toCharArray();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               char character = var5[var7];
               legacy.append(this.character).append(character);
            }

            return legacy.toString();
         }

         format = NamedTextColor.nearestTo(color);
      }

      int index = FORMATS.indexOf(format);
      return Character.toString(LEGACY_CHARS.charAt(index));
   }

   private TextComponent extractUrl(final TextComponent component) {
      if (this.urlReplacementConfig == null) {
         return component;
      } else {
         Component newComponent = component.replaceText(this.urlReplacementConfig);
         return newComponent instanceof TextComponent ? (TextComponent)newComponent : TextComponent.ofChildren(newComponent);
      }
   }

   @NotNull
   public TextComponent deserialize(@NotNull final String input) {
      int next = input.lastIndexOf(this.character, input.length() - 2);
      if (next == -1) {
         return this.extractUrl(Component.text(input));
      } else {
         List parts = new ArrayList();
         TextComponent.Builder current = null;
         boolean reset = false;
         int pos = input.length();

         do {
            LegacyComponentSerializerImpl.DecodedFormat decoded = this.decodeTextFormat(input.charAt(next + 1), input, next + 2);
            if (decoded != null) {
               int from = next + (decoded.encodedFormat == LegacyComponentSerializerImpl.FormatCodeType.KYORI_HEX ? 8 : 2);
               if (from != pos) {
                  if (current != null) {
                     if (reset) {
                        parts.add((TextComponent)current.build());
                        reset = false;
                        current = Component.text();
                     } else {
                        current = (TextComponent.Builder)Component.text().append(current.build());
                     }
                  } else {
                     current = Component.text();
                  }

                  current.content(input.substring(from, pos));
               } else if (current == null) {
                  current = Component.text();
               }

               if (!reset) {
                  reset = applyFormat(current, decoded.format);
               }

               if (decoded.encodedFormat == LegacyComponentSerializerImpl.FormatCodeType.BUNGEECORD_UNUSUAL_HEX) {
                  next -= 12;
               }

               pos = next;
            }

            next = input.lastIndexOf(this.character, next - 1);
         } while(next != -1);

         if (current != null) {
            parts.add((TextComponent)current.build());
         }

         String remaining = pos > 0 ? input.substring(0, pos) : "";
         if (parts.size() == 1 && remaining.isEmpty()) {
            return this.extractUrl((TextComponent)parts.get(0));
         } else {
            Collections.reverse(parts);
            return this.extractUrl((TextComponent)((TextComponent.Builder)Component.text().content(remaining).append(parts)).build());
         }
      }
   }

   @NotNull
   public String serialize(@NotNull final Component component) {
      LegacyComponentSerializerImpl.Cereal state = new LegacyComponentSerializerImpl.Cereal((1)null);
      this.flattener.flatten(component, state);
      return state.toString();
   }

   private static boolean applyFormat(@NotNull final TextComponent.Builder builder, @NotNull final TextFormat format) {
      if (format instanceof TextColor) {
         builder.colorIfAbsent((TextColor)format);
         return true;
      } else if (format instanceof TextDecoration) {
         builder.decoration((TextDecoration)format, TextDecoration.State.TRUE);
         return false;
      } else if (format instanceof LegacyComponentSerializerImpl.Reset) {
         return true;
      } else {
         throw new IllegalArgumentException(String.format("unknown format '%s'", format.getClass()));
      }
   }

   @NotNull
   public LegacyComponentSerializer.Builder toBuilder() {
      return new LegacyComponentSerializerImpl.BuilderImpl(this);
   }

   // $FF: synthetic method
   static TextDecoration[] access$500() {
      return DECORATIONS;
   }

   static {
      Map formats = new LinkedHashMap(22);
      formats.put(NamedTextColor.BLACK, "0");
      formats.put(NamedTextColor.DARK_BLUE, "1");
      formats.put(NamedTextColor.DARK_GREEN, "2");
      formats.put(NamedTextColor.DARK_AQUA, "3");
      formats.put(NamedTextColor.DARK_RED, "4");
      formats.put(NamedTextColor.DARK_PURPLE, "5");
      formats.put(NamedTextColor.GOLD, "6");
      formats.put(NamedTextColor.GRAY, "7");
      formats.put(NamedTextColor.DARK_GRAY, "8");
      formats.put(NamedTextColor.BLUE, "9");
      formats.put(NamedTextColor.GREEN, "a");
      formats.put(NamedTextColor.AQUA, "b");
      formats.put(NamedTextColor.RED, "c");
      formats.put(NamedTextColor.LIGHT_PURPLE, "d");
      formats.put(NamedTextColor.YELLOW, "e");
      formats.put(NamedTextColor.WHITE, "f");
      formats.put(TextDecoration.OBFUSCATED, "k");
      formats.put(TextDecoration.BOLD, "l");
      formats.put(TextDecoration.STRIKETHROUGH, "m");
      formats.put(TextDecoration.UNDERLINED, "n");
      formats.put(TextDecoration.ITALIC, "o");
      formats.put(LegacyComponentSerializerImpl.Reset.INSTANCE, "r");
      FORMATS = Collections.unmodifiableList(new ArrayList(formats.keySet()));
      LEGACY_CHARS = String.join("", formats.values());
      if (FORMATS.size() != LEGACY_CHARS.length()) {
         throw new IllegalStateException("FORMATS length differs from LEGACY_CHARS length");
      } else {
         SERVICE = Services.service(LegacyComponentSerializer.Provider.class);
         BUILDER = (Consumer)SERVICE.map(LegacyComponentSerializer.Provider::legacy).orElseGet(() -> {
            return (builder) -> {
            };
         });
      }
   }

   static enum FormatCodeType {
      MOJANG_LEGACY,
      KYORI_HEX,
      BUNGEECORD_UNUSUAL_HEX;

      // $FF: synthetic method
      private static LegacyComponentSerializerImpl.FormatCodeType[] $values() {
         return new LegacyComponentSerializerImpl.FormatCodeType[]{MOJANG_LEGACY, KYORI_HEX, BUNGEECORD_UNUSUAL_HEX};
      }
   }

   private static enum Reset implements TextFormat {
      INSTANCE;

      // $FF: synthetic method
      private static LegacyComponentSerializerImpl.Reset[] $values() {
         return new LegacyComponentSerializerImpl.Reset[]{INSTANCE};
      }
   }

   static final class DecodedFormat {
      final LegacyComponentSerializerImpl.FormatCodeType encodedFormat;
      final TextFormat format;

      private DecodedFormat(final LegacyComponentSerializerImpl.FormatCodeType encodedFormat, final TextFormat format) {
         if (format == null) {
            throw new IllegalStateException("No format found");
         } else {
            this.encodedFormat = encodedFormat;
            this.format = format;
         }
      }

      // $FF: synthetic method
      DecodedFormat(LegacyComponentSerializerImpl.FormatCodeType x0, TextFormat x1, 1 x2) {
         this(x0, x1);
      }
   }

   private final class Cereal implements FlattenerListener {
      // $FF: renamed from: sb java.lang.StringBuilder
      private final StringBuilder field_3208;
      private final StyleState style;
      @Nullable
      private TextFormat lastWritten;
      private StyleState[] styles;
      private int head;

      private Cereal() {
         this.field_3208 = new StringBuilder();
         this.style = new StyleState(this);
         this.styles = new StyleState[8];
         this.head = -1;
      }

      public void pushStyle(@NotNull final Style pushed) {
         int idx = ++this.head;
         if (idx >= this.styles.length) {
            this.styles = (StyleState[])Arrays.copyOf(this.styles, this.styles.length * 2);
         }

         StyleState state = this.styles[idx];
         if (state == null) {
            this.styles[idx] = state = new StyleState(this);
         }

         if (idx > 0) {
            state.set(this.styles[idx - 1]);
         } else {
            state.clear();
         }

         state.apply(pushed);
      }

      public void component(@NotNull final String text) {
         if (!text.isEmpty()) {
            if (this.head < 0) {
               throw new IllegalStateException("No style has been pushed!");
            }

            this.styles[this.head].applyFormat();
            this.field_3208.append(text);
         }

      }

      public void popStyle(@NotNull final Style style) {
         if (this.head-- < 0) {
            throw new IllegalStateException("Tried to pop beyond what was pushed!");
         }
      }

      void append(@NotNull final TextFormat format) {
         if (this.lastWritten != format) {
            this.field_3208.append(LegacyComponentSerializerImpl.this.character).append(LegacyComponentSerializerImpl.this.toLegacyCode(format));
         }

         this.lastWritten = format;
      }

      public String toString() {
         return this.field_3208.toString();
      }

      // $FF: synthetic method
      Cereal(1 x1) {
         this();
      }

      // $FF: synthetic method
      static StyleState access$600(LegacyComponentSerializerImpl.Cereal x0) {
         return x0.style;
      }

      // $FF: synthetic method
      static TextFormat access$700(LegacyComponentSerializerImpl.Cereal x0) {
         return x0.lastWritten;
      }
   }

   static final class BuilderImpl implements LegacyComponentSerializer.Builder {
      private char character;
      private char hexCharacter;
      private TextReplacementConfig urlReplacementConfig;
      private boolean hexColours;
      private boolean useTerriblyStupidHexFormat;
      private ComponentFlattener flattener;

      BuilderImpl() {
         this.character = 167;
         this.hexCharacter = '#';
         this.urlReplacementConfig = null;
         this.hexColours = false;
         this.useTerriblyStupidHexFormat = false;
         this.flattener = ComponentFlattener.basic();
         LegacyComponentSerializerImpl.BUILDER.accept(this);
      }

      BuilderImpl(@NotNull final LegacyComponentSerializerImpl serializer) {
         this();
         this.character = serializer.character;
         this.hexCharacter = serializer.hexCharacter;
         this.urlReplacementConfig = serializer.urlReplacementConfig;
         this.hexColours = serializer.hexColours;
         this.useTerriblyStupidHexFormat = serializer.useTerriblyStupidHexFormat;
      }

      @NotNull
      public LegacyComponentSerializer.Builder character(final char legacyCharacter) {
         this.character = legacyCharacter;
         return this;
      }

      @NotNull
      public LegacyComponentSerializer.Builder hexCharacter(final char legacyHexCharacter) {
         this.hexCharacter = legacyHexCharacter;
         return this;
      }

      @NotNull
      public LegacyComponentSerializer.Builder extractUrls() {
         return this.extractUrls(LegacyComponentSerializerImpl.DEFAULT_URL_PATTERN, (Style)null);
      }

      @NotNull
      public LegacyComponentSerializer.Builder extractUrls(@NotNull final Pattern pattern) {
         return this.extractUrls(pattern, (Style)null);
      }

      @NotNull
      public LegacyComponentSerializer.Builder extractUrls(@Nullable final Style style) {
         return this.extractUrls(LegacyComponentSerializerImpl.DEFAULT_URL_PATTERN, style);
      }

      @NotNull
      public LegacyComponentSerializer.Builder extractUrls(@NotNull final Pattern pattern, @Nullable final Style style) {
         Objects.requireNonNull(pattern, "pattern");
         this.urlReplacementConfig = (TextReplacementConfig)TextReplacementConfig.builder().match(pattern).replacement((url) -> {
            String clickUrl = url.content();
            if (!LegacyComponentSerializerImpl.URL_SCHEME_PATTERN.matcher(clickUrl).find()) {
               clickUrl = "http://" + clickUrl;
            }

            return (style == null ? url : (TextComponent.Builder)url.style(style)).clickEvent(ClickEvent.openUrl(clickUrl));
         }).build();
         return this;
      }

      @NotNull
      public LegacyComponentSerializer.Builder hexColors() {
         this.hexColours = true;
         return this;
      }

      @NotNull
      public LegacyComponentSerializer.Builder useUnusualXRepeatedCharacterHexFormat() {
         this.useTerriblyStupidHexFormat = true;
         return this;
      }

      @NotNull
      public LegacyComponentSerializer.Builder flattener(@NotNull final ComponentFlattener flattener) {
         this.flattener = (ComponentFlattener)Objects.requireNonNull(flattener, "flattener");
         return this;
      }

      @NotNull
      public LegacyComponentSerializer build() {
         return new LegacyComponentSerializerImpl(this.character, this.hexCharacter, this.urlReplacementConfig, this.hexColours, this.useTerriblyStupidHexFormat, this.flattener);
      }
   }

   static final class Instances {
      static final LegacyComponentSerializer SECTION;
      static final LegacyComponentSerializer AMPERSAND;

      static {
         SECTION = (LegacyComponentSerializer)LegacyComponentSerializerImpl.SERVICE.map(LegacyComponentSerializer.Provider::legacySection).orElseGet(() -> {
            return new LegacyComponentSerializerImpl('§', '#', (TextReplacementConfig)null, false, false, ComponentFlattener.basic());
         });
         AMPERSAND = (LegacyComponentSerializer)LegacyComponentSerializerImpl.SERVICE.map(LegacyComponentSerializer.Provider::legacyAmpersand).orElseGet(() -> {
            return new LegacyComponentSerializerImpl('&', '#', (TextReplacementConfig)null, false, false, ComponentFlattener.basic());
         });
      }
   }
}
