/* *****************************************************
 * (c) 2012 Particle In Cell Consulting LLC
 * 
 * This document is subject to the license specified in 
 * Starfish.java and the LICENSE file
 * *****************************************************/


package starfish.core.common;

import java.util.Arrays;
import starfish.core.domain.Mesh;

/**classes for performing vector math*/
public class Vector 
{
    /** computes v1 = v1-v
     * @param v12
     * @param v2*/
    public static void subtractInclusive(double v1[], double v2[])
    {
	assert(v1.length==v2.length);
    	
	for (int i=0;i<v1.length;i++)
	    v1[i]-= v2[i];
    }

    /**computes v1 = v1+v
     * @param v12
     * @param v2*/
    public static void addInclusive(double v1[], double v2[])
    {
	assert(v1.length==v2.length);
		
	for (int i=0;i<v1.length;i++)
	    v1[i]+=v2[i];			
    }

    /** returns r = v1-v
     * @param v12
     * @param v2
     * @return */
    public static double[] subtract(double v1[], double v2[])
    {
	assert(v1.length==v2.length);
    	
	double r[] = new double[v1.length];
	for (int i=0;i<v1.length;i++)
	    r[i] = v1[i]-v2[i];
	return r;			
    }

    /** returns r = v1+v
     * @param v12
     * @param v2
     * @return */
    public static double[] add(double v1[], double v2[])
    {
	assert(v1.length==v2.length);
	
	double r[] = new double[v1.length];
	for (int i=0;i<v1.length;i++)
	    r[i] = v1[i]+v2[i];
	return r;			
    }

    /** Multiplies vector by t
     * @param v
     @param t scalar to multiply by
     * @return */
    public static double[] mult(double v[], double t)
    {
	double r[] = new double[v.length];
	for (int i=0;i<v.length;i++)
	    r[i] = v[i]*t;			
	return r;
    }

    /** Multiplies vector by t and stores in result vector
     * @param v
     @param t scalar to multiply by
     * @param result*/
    public static void mult(double v[], double t, double result[])
    {
	for (int i=0;i<v.length;i++)
	    result[i] *= t;			
    }
    
    /** returns {@code\(r = <v1,v2>\)}, inner produc
     * @param v1
     * @param v2t
     * @return */
    public static double[] mult(double v1[], double v2[])
    {
	assert(v1.length==v2.length);
	double r[] = new double[v1.length];
	for (int i=0;i<v1.length;i++)
	    r[i] = v1[i]*v2[i];
	return r;			
    }

    /** returns copy of the vecto
     * @param v
     * @return r*/
    public static double[] copy(double v[])
    {
	double r[] = new double[v.length];
	System.arraycopy(v, 0, r, 0, r.length);
	return r;
    }
	
    /** generates empty vector
     @param n length of the vector
     * @return */
    public static double[] zeros(int n)
    {
	double r[] = new double[n];
	Arrays.fill(r, 0);
	return r;
    }

    
    /** computes l2 norm, sqrt(sum(v^2)/size
     * @param v)
     * @return */
    public static double norm (double v[])
    {
	double v2=0;
	for (int i=0;i<v.length;i++)
	    v2+=v[i]*v[i];
	return Math.sqrt(v2)/v.length;
    }

    /** computes vector dot produc
     * @param v1t
     * @param v2
     * @return */
    public static double dot(double v1[], double v2[]) 
    {
	double sum = 0;
	assert(v1.length==v2.length);
	for (int i=0;i<v1.length;i++)
	    sum += v1[i]*v2[i];
		
	return sum;
    }
    

    /** copies 2D data into 1D arra
     * @param data2D
     * @return y*/
    public static double[] deflate(double data2D[][])
    {
	int ni = data2D.length;
	int nj = data2D[0].length;
    	
	double[] data1D = new double[ni * nj];
		
	/*this needs to match IJtoN, which uses j*ni + i*/
	int u=0;
	for (int j = 0; j < nj; j++)
	    for (int i = 0; i < ni; i++)
	    {
		data1D[u++] = data2D[i][j];
	    }

	return data1D;		
    }

    /** copies 2D data into 1D array
     * @param data2D
     * @param data1D*/
    public static void deflate(double data2D[][], double data1D[])
    {
	int ni = data2D.length;
	int nj = data2D[0].length;
    	
		
	/*this needs to match IJtoN, which uses j*ni + i*/
	int u=0;
	for (int j = 0; j < nj; j++)
	    for (int i = 0; i < ni; i++)
	    {
		data1D[u++] = data2D[i][j];
	    }
    }
    /** copies 1D data to 2
     * @param data1D
     * @param niD
     * @param nj
     * @param data2D*/
    public static void inflate(double data1D[], int ni,int nj, double data2D[][])
    {
	/*unpack data*/
	int u = 0;
	for (int j = 0; j < nj; j++)
	    for (int i = 0; i < ni; i++)
	    	data2D[i][j] = data1D[u++];	
    }	
	
    /** filter to selectively combine two vector
     * @param use_firsts
     * @param first
     * @param second
     * @return */
    public static double[] merge(boolean[] use_first, double first[], double second[]) 
    {
	assert(first.length==second.length);
		
	double r[] = new double[first.length];
	for (int i=0;i<first.length;i++)
	    r[i] = use_first[i]?first[i]:second[i];
	
	return r;
    }

    /** filter to selectively combine two vector
     * @param use_firsts
     * @param first
     * @param second
     * @param r*/
    public static void merge(boolean[] use_first, double first[], double second[], double r[]) 
    {
	assert(first.length==second.length);
		
	for (int i=0;i<first.length;i++)
	    r[i] = use_first[i]?first[i]:second[i];	
    }
    
    /** Similar to merge, but merges in bc_value from mesh
     * 
     * @param fixed_node use data from mesh IJtoN node bc_value otherwise copy from second
     * @param second vector of inputs
     * @param mesh mesh data providing bc_value
     * @return merged vector
     */
    public static double[] mergeBC(boolean[] fixed_node, Mesh mesh, double second[])
    {
	assert(second.length==mesh.n_nodes);
		
	double r[] = new double[second.length];
	for (int i=0;i<mesh.ni;i++)
	    for (int j=0;j<mesh.nj;j++)
	    {
		int n = mesh.IJtoN(i, j);
		r[n] = fixed_node[n]?mesh.getNode(i,j).bc_value:second[n];		
	    }
	
	return r;
    }

    /**
     * @param v * @return magnitude of a v[2] vector*/
    public static double mag2(double v[])
    {
	return Math.sqrt(v[0]*v[0]+v[1]*v[1]);		
    }

    /** computes dot product of a 2D vecto
     * @param v1r
     * @param v2
     * @return */
    public static double dot2(double v1[], double v2[]) 
    {
	return v1[0]*v2[0]+v1[1]*v2[1];
    }

    /**
     * @param v * @return magnitude of a v[2] vector*/
    public static double mag3(double v[])
    {
	return Math.sqrt(v[0]*v[0]+v[1]*v[1]+v[2]*v[2]);		
    }

    /** computes dot product of a 2D vector
     * @param v1
     * @param v2
     * @return */
    public static double dot3(double v1[], double v2[]) 
    {
	return v1[0]*v2[0]+v1[1]*v2[1]+v1[2]*v2[2];
    }
	
    /**prints the vector on standard ou
     * @param vt*/
    public static void print(double v[]) 
    {
	for (int i=0;i<v.length;i++)
	    System.out.printf("%g ",v[i]);
	System.out.printf("\n");
    }

    /**cross product of a 3d arra
     * @param v1
     * @param v2y
     * @return */
    public static double[] CrossProduct3(double v1[], double v2[])
    {
	double r[]=new double[3];
	r[0] = v1[1]*v2[2]-v1[2]*v2[1];
	r[1] = -v1[0]*v2[2]+v1[2]*v2[0];
	r[2] = v1[0]*v2[1]-v1[1]*v2[0];
	return r;
    }

    /**
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double dist2(double v1[], double v2[]) 
    {
	double di = v1[0] - v2[0];
	double dj = v1[1] - v2[1];
	return Math.sqrt(di*di + dj*dj);	
    }

    /**
     *
     * @return
     */
    public static double[] randomUnitVector() 
    {
	double v[] = new double[3];
	v[0] = Starfish.rnd2();
	v[1] = Starfish.rnd2();
	v[2] = 0;
			
	unit3(v);
	return v;			
    }

    /**
     *
     * @param v
     */
    public static void unit2(double[] v) 
    {
	assert v.length==2;
	
	double mag = mag2(v);
	v[0]/=mag;
	v[1]/=mag;
    }

    /**
     *
     * @param v
     */
    public static void unit3(double[] v)
    {
	assert v.length==3;
	
	double mag = mag3(v);
	v[0]/=mag;
	v[1]/=mag;
	v[2]/=mag;
    }
    
    /** multiplies v[2] by t, where t is a scalar
     * @param v data to multiply
     * @param t scalar*/
    public static void multInclusive2(double v[], double t)
    {
	v[0]*=t;
	v[1]*=t;
    }
    
    /** mirrors one vector around another, useful for elastic surface impacts
     @param vec	vector to reflect
     @param r	vector to reflect about
     * @return 
     */
    public static double[] mirror(double vec[], double r[])
    {
	/*compute tangential component*/
	double t_mag = dot(vec,r);
	double t[] = Vector.mult(r, t_mag);
	
	/*normal component*/
	double n[] = Vector.subtract(vec, t);
	
	/*reflect tangential component and reassemble*/
	t = Vector.mult(t, -1);
	return Vector.add(t, n);
    }

    /*rotates vector by the given angle about the third axis
     * @param vec vector to rotate
     * @param angle angle to rotate by in radians
     * @return new vector corresponding to vec rotated by angle
     */

    /**
     *
     * @param vec
     * @param angle
     * @return
     */

    public static double[] rotate2(double[] vec, double angle)
    {
	double r[] = new double[vec.length];
	r[0] = Math.cos(angle)*vec[0] - Math.sin(angle)*vec[1];
	r[1] = Math.sin(angle)*vec[0] + Math.cos(angle)*vec[1];
	return r;
    }
   
    /*samples a random vector according to the cosine law*/

    /**
     *
     * @param normal
     * @param tangent
     * @return
     */

    public static double[] lambertianVector(double normal[], double tangent[])
    {
	/*vectors defining the local coordinate system*/
	double p[] = {0,0,1};  /*in plane vector*/
	
	/*cosine law*/
	double cosphi = Math.sqrt(1.0 - Starfish.rnd());
	
	if (cosphi > 0.99999) cosphi = 0.99999;
	double theta_vel = 2 * Math.PI * Starfish.rnd();
	double sinphi = Math.sqrt(1.0 - cosphi*cosphi);
        	    
	double c_tang = Math.cos(theta_vel) * sinphi;	/*i component*/
	double c_normal = cosphi ;		    /*j component*/
	double c_inplane = Math.sin(theta_vel) * sinphi;	/*in plane component*/
	
	double vec[] = new double[3];
	/*set velocity and scale by drift*/
	for (int i=0;i<3;i++)
	    vec[i] = c_tang*tangent[i] + c_normal*normal[i] + c_inplane*p[i];
	return vec;
    }

    /**
     *
     * @param v
     * @return
     */
    public static double max(final double v[])
    {
	double val = v[0];
	for (int i=0;i<v.length;i++)
	    if (v[i]>val) val=v[i];
	
	return val;
    }
    
    /**
     *
     * @param v
     * @return
     */
    public static double min(final double v[])
    {
	double val = v[0];
	for (int i=0;i<v.length;i++)
	    if (v[i]<val) val=v[i];
	
	return val;
    }

    /**@param v vector to check
     * @return true if all vector components are finite*/
    public static boolean isFinite(double[] v)
    {
	for (int i=0;i<v.length;i++)
	    if (!Double.isFinite(v[i]))
		return false;
	return true;
    }
}
