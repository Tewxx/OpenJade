import org.objectweb.asm.*;
import org.objectweb.asm.commons.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;
import java.io.*;

/** Remaps a local Minecraft/Forge dependency to the names in the recovered SRG map.
 * Only build-time dependencies are written; the user's game files are never modified. */
public final class RemapCompileDependencies {
    public static void main(String[] args) throws Exception {
        Map<String,String> names = new HashMap<>();
        for (String line : Files.readAllLines(Paths.get(args[0]))) {
            String[] p = line.trim().split("\\s+");
            if (p[0].equals("CL:")) names.put(p[2],p[1]);
            if (p[0].equals("FD:")) {
                int i=p[2].lastIndexOf('/');
                names.put(p[2].substring(0,i)+"."+p[2].substring(i+1),p[1].substring(p[1].lastIndexOf('/')+1));
            }
            if (p[0].equals("MD:")) {
                int i=p[3].lastIndexOf('/');
                names.put(p[3].substring(0,i)+"."+p[3].substring(i+1)+p[4],p[1].substring(p[1].lastIndexOf('/')+1));
            }
        }
        Remapper remapper = new SimpleRemapper(names);
        try (ZipFile input = new ZipFile(args[1]); ZipOutputStream output = new ZipOutputStream(Files.newOutputStream(Paths.get(args[2])))) {
            Enumeration<? extends ZipEntry> entries=input.entries();
            Set<String> written=new HashSet<>();
            while(entries.hasMoreElements()) {
                ZipEntry entry=entries.nextElement();
                if(!entry.getName().endsWith(".class")) continue;
                byte[] data;
                try(InputStream stream=input.getInputStream(entry)) { data=read(stream); }
                ClassReader reader=new ClassReader(data);
                ClassWriter writer=new ClassWriter(0);
                ClassVisitor metadata = new ClassVisitor(Opcodes.ASM9, writer) {
                    String owner;
                    public void visit(int v,int a,String n,String sig,String sup,String[] interfaces) {
                        owner=n;super.visit(v,a,n,sig,sup,interfaces);
                    }
                    public MethodVisitor visitMethod(int a,String n,String d,String sig,String[] exceptions) {
                        // MCP restores checked exceptions stripped from the distributed game bytecode.
                        if(owner.equals("net/minecraft/client/gui/GuiScreen") &&
                           (n.equals("handleInput") || n.equals("mouseClicked") || n.equals("keyTyped") || n.equals("handleMouseInput") || n.equals("handleKeyboardInput")))
                            exceptions=new String[]{"java/io/IOException"};
                        return super.visitMethod(a,n,d,sig,exceptions);
                    }
                };
                reader.accept(new ClassRemapper(metadata,remapper),0);
                String name=remapper.mapType(reader.getClassName())+".class";
                if(!written.add(name)) throw new IOException("Duplicate mapped class: "+name);
                output.putNextEntry(new ZipEntry(name));output.write(writer.toByteArray());output.closeEntry();
            }
            System.out.println("Mapped "+written.size()+" dependency classes: "+args[2]);
        }
    }
    static byte[] read(InputStream in) throws IOException { ByteArrayOutputStream out=new ByteArrayOutputStream();byte[] buf=new byte[65536];int n;while((n=in.read(buf))!=-1)out.write(buf,0,n);return out.toByteArray(); }
}

