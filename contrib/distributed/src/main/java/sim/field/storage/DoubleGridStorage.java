package sim.field.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import mpi.*;
import static mpi.MPI.slice;

import sim.field.partitioning.IntHyperRect;
import sim.util.MPIParam;
import sim.util.*;

public class DoubleGridStorage<T extends Serializable> extends GridStorage<T>{
	
	//TODO CHANGE HERE TO BE EQUAL TO THE ABSTRACT METHODS
	@Override
	public void setLocation(T obj, NumberND p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NumberND getLocation(T obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeObject(T obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeObjects(NumberND p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<T> getObjects(NumberND p) {
		// TODO Auto-generated method stub
		return null;
	}

    public DoubleGridStorage(IntHyperRect shape, double initVal) {
        super(shape);
        baseType = MPI.DOUBLE;
        storage = allocate(shape.getArea());
        Arrays.fill((double[])storage, initVal);
    }

    public GridStorage getNewStorage(IntHyperRect shape) {
        return new DoubleGridStorage(shape, 0);
    }

    public byte[] pack(MPIParam mp) throws MPIException {
        byte[] buf = new byte[MPI.COMM_WORLD.packSize(mp.size, baseType)];
        MPI.COMM_WORLD.pack(slice((double[])storage, mp.idx), 1, mp.type, buf, 0);
        return buf;
    }

    public int unpack(MPIParam mp, Serializable buf) throws MPIException {
        return MPI.COMM_WORLD.unpack((byte[])buf, 0, slice((double[])storage, mp.idx), 1, mp.type);
    }

    public String toString() {
        int[] size = shape.getSize();
        double[] array = (double[])storage;
        StringBuffer buf = new StringBuffer(String.format("DoubleGridStorage-%s\n", shape));

        if (shape.getNd() == 2)
            for (int i = 0; i < size[0]; i++) {
                for (int j = 0; j < size[1]; j++)
                    buf.append(String.format(" %4.2f ", array[i * size[1] + j]));
                buf.append("\n");
            }

        return buf.toString();
    }

    protected Object allocate(int size) {
        return new double[size];
    }

}
