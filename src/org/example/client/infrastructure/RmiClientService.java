package org.example.client.infrastructure;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClientService {
    private static Registry registry;

    public static Registry getRegistry() throws RemoteException {
        if (registry == null) {
            registry = LocateRegistry.getRegistry(2004);
        }
        return registry;
    }
}
