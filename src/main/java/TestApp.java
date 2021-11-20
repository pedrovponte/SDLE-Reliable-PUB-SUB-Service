import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class TestApp {

    public static void main(String[] args) throws RemoteException, NotBoundException {

        if (args.length < 2)
            throw new IllegalArgumentException("Not enough arguments");

        String topic = "";
        Registry reg = LocateRegistry.getRegistry("localhost");
        String id = args[1];

        switch (args[0].toLowerCase()) {
            case "put":
                // put <topic> <message>
                topic = args[2];
                String msg = args[3];
                PublisherInterface publisher = (PublisherInterface) reg.lookup("Pub" + id);
                publisher.put(topic, msg);
                break;
            case "subscribe":
                // subscribe <id> <topic>
                topic = args[2];
                SubscriberInterface subscriber = (SubscriberInterface) reg.lookup("Sub" + id);
                subscriber.subscribe(topic);
                break;
            case "unsubscribe":
                // unsubscribe <id> <topic>
                topic = args[2];
                SubscriberInterface subscriber1 = (SubscriberInterface) reg.lookup("Sub" + id);
                subscriber1.unsubscribe(topic);
                break;
            case "get":
                topic = args[2];
                SubscriberInterface subscriber2 = (SubscriberInterface) reg.lookup("Sub" + id);
                subscriber2.get(topic);
            default:
                throw new IllegalArgumentException("Illegal argument" + args[1]);

        }
    }
}