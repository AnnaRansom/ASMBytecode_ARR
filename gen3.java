import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class gen3 {
    public static void main(String[] args) throws IOException {
        // Create a ClassWriter for a public class named "program3"
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "program3", null, "java/lang/Object", null);

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
        // Divide two floats
        mv.visitLdcInsn(9.0f);
        mv.visitLdcInsn(3.3f);
        mv.visitInsn(Opcodes.FDIV);
        mv.visitVarInsn(Opcodes.FSTORE, 1); // Store result in local variable 1

        // Divide two doubles
        mv.visitLdcInsn(10.0);
        mv.visitLdcInsn(2.0);
        mv.visitInsn(Opcodes.DDIV);
        mv.visitVarInsn(Opcodes.DSTORE, 2); // Store result in local variable 2

        // Print float result
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(Opcodes.FLOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(F)V", false);

        // Print double result
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(Opcodes.DLOAD, 2);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(D)V", false);


        // Close main method
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0,0);
        mv.visitEnd();

        cw.visitEnd();

        // Write the class to a .class file
        byte[] bytes = cw.toByteArray();
        try (FileOutputStream fos = new FileOutputStream("program3.class")) {
            fos.write(bytes);
        }
}}