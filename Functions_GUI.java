package Ex1;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.google.gson.Gson;

public class Functions_GUI implements functions {
	ArrayList<function> array = new ArrayList<function>();
	public static Color[] Colors = {Color.blue, Color.cyan, Color.MAGENTA, Color.ORANGE, Color.red, Color.GREEN, Color.PINK};

	@Override
	public boolean add(function arg0) {
		return array.add(arg0);
	}

	@Override
	public boolean addAll(Collection<? extends function> arg0) {

		return array.addAll(arg0);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		array.clear();
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return array.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return array.containsAll(arg0);
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return array.isEmpty();
	}

	@Override
	public Iterator<function> iterator() {
		// TODO Auto-generated method stub
		return array.iterator();
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return array.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return array.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return array.retainAll(arg0);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return array.size();
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return array.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return array.toArray(arg0);
	}

	@Override
	public void initFromFile(String file) throws IOException {

        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)) {
        	ComplexFunction f = new ComplexFunction(new Monom("x^2"));
            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
               array.add(f.initFromString(line));
               
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }


	@Override
	public void saveToFile(String file) throws IOException {
				    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				    for(function f : array) {
				    	writer.write(f.toString());
				    	writer.write("\n");
				    }   
				    writer.close();
				}

	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(rx.get_min(), rx.get_max());
		StdDraw.setYscale(ry.get_min(), ry.get_max());
		StdDraw.setPenRadius(0.01);
		StdDraw.line(rx.get_min(), 0, rx.get_max(), 0);
		StdDraw.line(0, ry.get_min(),0, ry.get_max());
		StdDraw.setPenRadius();
		for(double i =Math.floor(rx.get_min());i<rx.get_max();i++) {
			if(i!=0) {
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.text(i, 0.2, i+"");
				StdDraw.setPenColor(Color.gray);
				StdDraw.line(i, ry.get_min(), i,ry.get_max());
			}
		}
		for(double i =Math.floor(ry.get_min());i<ry.get_max();i++) {
			if(i!=0) {
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.text(0.5, i, i+"");
				StdDraw.setPenColor(Color.gray);
				StdDraw.line(rx.get_min(), i, rx.get_max(),i);
			}
		}
		double epsilon = (rx.get_max()-rx.get_min())/resolution;
		double y=0;
		double y1=0;
		int gColor =0;
		for(int i=0;i<this.size();i++) { //limit gColor
			if(gColor==6)
				gColor=0;
			StdDraw.setPenColor(Colors[gColor++]);
			for(double x = rx.get_min();x<=rx.get_max();x=x+epsilon) {
				System.out.println(array.get(i).f(x)+" "+x);
				y = array.get(i).f(x);
				y1 = array.get(i).f(x+epsilon);
				StdDraw.line(x, y,x+epsilon, y1);
			}
		}
	}

	@Override
	public void drawFunctions(String json_file) {
		Gson gson = new Gson();
		
		try 
		{
			FileReader reader = new FileReader(json_file);
			Parameters param = gson.fromJson(reader,Parameters.class);
			Range rx = new Range(param.Range_X[0],param.Range_X[1]);
			Range ry = new Range(param.Range_Y[0],param.Range_Y[1]);
			drawFunctions(param.Width, param.Height,rx, ry, param.Resolution);
		} 
		catch (FileNotFoundException e) {
			Range rx = new Range(-10,10);
			Range ry = new Range(-10,10);
			drawFunctions(1000,600, rx, ry,800);
		}
	}

}
