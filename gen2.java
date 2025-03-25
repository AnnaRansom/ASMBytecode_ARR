import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class gen2 {
    public static void main(String[] args) throws IOException {
        // Create a ClassWriter for a public class named "program2"
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "program2", null, "java/lang/Object", null);

        // Generate the Method Visitor constructor
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0); //load the first variable: this
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false); //invoke the superclass constructor (for object)
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(2,4);
        mv.visitEnd();

        // Generate main method
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC+Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();

        // VISIT CODE
        // Subtract two longs
        mv.visitLdcInsn(200000L);
        mv.visitLdcInsn(300000L);
        mv.visitInsn(Opcodes.LSUB);
        mv.visitVarInsn(Opcodes.LSTORE, 1); // Store result in local variable 1

        // Subtract two doubles
        mv.visitLdcInsn((Double)2.5);
        mv.visitLdcInsn((Double)3.5);
        mv.visitInsn(Opcodes.DSUB);
        mv.visitVarInsn(Opcodes.DSTORE, 3); // Store result in local variable 2

        // Print long result
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(Opcodes.LLOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V", false);

        // Print double result
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(Opcodes.DLOAD, 3);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(D)V", false);

        // Close main method
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0,0);
        mv.visitEnd();

        cw.visitEnd();

        // Write the class to a .class file
        byte[] bytes = cw.toByteArray();
        try (FileOutputStream fos = new FileOutputStream("program2.class")) {
            fos.write(bytes);
        }
}}