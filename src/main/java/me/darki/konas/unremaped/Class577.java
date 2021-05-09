package me.darki.konas.unremaped;

import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import me.darki.konas.config.Config;
import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.module.render.BreadCrums;
import net.minecraft.util.math.Vec3d;
import java.io.FileWriter;
import me.darki.konas.command.Logger;
import com.google.common.io.Files;
import java.io.File;
import me.darki.konas.command.Command;

public class Class577 extends Command
{
    public static File Field490;
    
    public static void Method565(final File file) {
        ChatUtil.Method1033("%s", Files.getNameWithoutExtension(file.getName()));
    }
    
    @Override
    public void Method174(final String[] array) {
        if (array.length < 2) {
            Logger.Method1119(this.Method191());
            return;
        }
        if (!Class577.Field490.exists()) {
            Class577.Field490.mkdir();
        }
        final String lowerCase = array[1].toLowerCase();
        int n = -1;
        switch (lowerCase.hashCode()) {
            case 115: {
                if (lowerCase.equals("s")) {
                    n = 0;
                    break;
                }
                break;
            }
            case 3522941: {
                if (lowerCase.equals("save")) {
                    n = 1;
                    break;
                }
                break;
            }
            case 3327206: {
                if (lowerCase.equals("load")) {
                    n = 2;
                    break;
                }
                break;
            }
            case 3322014: {
                if (lowerCase.equals("list")) {
                    n = 3;
                    break;
                }
                break;
            }
            case 94746189: {
                if (lowerCase.equals("clear")) {
                    n = 4;
                    break;
                }
                break;
            }
        }
        Label_1192: {
            switch (n) {
                case 0:
                case 1: {
                    if (array.length < 3) {
                        Logger.Method1119(this.Method191());
                        return;
                    }
                    try {
                        final FileWriter fileWriter = new FileWriter(Class577.Field490 + File.separator + array[2] + ".csv");
                        for (final Vec3d vec3d : BreadCrums.arrayList) {
                            fileWriter.append(vec3d.x + "," + vec3d.y + "," + vec3d.z);
                            fileWriter.append("\n");
                        }
                        fileWriter.flush();
                        fileWriter.close();
                    }
                    catch (IOException ex) {
                        Logger.Method1119("Error while saving!");
                    }
                    break;
                }
                case 2: {
                    if (new File(Class577.Field490 + File.separator + array[2] + ".csv").isFile()) {
                        try {
                            final BufferedReader bufferedReader = new BufferedReader(new FileReader(Class577.Field490 + File.separator + array[2] + ".csv"));
                            BreadCrums.arrayList.clear();
                            String line;
                            while ((line = bufferedReader.readLine()) != null) {
                                final String[] split = line.split(",");
                                BreadCrums.arrayList.add(new Vec3d(Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2])));
                            }
                            bufferedReader.close();
                            BreadCrums.onlyRender.setValue(true);
                        }
                        catch (Exception ex2) {
                            Logger.Method1119("Error while loading, please ensure your file is not corrupted!");
                        }
                        break Label_1192;
                    }
                    Logger.Method1119("Invalid filename!");
                    break Label_1192;
                }
                case 3: {
                    ChatUtil.Method1033("(h)Saved Breadcrums:", new Object[0]);
                    if (Class577.Field490.listFiles() != null) {
                        Arrays.stream(Class577.Field490.listFiles()).filter(Class577::Method566).collect((Collector<? super File, ?, List<? super File>>)Collectors.toList()).forEach((Consumer<? super Object>)Class577::Method565);
                        break;
                    }
                    break;
                }
                case 4: {
                    BreadCrums.arrayList.clear();
                    break;
                }
                default: {
                    Logger.Method1119(this.Method191());
                }
            }
        }
    }
    
    public static boolean Method566(final File file) {
        return file.getName().endsWith(".csv");
    }
    
    static {
        Class577.Field490 = new File(Config.KONAS_FOLDER, "breadcrums");
    }
    
    public Class577() {
        super("Breadcrums", "Load and save breadcrums", new SyntaxChunk[] { new SyntaxChunk("<save/load/list/clear>"), new SyntaxChunk("<name>") });
    }
}