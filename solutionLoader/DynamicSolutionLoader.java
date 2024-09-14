import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class DynamicSolutionLoader {

    private static final String SOLUTIONS_DIRECTORY = "solutions";
    private static final String SOLUTION_CLASS_NAME = "Solution";
    private static final String SOURCE_FILE_EXTENSION = ".java";

    public static Object executeSolution(String problemId, Object... params) throws Exception {
        Class<?> solutionClass = loadSolutionClass(problemId);
        return invokeSolutionMethod(solutionClass, params);
    }

    private static Class<?> loadSolutionClass(String problemId) throws IOException, ClassNotFoundException {
        String sourceFilePath = buildSourceFilePath(problemId);
        ensureFileExists(sourceFilePath);
        compileSourceFile(sourceFilePath);
        return loadCompiledClass();
    }

    private static String buildSourceFilePath(String problemId) {
        return SOLUTIONS_DIRECTORY + File.separator + problemId + SOURCE_FILE_EXTENSION;
    }

    private static void ensureFileExists(String filePath) throws IOException {
        if (!new File(filePath).exists()) {
            throw new IOException("Source file not found: " + filePath);
        }
    }

    private static void compileSourceFile(String sourceFilePath) throws IOException {
        javax.tools.JavaCompiler compiler = javax.tools.ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IOException("No Java compiler available. Ensure you're running on a JDK, not a JRE.");
        }
        int compilationResult = compiler.run(null, null, null, sourceFilePath);

        if (compilationResult != 0) {
            throw new IOException("Compilation failed for file: " + sourceFilePath);
        }
    }

    private static Class<?> loadCompiledClass() throws IOException, ClassNotFoundException {
        URL[] classPath = {new File(SOLUTIONS_DIRECTORY).toURI().toURL()};
        try (URLClassLoader classLoader = new URLClassLoader(classPath)) {
            return classLoader.loadClass(SOLUTION_CLASS_NAME);
        }
    }

    private static Object invokeSolutionMethod(Class<?> solutionClass, Object... params) throws Exception {
        Constructor<?> constructor = solutionClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object instance = constructor.newInstance();
        Method method = findPublicMethod(solutionClass);
        method.setAccessible(true);
        System.out.println("Executing solution " + method.getName());
        System.out.print("[");
        for (var param : params) {
            System.out.print(param);
            System.out.print(", ");
        }
        System.out.println("]");
        return method.invoke(instance, params);
    }

    private static Method findPublicMethod(Class<?> clazz) throws NoSuchMethodException {
        return Arrays.stream(clazz.getDeclaredMethods())
                     .filter(method -> java.lang.reflect.Modifier.isPublic(method.getModifiers()))
                     .findFirst()
                     .orElseThrow(() -> new NoSuchMethodException("No public method found in class " + clazz.getName()));
    }
}