import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SingletonDP {

    public static void main(String[] args) throws CloneNotSupportedException {
        EagerSingleton singleton = EagerSingleton.getSingleton();
        LazyInitializedSingleton laSingleton = LazyInitializedSingleton.getInitializedSingleton();
        StaticBlockSingleton blockSingleton = StaticBlockSingleton.getStaticBlockSingleton();
        getSeriliseObj();
        getClone();
    }

    private static void getSeriliseObj() {
        try {

            SerializedSingleton serializedSingleton = SerializedSingleton.getInstance();
            ObjectOutput out = new ObjectOutputStream(new FileOutputStream("SerializedSingleton.ser"));
            out.writeObject(serializedSingleton);
            out.close();

            // deserilise
            ObjectInput in = new ObjectInputStream(new FileInputStream("SerializedSingleton.ser"));
            SerializedSingleton deserializedSingleton = (SerializedSingleton) in.readObject();

            System.out.println(serializedSingleton.hashCode());
            System.out.println(deserializedSingleton.hashCode());
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private static void getClone() {
        CloneSingleton instance = CloneSingleton.getSingleton();
        CloneSingleton instance1 = (CloneSingleton) instance.clone();
        System.out.println(instance.hashCode());
        System.out.println(instance1.hashCode());
    }
}

enum Singleton {
    INSTANCE;
}

class CloneSingleton implements Cloneable {
    private static final CloneSingleton singleton = new CloneSingleton();

    private CloneSingleton() {
    }

    /**
     * @return the singleton
     */
    public static CloneSingleton getSingleton() {
        return singleton;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return singleton;
    }
}

class SerializedSingleton implements Serializable {
    private static final long serialVersionUID = -7604766932017737115L;

    private SerializedSingleton() {
    }

    private static class SingletonHelper {
        private static final SerializedSingleton instance = new SerializedSingleton();
    }

    public static SerializedSingleton getInstance() {
        return SingletonHelper.instance;
    }

    protected Object readResolve() {
        return getInstance();
    }
}

class ThreadSafeSyncBlock {
    private static ThreadSafeSyncBlock instance;

    private ThreadSafeSyncBlock() {
    }

    /**
     * @return the instance
     */
    public static ThreadSafeSyncBlock getInstance() {
        if (instance == null) {
            synchronized (ThreadSafeSyncBlock.class) {
                if (instance == null) {
                    instance = new ThreadSafeSyncBlock();
                }
            }
        }
        return instance;
    }
}

class ThreadSafeSingletonSyncMethod {
    // This will work fine but reduce the performance of the code
    private static ThreadSafeSingletonSyncMethod instance;

    private ThreadSafeSingletonSyncMethod() {
    }

    public static synchronized ThreadSafeSingletonSyncMethod getInstance() {
        if (instance == null) {
            instance = new ThreadSafeSingletonSyncMethod();
        }
        return instance;
    }

}

/** Static block initialization */
class StaticBlockSingleton {
    private static StaticBlockSingleton staticBlockSingleton;
    static {
        try {
            System.out.println("Static Block");
            staticBlockSingleton = new StaticBlockSingleton();
        } catch (Exception e) {

        }
    }

    private StaticBlockSingleton() {
        System.out.println("Creating Obj");
    }

    /**
     * @return the staticBlockSingleton
     */
    public static StaticBlockSingleton getStaticBlockSingleton() {
        System.out.println(" Geting Obj");
        return staticBlockSingleton;
    }

}

class LazyInitializedSingleton {

    private static LazyInitializedSingleton initializedSingleton;

    private LazyInitializedSingleton() {
    }

    /**
     * @return the initializedSingleton
     */
    public static LazyInitializedSingleton getInitializedSingleton() {
        if (initializedSingleton == null)
            initializedSingleton = new LazyInitializedSingleton();
        return initializedSingleton;
    }
}

class EagerSingleton {
    private static final EagerSingleton singleton = new EagerSingleton();

    private EagerSingleton() {
    }

    /**
     * @return the singleton
     */
    public static EagerSingleton getSingleton() {
        return singleton;
    }

}
