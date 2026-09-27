// Jade recovery: recovered class name: SchemaSafeClassRemapper; original class: jade.deps.eLz.xLFwnurAtG
package jade.client.runtime;

import jade.deps.asm.ClassVisitor;
import jade.deps.asm.FieldVisitor;
import jade.deps.asm.MethodVisitor;
import jade.deps.asm.commons.ClassRemapper;
import jade.deps.asm.commons.Remapper;

public final class SchemaSafeClassRemapper extends ClassRemapper {
   private final Remapper declarations;
   private String owner;

   public SchemaSafeClassRemapper(int api, ClassVisitor visitor, Remapper declarations, Remapper references) {
      super(api, visitor, references);
      this.declarations = declarations;
   }

   @Override
   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
      this.owner = name;
      super.visit(version, access, name, signature, superName, interfaces);
   }

   @Override
   public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
      FieldVisitor field = this.cv
         .visitField(
            access,
            this.declarations.mapFieldName(this.owner, name, descriptor),
            this.declarations.mapDesc(descriptor),
            this.declarations.mapSignature(signature, true),
            value == null ? null : this.declarations.mapValue(value)
         );
      return field == null ? null : this.createFieldRemapper(field);
   }

   @Override
   public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
      MethodVisitor method = this.cv
         .visitMethod(
            access,
            this.declarations.mapMethodName(this.owner, name, descriptor),
            this.declarations.mapMethodDesc(descriptor),
            this.declarations.mapSignature(signature, false),
            exceptions == null ? null : this.declarations.mapTypes(exceptions)
         );
      return method == null ? null : this.createMethodRemapper(method);
   }
}
