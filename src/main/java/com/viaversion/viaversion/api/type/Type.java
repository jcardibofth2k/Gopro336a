package com.viaversion.viaversion.api.type;

import com.viaversion.viaversion.api.type.types.ArrayType;
import com.viaversion.viaversion.api.type.types.BooleanType;
import com.viaversion.viaversion.api.type.types.ByteArrayType;
import com.viaversion.viaversion.api.type.types.ByteType;
import com.viaversion.viaversion.api.type.types.ComponentType;
import com.viaversion.viaversion.api.type.types.DoubleType;
import com.viaversion.viaversion.api.type.types.FloatType;
import com.viaversion.viaversion.api.type.types.IntType;
import com.viaversion.viaversion.api.type.types.LongArrayType;
import com.viaversion.viaversion.api.type.types.LongType;
import com.viaversion.viaversion.api.type.types.RemainingBytesType;
import com.viaversion.viaversion.api.type.types.ShortByteArrayType;
import com.viaversion.viaversion.api.type.types.ShortType;
import com.viaversion.viaversion.api.type.types.StringType;
import com.viaversion.viaversion.api.type.types.UUIDIntArrayType;
import com.viaversion.viaversion.api.type.types.UUIDType;
import com.viaversion.viaversion.api.type.types.UnsignedByteType;
import com.viaversion.viaversion.api.type.types.UnsignedShortType;
import com.viaversion.viaversion.api.type.types.VarIntArrayType;
import com.viaversion.viaversion.api.type.types.VarIntType;
import com.viaversion.viaversion.api.type.types.VarLongType;
import com.viaversion.viaversion.api.type.types.VoidType;
import com.viaversion.viaversion.api.type.types.minecraft.BlockChangeRecordType;
import com.viaversion.viaversion.api.type.types.minecraft.EulerAngleType;
import com.viaversion.viaversion.api.type.types.minecraft.FlatItemArrayType;
import com.viaversion.viaversion.api.type.types.minecraft.FlatItemType;
import com.viaversion.viaversion.api.type.types.minecraft.FlatVarIntItemArrayType;
import com.viaversion.viaversion.api.type.types.minecraft.FlatVarIntItemType;
import com.viaversion.viaversion.api.type.types.minecraft.ItemArrayType;
import com.viaversion.viaversion.api.type.types.minecraft.ItemType;
import com.viaversion.viaversion.api.type.types.minecraft.NBTType;
import com.viaversion.viaversion.api.type.types.minecraft.OptPosition1_14Type;
import com.viaversion.viaversion.api.type.types.minecraft.OptPositionType;
import com.viaversion.viaversion.api.type.types.minecraft.OptUUIDType;
import com.viaversion.viaversion.api.type.types.minecraft.OptionalComponentType;
import com.viaversion.viaversion.api.type.types.minecraft.OptionalVarIntType;
import com.viaversion.viaversion.api.type.types.minecraft.Position1_14Type;
import com.viaversion.viaversion.api.type.types.minecraft.PositionType;
import com.viaversion.viaversion.api.type.types.minecraft.VarLongBlockChangeRecordType;
import com.viaversion.viaversion.api.type.types.minecraft.VectorType;
import com.viaversion.viaversion.api.type.types.minecraft.VillagerDataType;

public abstract class Type implements ByteBufReader, ByteBufWriter {
   public static final ByteType BYTE = new ByteType();
   public static final UnsignedByteType UNSIGNED_BYTE = new UnsignedByteType();
   public static final Type BYTE_ARRAY_PRIMITIVE = new ByteArrayType();
   public static final Type SHORT_BYTE_ARRAY = new ShortByteArrayType();
   public static final Type REMAINING_BYTES = new RemainingBytesType();
   public static final ShortType SHORT = new ShortType();
   public static final UnsignedShortType UNSIGNED_SHORT = new UnsignedShortType();
   public static final IntType INT = new IntType();
   public static final FloatType FLOAT = new FloatType();
   public static final DoubleType DOUBLE = new DoubleType();
   public static final LongType LONG = new LongType();
   public static final Type LONG_ARRAY_PRIMITIVE = new LongArrayType();
   public static final BooleanType BOOLEAN = new BooleanType();
   public static final Type COMPONENT = new ComponentType();
   public static final Type OPTIONAL_COMPONENT = new OptionalComponentType();
   public static final Type STRING = new StringType();
   public static final Type STRING_ARRAY;
   public static final Type UUID;
   public static final Type OPTIONAL_UUID;
   public static final Type UUID_INT_ARRAY;
   public static final Type UUID_ARRAY;
   public static final VarIntType VAR_INT;
   public static final OptionalVarIntType OPTIONAL_VAR_INT;
   public static final Type VAR_INT_ARRAY_PRIMITIVE;
   public static final VarLongType VAR_LONG;
   /** @deprecated */
   @Deprecated
   public static final Type BYTE_ARRAY;
   /** @deprecated */
   @Deprecated
   public static final Type UNSIGNED_BYTE_ARRAY;
   /** @deprecated */
   @Deprecated
   public static final Type BOOLEAN_ARRAY;
   /** @deprecated */
   @Deprecated
   public static final Type INT_ARRAY;
   /** @deprecated */
   @Deprecated
   public static final Type SHORT_ARRAY;
   /** @deprecated */
   @Deprecated
   public static final Type UNSIGNED_SHORT_ARRAY;
   /** @deprecated */
   @Deprecated
   public static final Type DOUBLE_ARRAY;
   /** @deprecated */
   @Deprecated
   public static final Type LONG_ARRAY;
   /** @deprecated */
   @Deprecated
   public static final Type FLOAT_ARRAY;
   /** @deprecated */
   @Deprecated
   public static final Type VAR_INT_ARRAY;
   /** @deprecated */
   @Deprecated
   public static final Type VAR_LONG_ARRAY;
   public static final VoidType NOTHING;
   public static final Type POSITION;
   public static final Type POSITION1_14;
   public static final Type ROTATION;
   public static final Type VECTOR;
   public static final Type NBT;
   public static final Type NBT_ARRAY;
   public static final Type OPTIONAL_POSITION;
   public static final Type OPTIONAL_POSITION_1_14;
   public static final Type BLOCK_CHANGE_RECORD;
   public static final Type BLOCK_CHANGE_RECORD_ARRAY;
   public static final Type VAR_LONG_BLOCK_CHANGE_RECORD;
   public static final Type VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY;
   public static final Type VILLAGER_DATA;
   public static final Type ITEM;
   public static final Type ITEM_ARRAY;
   public static final Type FLAT_ITEM;
   public static final Type FLAT_VAR_INT_ITEM;
   public static final Type FLAT_ITEM_ARRAY;
   public static final Type FLAT_VAR_INT_ITEM_ARRAY;
   public static final Type FLAT_ITEM_ARRAY_VAR_INT;
   public static final Type FLAT_VAR_INT_ITEM_ARRAY_VAR_INT;
   private final Class outputClass;
   private final String typeName;

   protected Type(Class outputClass) {
      this(outputClass.getSimpleName(), outputClass);
   }

   protected Type(String typeName, Class outputClass) {
      this.outputClass = outputClass;
      this.typeName = typeName;
   }

   public Class getOutputClass() {
      return this.outputClass;
   }

   public String getTypeName() {
      return this.typeName;
   }

   public Class getBaseClass() {
      return this.getClass();
   }

   public String toString() {
      return "Type|" + this.typeName;
   }

   static {
      STRING_ARRAY = new ArrayType(STRING);
      UUID = new UUIDType();
      OPTIONAL_UUID = new OptUUIDType();
      UUID_INT_ARRAY = new UUIDIntArrayType();
      UUID_ARRAY = new ArrayType(UUID);
      VAR_INT = new VarIntType();
      OPTIONAL_VAR_INT = new OptionalVarIntType();
      VAR_INT_ARRAY_PRIMITIVE = new VarIntArrayType();
      VAR_LONG = new VarLongType();
      BYTE_ARRAY = new ArrayType(BYTE);
      UNSIGNED_BYTE_ARRAY = new ArrayType(UNSIGNED_BYTE);
      BOOLEAN_ARRAY = new ArrayType(BOOLEAN);
      INT_ARRAY = new ArrayType(INT);
      SHORT_ARRAY = new ArrayType(SHORT);
      UNSIGNED_SHORT_ARRAY = new ArrayType(UNSIGNED_SHORT);
      DOUBLE_ARRAY = new ArrayType(DOUBLE);
      LONG_ARRAY = new ArrayType(LONG);
      FLOAT_ARRAY = new ArrayType(FLOAT);
      VAR_INT_ARRAY = new ArrayType(VAR_INT);
      VAR_LONG_ARRAY = new ArrayType(VAR_LONG);
      NOTHING = new VoidType();
      POSITION = new PositionType();
      POSITION1_14 = new Position1_14Type();
      ROTATION = new EulerAngleType();
      VECTOR = new VectorType();
      NBT = new NBTType();
      NBT_ARRAY = new ArrayType(NBT);
      OPTIONAL_POSITION = new OptPositionType();
      OPTIONAL_POSITION_1_14 = new OptPosition1_14Type();
      BLOCK_CHANGE_RECORD = new BlockChangeRecordType();
      BLOCK_CHANGE_RECORD_ARRAY = new ArrayType(BLOCK_CHANGE_RECORD);
      VAR_LONG_BLOCK_CHANGE_RECORD = new VarLongBlockChangeRecordType();
      VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY = new ArrayType(VAR_LONG_BLOCK_CHANGE_RECORD);
      VILLAGER_DATA = new VillagerDataType();
      ITEM = new ItemType();
      ITEM_ARRAY = new ItemArrayType();
      FLAT_ITEM = new FlatItemType();
      FLAT_VAR_INT_ITEM = new FlatVarIntItemType();
      FLAT_ITEM_ARRAY = new FlatItemArrayType();
      FLAT_VAR_INT_ITEM_ARRAY = new FlatVarIntItemArrayType();
      FLAT_ITEM_ARRAY_VAR_INT = new ArrayType(FLAT_ITEM);
      FLAT_VAR_INT_ITEM_ARRAY_VAR_INT = new ArrayType(FLAT_VAR_INT_ITEM);
   }
}
