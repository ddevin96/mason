/*
  Copyright 2021 by Sean Luke and George Mason University
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/

package sim.des;

import sim.engine.*;
import java.util.*;

public abstract class Macro implements Named, Steppable
    {
    ArrayList<Named> steppables = new ArrayList<>();
    ArrayList<Receiver> receivers = new ArrayList<>();
    ArrayList<Provider> providers = new ArrayList<>();

    protected void add(Named step)
        {
        if (!steppables.contains(step))
            steppables.add(step);
        }

    protected void addReceiver(Receiver recv)
        {
        if (!receivers.contains(recv))
            receivers.add(recv);
        add(recv);
        }
                
    protected void addProvider(Provider prov)
        {
        if (!providers.contains(prov))
            providers.add(prov);
        add(prov);
        }
        
    public Receiver[] getReceivers() { return receivers.toArray(new Receiver[receivers.size()]); }
        
    public String[] getReceiverNames() 
        { 
        Receiver[] recv = getReceivers();
        String[] retval = new String[recv.length];
        for(int i = 0; i < recv.length; i++)
            retval[i] = recv[i].getName();
        return retval;
        }
                
    public Provider[] getProviders() { return providers.toArray(new Provider[providers.size()]); }
                
    public String[] getProviderNames() 
        { 
        Provider[] prov = getProviders();
        String[] retval = new String[prov.length];
        for(int i = 0; i < prov.length; i++)
            retval[i] = prov[i].getName();
        return retval;
        }
                
    public void step(SimState state)
        {
        for(Steppable step : steppables)
            {
            step.step(state);
            }
        }
    }
