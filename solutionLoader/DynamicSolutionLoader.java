import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DynamicSolutionLoader {

    private static final String SOLUTIONS_DIRECTORY = "solutions";
    private static final String COMPILED_DIRECTORY = "compiled_solutions";
    private static final String SOLUTION_CLASS_NAME = "Solution";
    private static final String SOURCE_FILE_EXTENSION = ".java";
    private static final Logger LOGGER = Logger.getLogger(DynamicSolutionLoader.class.getName());

    public static Object executeSolution(String problemId, Object... params) throws Exception {
        return executeSolutionWithMethod(problemId, Optional.empty(), params);
    }

    public static Object executeSolutionWithMethod(String problemId, String methodName, Object... params) throws Exception {
        return executeSolutionWithMethod(problemId, Optional.of(methodName), params);
    }

    private static Object executeSolutionWithMethod(String problemId, Optional<String> methodName, Object... params) throws Exception {
        Class<?> solutionClass = loadSolutionClass(problemId);
        return invokeSolutionMethod(solutionClass, methodName, params);
    }

    private static Class<?> loadSolutionClass(String problemId) throws IOException, ClassNotFoundException {
        String sourceFilePath = buildSourceFilePath(problemId);
        ensureFileExists(sourceFilePath);
        if (shouldCompile(sourceFilePath)) {
            compileSourceFile(sourceFilePath);
        }
        return loadCompiledClass();
    }

    private static String buildSourceFilePath(String problemId) {
        return SOLUTIONS_DIRECTORY + File.separator + problemId + SOURCE_FILE_EXTENSION;
    }

    private static void ensureFileExists(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("Source file not found or is not a valid file: " + filePath);
        }
    }

    private static boolean shouldCompile(String sourceFilePath) {
        File compiledFile = new File(COMPILED_DIRECTORY + File.separator + SOLUTION_CLASS_NAME + ".class");
        File sourceFile = new File(sourceFilePath);
        return !compiledFile.exists() || sourceFile.lastModified() > compiledFile.lastModified();
    }

    private static void compileSourceFile(String sourceFilePath) throws IOException {
        javax.tools.JavaCompiler compiler = javax.tools.ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IOException("No Java compiler available. Ensure you're running on a JDK, not a JRE.");
        }

        File compiledDir = new File(COMPILED_DIRECTORY);
        if (!compiledDir.exists() && !compiledDir.mkdirs()) {
            throw new IOException("Failed to create compiled directory: " + COMPILED_DIRECTORY);
        }

        OutputStream compilationOutputStream = new ByteArrayOutputStream();
        int compilationResult = compiler.run(null, compilationOutputStream, compilationOutputStream, "-d", COMPILED_DIRECTORY, sourceFilePath);

        LOGGER.log(Level.INFO, compilationOutputStream.toString());

        if (compilationResult != 0) {
            throw new IOException("Compilation failed for file: \n" + sourceFilePath);
        }
    }

    private static Class<?> loadCompiledClass() throws IOException, ClassNotFoundException {
        URL[] classPath = {new File(COMPILED_DIRECTORY).toURI().toURL()};
        try (URLClassLoader classLoader = new URLClassLoader(classPath)) {
            return classLoader.loadClass(SOLUTION_CLASS_NAME);
        }
    }

    private static Object invokeSolutionMethod(Class<?> solutionClass, Optional<String> methodName, Object... params) throws Exception {
        Constructor<?> constructor = solutionClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object instance = constructor.newInstance();

        Method method = methodName
                .map(name -> findMethodByName(solutionClass, name, params)
                        .orElseThrow(() -> new RuntimeException("Method " + name + " not found in class " + solutionClass.getName())))
                .orElse(findFirstPublicMethod(solutionClass));
        method.setAccessible(true);
        LOGGER.log(Level.INFO, "Executing method {0}", method.getName());
        return method.invoke(instance, params);
    }

    private static Method findFirstPublicMethod(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> java.lang.reflect.Modifier.isPublic(method.getModifiers()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No public method found in class " + clazz.getName()));
    }

    private static Optional<Method> findMethodByName(Class<?> clazz, String methodName, Object[] params) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getName().equals(methodName) && method.getParameterCount() == params.length)
                .findFirst();
    }
}
