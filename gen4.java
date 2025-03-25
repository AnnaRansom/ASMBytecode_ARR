import org.objectweb.asm.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class gen4 {
    public static void main(String[] args) throws IOException {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "program4", null, "java/lang/Object", null);

        // Constructor
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0); 
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        // Main method
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();

        // int a = 10;
        mv.visitIntInsn(Opcodes.BIPUSH, 10);
        mv.visitVarInsn(Opcodes.ISTORE, 1);

        // int b = 20;
        mv.visitIntInsn(Opcodes.BIPUSH, 20);
        mv.visitVarInsn(Opcodes.ISTORE, 2);

        // if (a > b)
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitVarInsn(Opcodes.ILOAD, 2);
        Label elseLabel = new Label();
        mv.visitJumpInsn(Opcodes.IF_ICMPLE, elseLabel);  // if a <= b, jump to else

        // Print variable "a"
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);

        // GOTO end
        Label endLabel = new Label();
        mv.visitJumpInsn(Opcodes.GOTO, endLabel);

        // Else block
        mv.visitLabel(elseLabel);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(Opcodes.ILOAD, 2);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);

        // End label
        mv.visitLabel(endLabel);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitInsn(Opcodes.RETURN);

        // Finalize method
        mv.visitMaxs(2, 3);
        mv.visitEnd();

        // Complete the class
        cw.visitEnd();

        // Write the class to a .class file
        byte[] bytes = cw.toByteArray();
        try (FileOutputStream fos = new FileOutputStream("program4.class")) {
            fos.write(bytes);
        }
    }
}
