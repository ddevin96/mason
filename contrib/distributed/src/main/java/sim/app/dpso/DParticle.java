package sim.app.dpso;

import sim.app.pso.Evaluatable;
import sim.engine.DObject;
import sim.engine.DSteppable;
import sim.engine.SimState;
import sim.util.Double2D;
import sim.util.MutableDouble2D;

public class DParticle extends DObject{
	
    private static final long serialVersionUID = 1;

    double bestVal = 0;     
    MutableDouble2D bestPosition = new MutableDouble2D();

    MutableDouble2D position = new MutableDouble2D();
    MutableDouble2D velocity = new MutableDouble2D();       
                
    DPSO dpso;
    Evaluatable fitnessFunction;
    int index;  // this kludge is necessary because the particles are individually scheduled

    public DParticle() 
    {
    super();
    }
    
    public DParticle(double x, double y, double vx, double vy, DPSO dpso, Evaluatable f, int index)
    {
    super();

    this.position.setTo(x, y);
    this.velocity.setTo(vx, vy);
            
    this.dpso = dpso;
    this.fitnessFunction = f;
    
    //dpso.space.moveAgent(new Double2D(position), this); //Do this in DPSO
    
    this.index = index;
    }   
    
    public void updateBest(double currVal, double currX, double currY)
    {
    if (currVal > bestVal)
        {
        bestVal = currVal;
        bestPosition.setTo(currX, currY);
                    
        dpso.updateBest(currVal, currX, currY);
        }
    }
    
    public double getFitness()
    {
    return fitnessFunction.calcFitness(position.x,position.y);
    }
    
    public void stepUpdateFitness()
    {
    updateBest(getFitness(), position.x, position.y);
    }

    public void stepUpdateVelocity()
    {
    double x = position.x;
    double y = position.y;
            
    MutableDouble2D nBestPos = new MutableDouble2D(); 
    dpso.getNeighborhoodBest(index, nBestPos);       // updates the location of nBestPos
            
    // calc new velocity
    // calc x component
    double inertia = velocity.x;
    double pDelta = bestPosition.x - x;
    double nDelta = nBestPos.x - x;
    //double gDelta = dpso.bestPosition.x - x;
    double gDelta = dpso.best_x - x;
    double pWeight = Math.random() + 0.4;
    double nWeight = Math.random() + 0.4;
    double gWeight = Math.random() + 0.4;
    double vx = (0.9*inertia + pWeight*pDelta + nWeight*nDelta + gWeight*gDelta) / (1+pWeight+nWeight+gWeight);
             
    // calc y component
    inertia = velocity.y;
    pDelta = bestPosition.y - y;
    nDelta = nBestPos.y - y;
    //gDelta = dpso.bestPosition.y - y;
    gDelta = dpso.best_y - y;

    pWeight = Math.random() + 0.4;
    nWeight = Math.random() + 0.4;
    gWeight = Math.random() + 0.4;
    double vy = (0.9*inertia + pWeight*pDelta + nWeight*nDelta + gWeight*gDelta) / (1+pWeight+nWeight+gWeight);

    vx *= dpso.velocityScalar;
    vy *= dpso.velocityScalar;
            
    // update velocity
    velocity.setTo(vx, vy);         
    }
    
public void stepUpdatePosition()
    {
    //System.out.println(
    //              "Best: " + n.bestVal + " (" + n.bestPosition.x + ", " + n.bestPosition.y + ")");
    position.addIn(velocity);
    //dpso.space.setObjectLocation(this, new Double2D(position));
    dpso.space.moveAgent(new Double2D(position), this);
    }



    
}
