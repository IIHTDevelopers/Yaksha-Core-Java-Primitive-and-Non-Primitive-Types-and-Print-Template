import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.stmt.ExpressionStmt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AutoGrader {

    public static void main(String[] args) throws Exception {
        // Load the participant's Java file
        File participantFile = new File("Project1.java");  // Path to participant's file
        CompilationUnit cu = JavaParser.parse(new FileInputStream(participantFile));

        // Run the grading tests
        testDataTypes(cu);
        testTypeCasting(cu);
        testMethodImplementation(cu);
        testMethodCalls(cu);
    }

    // Test if the code declares both primitive and non-primitive types
    private static void testDataTypes(CompilationUnit cu) {
        System.out.println("Testing Data Types...");

        boolean hasPrimitive = false;
        boolean hasNonPrimitive = false;

        // Look for field declarations
        for (FieldDeclaration field : cu.findAll(FieldDeclaration.class)) {
            Type type = field.getElementType();
            if (type instanceof PrimitiveType) {
                hasPrimitive = true;
            } else {
                hasNonPrimitive = true;
            }
        }

        // Check if both types are declared
        if (hasPrimitive && hasNonPrimitive) {
            System.out.println("✓ Both primitive and non-primitive types are declared.");
        } else {
            System.out.println("✘ Missing either primitive or non-primitive types.");
        }
    }

    // Test for correct usage of type casting (both implicit and explicit)
    private static void testTypeCasting(CompilationUnit cu) {
        System.out.println("Testing Type Casting...");

        boolean hasExplicitCasting = false;
        boolean hasImplicitCasting = false;

        // Look for explicit casting expressions (cast expressions)
        for (CastExpr cast : cu.findAll(CastExpr.class)) {
            hasExplicitCasting = true;
            System.out.println("✓ Found explicit casting: " + cast.getExpression() + " to " + cast.getType());
        }

        // Look for implicit casting (assignments that involve type widening)
        for (ExpressionStmt expr : cu.findAll(ExpressionStmt.class)) {
            if (expr.toString().contains("double") && expr.toString().contains("int")) {
                hasImplicitCasting = true;
                System.out.println("✓ Found implicit casting (int to double).");
            }
        }

        // Check if both explicit and implicit casting are present
        if (hasExplicitCasting && hasImplicitCasting) {
            System.out.println("✓ Both explicit and implicit type casting detected.");
        } else {
            System.out.println("✘ Missing explicit or implicit type casting.");
        }
    }

    // Test if the method 'exampleMethod' is implemented correctly
    private static void testMethodImplementation(CompilationUnit cu) {
        System.out.println("Testing Method Implementation...");

        boolean hasExampleMethod = cu.findAll(com.github.javaparser.ast.body.MethodDeclaration.class).stream()
            .anyMatch(method -> method.getName().asString().equals("exampleMethod"));

        if (hasExampleMethod) {
            System.out.println("✓ 'exampleMethod' is implemented.");
        } else {
            System.out.println("✘ 'exampleMethod' is missing.");
        }
    }

    // Test if the main method calls processDataTypes() and exampleMethod() correctly
    private static void testMethodCalls(CompilationUnit cu) {
        System.out.println("Testing Method Calls...");

        boolean calledProcessDataTypes = cu.findAll(ExpressionStmt.class).stream()
            .anyMatch(expr -> expr.toString().contains("processDataTypes()"));

        boolean calledExampleMethod = cu.findAll(ExpressionStmt.class).stream()
            .anyMatch(expr -> expr.toString().contains("exampleMethod"));

        if (calledProcessDataTypes) {
            System.out.println("✓ 'processDataTypes()' is called.");
        } else {
            System.out.println("✘ 'processDataTypes()' is missing in main method.");
        }

        if (calledExampleMethod) {
            System.out.println("✓ 'exampleMethod()' is called.");
        } else {
            System.out.println("✘ 'exampleMethod()' is missing in main method.");
        }
    }
}
