package com.viaversion.viaversion.libs.gson.internal;

import com.viaversion.viaversion.libs.gson.InstanceCreator;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.internal.reflect.ReflectionAccessor;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public final class ConstructorConstructor {
   private final Map instanceCreators;
   private final ReflectionAccessor accessor = ReflectionAccessor.getInstance();

   public ConstructorConstructor(Map instanceCreators) {
      this.instanceCreators = instanceCreators;
   }

   public ObjectConstructor get(TypeToken typeToken) {
      final Type type = typeToken.getType();
      Class rawType = typeToken.getRawType();
      final InstanceCreator typeCreator = (InstanceCreator)this.instanceCreators.get(type);
      if (typeCreator != null) {
         return new ObjectConstructor() {
            public Object construct() {
               return typeCreator.createInstance(type);
            }
         };
      } else {
         final InstanceCreator rawTypeCreator = (InstanceCreator)this.instanceCreators.get(rawType);
         if (rawTypeCreator != null) {
            return new ObjectConstructor() {
               public Object construct() {
                  return rawTypeCreator.createInstance(type);
               }
            };
         } else {
            ObjectConstructor defaultConstructor = this.newDefaultConstructor(rawType);
            if (defaultConstructor != null) {
               return defaultConstructor;
            } else {
               ObjectConstructor defaultImplementation = this.newDefaultImplementationConstructor(type, rawType);
               return defaultImplementation != null ? defaultImplementation : this.newUnsafeAllocator(type, rawType);
            }
         }
      }
   }

   private ObjectConstructor newDefaultConstructor(Class rawType) {
      try {
         final Constructor constructor = rawType.getDeclaredConstructor();
         if (!constructor.isAccessible()) {
            this.accessor.makeAccessible(constructor);
         }

         return new ObjectConstructor() {
            public Object construct() {
               try {
                  Object[] args = null;
                  return constructor.newInstance((Object[])args);
               } catch (InstantiationException var2) {
                  throw new RuntimeException("Failed to invoke " + constructor + " with no args", var2);
               } catch (InvocationTargetException var3) {
                  throw new RuntimeException("Failed to invoke " + constructor + " with no args", var3.getTargetException());
               } catch (IllegalAccessException var4) {
                  throw new AssertionError(var4);
               }
            }
         };
      } catch (NoSuchMethodException var3) {
         return null;
      }
   }

   private ObjectConstructor newDefaultImplementationConstructor(final Type type, Class rawType) {
      if (Collection.class.isAssignableFrom(rawType)) {
         if (SortedSet.class.isAssignableFrom(rawType)) {
            return new ObjectConstructor() {
               public Object construct() {
                  return new TreeSet();
               }
            };
         } else if (EnumSet.class.isAssignableFrom(rawType)) {
            return new ObjectConstructor() {
               public Object construct() {
                  if (type instanceof ParameterizedType) {
                     Type elementType = ((ParameterizedType)type).getActualTypeArguments()[0];
                     if (elementType instanceof Class) {
                        return EnumSet.noneOf((Class)elementType);
                     } else {
                        throw new JsonIOException("Invalid EnumSet type: " + type.toString());
                     }
                  } else {
                     throw new JsonIOException("Invalid EnumSet type: " + type.toString());
                  }
               }
            };
         } else if (Set.class.isAssignableFrom(rawType)) {
            return new ObjectConstructor() {
               public Object construct() {
                  return new LinkedHashSet();
               }
            };
         } else {
            return Queue.class.isAssignableFrom(rawType) ? new ObjectConstructor() {
               public Object construct() {
                  return new ArrayDeque();
               }
            } : new ObjectConstructor() {
               public Object construct() {
                  return new ArrayList();
               }
            };
         }
      } else if (Map.class.isAssignableFrom(rawType)) {
         if (ConcurrentNavigableMap.class.isAssignableFrom(rawType)) {
            return new ObjectConstructor() {
               public Object construct() {
                  return new ConcurrentSkipListMap();
               }
            };
         } else if (ConcurrentMap.class.isAssignableFrom(rawType)) {
            return new ObjectConstructor() {
               public Object construct() {
                  return new ConcurrentHashMap();
               }
            };
         } else if (SortedMap.class.isAssignableFrom(rawType)) {
            return new ObjectConstructor() {
               public Object construct() {
                  return new TreeMap();
               }
            };
         } else {
            return type instanceof ParameterizedType && !String.class.isAssignableFrom(TypeToken.get(((ParameterizedType)type).getActualTypeArguments()[0]).getRawType()) ? new ObjectConstructor() {
               public Object construct() {
                  return new LinkedHashMap();
               }
            } : new ObjectConstructor() {
               public Object construct() {
                  return new LinkedTreeMap();
               }
            };
         }
      } else {
         return null;
      }
   }

   private ObjectConstructor newUnsafeAllocator(final Type type, final Class rawType) {
      return new ObjectConstructor() {
         private final UnsafeAllocator unsafeAllocator = UnsafeAllocator.create();

         public Object construct() {
            try {
               Object newInstance = this.unsafeAllocator.newInstance(rawType);
               return newInstance;
            } catch (Exception var2) {
               throw new RuntimeException("Unable to invoke no-args constructor for " + type + ". Registering an InstanceCreator with Gson for this type may fix this problem.", var2);
            }
         }
      };
   }

   public String toString() {
      return this.instanceCreators.toString();
   }
}
