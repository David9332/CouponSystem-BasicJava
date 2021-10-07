package Exceptions;

public class UserErrorException extends Exception {
    private String className = "";
    private String methodName = "";
    private String message = "";

    /**
     * A constructor for user errors exceptions.
     *
     * @param className  - The class in which the exception occurred.
     * @param methodName - The method in which exception occurred.
     * @param message    - The possible explanation for the exception.
     */
    public UserErrorException(String className, String methodName, String message) {
        System.out.println("User error at: \nCLASS: " + className + ", METHOD: " + methodName + "\n" + message);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodeName() {
        return methodName;
    }

    public void setMethodeName(String methodeName) {
        this.methodName = methodeName;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
