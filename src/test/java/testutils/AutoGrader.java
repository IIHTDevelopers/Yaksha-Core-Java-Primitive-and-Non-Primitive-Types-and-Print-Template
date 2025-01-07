package testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;

public class AutoGrader {

	// Test if the code declares both primitive and non-primitive types
	public boolean testDataTypes(String filePath) throws IOException {
		System.out.println("Starting testDataTypes with file: " + filePath);

		File participantFile = new File(filePath); // Path to participant's file
		if (!participantFile.exists()) {
			System.out.println("File does not exist at path: " + filePath);
			return false;
		}

		FileInputStream fileInputStream = new FileInputStream(participantFile);
		JavaParser javaParser = new JavaParser();
		CompilationUnit cu;
		try {
			cu = javaParser.parse(fileInputStream).getResult()
					.orElseThrow(() -> new IOException("Failed to parse the Java file"));
		} catch (IOException e) {
			System.out.println("Error parsing the file: " + e.getMessage());
			throw e;
		}

		System.out.println("Parsed the Java file successfully.");

		boolean hasPrimitive = false;
		boolean hasNonPrimitive = false;

		// Log the parsed fields (to see what JavaParser captures)
		System.out.println("------ Field Declarations ------");
		for (FieldDeclaration field : cu.findAll(FieldDeclaration.class)) {
			Type type = field.getElementType();
			System.out.println("Field Declaration found: " + field.getVariables());
			if (type instanceof PrimitiveType) {
				hasPrimitive = true;
			} else {
				hasNonPrimitive = true;
			}
		}

		// Log the parsed local variables
		System.out.println("------ Local Variable Declarations ------");
		for (VariableDeclarationExpr var : cu.findAll(VariableDeclarationExpr.class)) {
			System.out.println("Local Variable Declaration found: " + var.getVariables());
			Type type = var.getElementType();
			if (type instanceof PrimitiveType) {
				hasPrimitive = true;
			} else {
				hasNonPrimitive = true;
			}
		}

		// Check if both primitive and non-primitive types are found
		System.out.println("Has primitive: " + hasPrimitive);
		System.out.println("Has non-primitive: " + hasNonPrimitive);

		boolean result = hasPrimitive && hasNonPrimitive;
		System.out.println("Test result: " + result);

		return result;
	}

}
