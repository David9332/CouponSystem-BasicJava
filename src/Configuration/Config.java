package Beans;

import java.io.*;

public class Config implements Serializable {
    //we will need serialVersionUID = to indicate a specific class serialization
    //link to data : https://stackoverflow.com/questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 6529685098267757690L;
    String sqlConnectionString;
    boolean createIfNotExists;
    boolean useTimeZone;
    String serverTimeZone;
    String userName;
    String userPassword;
    String DBname;

    /**
     * A default constructor used in the readConfig method in this class.
     */
    public Config() {
    }

    /**
     * A constructor of the Config object.
     *
     * @param sqlConnectionString
     * @param createIfNotExists
     * @param useTimeZone
     * @param serverTimeZone
     * @param userName
     * @param userPassword
     * @param DBname
     */
    public Config(String sqlConnectionString, boolean createIfNotExists, boolean useTimeZone, String serverTimeZone, String userName, String userPassword, String DBname) {
        this.sqlConnectionString = sqlConnectionString;
        this.createIfNotExists = createIfNotExists;
        this.useTimeZone = useTimeZone;
        this.serverTimeZone = serverTimeZone;
        this.userName = userName;
        this.userPassword = userPassword;
        this.DBname = DBname;
    }

    //we will save our configuration (config) into a file
    //Serialization

    /**
     * Writing the configuration into a file and saving it.
     *
     * @return - boolean (True or false).
     */
    public boolean saveConfig() {
        try {
            //we declare the file itself, where we are going to write data....
            FileOutputStream fileOut = new FileOutputStream("drone.config");
            //we will write an object , since we don't know which class type we will be using
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            //write the file on current instance
            out.writeObject(this);
            //close the output stream
            out.close();
            //close the file
            fileOut.close();
            //tell all is ok
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Reading the configuration from a file.
     *
     * @return - The Config object "returnResult": the data as a config file.
     */
    //we will read our configuration (config) from a file
    //Deserializing
    public static Config readConfig() {
        Config returnResult = new Config();
        //choose the file that we will read from
        try {
            //point to the file that we will be reading from
            FileInputStream fileIn = new FileInputStream("drone.config");
            //create an object from the file
            ObjectInputStream in = new ObjectInputStream(fileIn);
            //get the data as config file (by the fields that we used)
            returnResult = (Config) in.readObject();
            //close inputStream
            in.close();
            //close the file
            fileIn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return returnResult;
        }

    }

    public String getSqlConnectionString() {
        return sqlConnectionString;
    }

    public void setSqlConnectionString(String sqlConnectionString) {
        this.sqlConnectionString = sqlConnectionString;
    }

    public boolean isCreateIfNotExists() {
        return createIfNotExists;
    }

    public void setCreateIfNotExists(boolean createIfNotExists) {
        this.createIfNotExists = createIfNotExists;
    }

    public boolean isUseTimeZone() {
        return useTimeZone;
    }

    public void setUseTimeZone(boolean useTimeZone) {
        this.useTimeZone = useTimeZone;
    }

    public String getServerTimeZone() {
        return serverTimeZone;
    }

    public void setServerTimeZone(String serverTimeZone) {
        this.serverTimeZone = serverTimeZone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getDBname() {
        return DBname;
    }

    public void setDBname(String DBname) {
        this.DBname = DBname;
    }

    @Override
    public String toString() {
        return "Config{" +
                "sqlConnectionString='" + sqlConnectionString + '\'' +
                ", createIfNotExists=" + createIfNotExists +
                ", useTimeZone=" + useTimeZone +
                ", serverTimeZone='" + serverTimeZone + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", DBname='" + DBname + '\'' +
                '}';
    }
}