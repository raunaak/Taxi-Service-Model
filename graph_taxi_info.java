

import java.util.*;

public class graph_taxi_info
	{
		
		// mapping vertex V to (V',distance of V'), V' belongs to set of
		// neighbours of V
		
		private HashMap<String, HashMap<String, Integer>>	loc_neigh	= new HashMap<String, HashMap<String, Integer>>();
		
		private HashMap<String, String>						taxis		= new HashMap<String, String>();
		
		private HashMap<String, Integer>					taxiAvailability	= new HashMap<String, Integer>();
		
		public void addEdge(String source, String destination, int time)
			{
				
				if (!loc_neigh.containsKey(source))
					loc_neigh.put(source, new HashMap<String, Integer>());
				if (!loc_neigh.containsKey(destination))
					loc_neigh.put(destination, new HashMap<String, Integer>());
				if (!loc_neigh.get(source).containsKey(destination))
					loc_neigh.get(source).put(destination, time);
				if (!loc_neigh.get(destination).containsKey(source))
					loc_neigh.get(destination).put(source, time);
			}
			
		public void addTaxi(String name, String posn)
			{
				
				if (!loc_neigh.containsKey(posn))
					{
						System.out.println("No such position exists for taxi!!!");
						return;
					}
				if (!taxis.containsKey(name))
					{
						taxis.put(name, posn);
						taxiAvailability.put(name, 0);
					}
				else
					System.out.println("Taxi by this name already exists!!!");
			}
			
		// returns the element(String) in unsettled with minimum Integer value
		// amongst the elements of unsettled which are not in settled
		public String findmin(HashMap<String, Integer> x, ArrayList<String> settled)
			{
				
				// find minimum distance node which is not finalised
				String ans = "";
				int min = Integer.MAX_VALUE;
				for (Map.Entry<String, Integer> entry : x.entrySet())
					{
						String a = entry.getKey();
						int b = entry.getValue();
						if (!settled.contains(a))
							{
								if (b < min)
									{
										min = b;
										ans = a;
									}
							}
					}
				return ans;
			}
			
		// The output gives for input vertex s is of the form {(s,a);}
		/*
		 * eg nodes are {1,2,3,4,5,6} and output y =
		 * {(1,1),(2,1),(4,1),(5,1),(3,2)} then it means that if we wanna go
		 * from 1 to 3 via shortest path then 2 will come on the way
		 */
		public HashMap<String, String> shortestpath(String s)
			{
				
				if (!loc_neigh.containsKey(s))
					{
						System.out.println("We don't provide service in this location or this location doesn't exist");
						return null;
					}
				HashMap<String, Integer> x = new HashMap<String, Integer>();
				HashMap<String, String> y = new HashMap<String, String>();
				ArrayList<String> settled = new ArrayList<String>();
				for (Map.Entry<String, HashMap<String, Integer>> entry : loc_neigh.entrySet())
					{
						String a = entry.getKey();
						x.put(a, Integer.MAX_VALUE);
						y.put(a, "");
					}
				y.put(s, s);
				x.put(s, 0);
				int n = loc_neigh.size();
				
				// Here in I apply DIjKstra algorithm
				for (int i = 0; i < n - 1; i++)
					{
						String b = findmin(x, settled);
						settled.add(b);
						
						// Here in I apply DICKstra algorithm
						for (Map.Entry<String, Integer> entry : loc_neigh.get(b).entrySet())
							{
								String p = entry.getKey();
								Integer q = entry.getValue();
								if (!settled.contains(p))
									{
										if (x.get(b) + q < x.get(p))
											{
												x.put(p, x.get(b) + q);
												y.put(p, b);
											}
									}
							}
							
					}
					
				return y;
			}
		
		
		
		// eg nodes = {(1,2,3,4,5,6}    s=1  it will give all the nodes along with their shortest distance from 1
		public HashMap<String, Integer> shortestPathLength(String s)
			{
				
				if (!loc_neigh.containsKey(s))
					{
						System.out.println("We don't provide service in this location or this location doesn't exist");
						return null;
					}
				HashMap<String, Integer> x = new HashMap<String, Integer>();
				HashMap<String, String> y = new HashMap<String, String>();
				ArrayList<String> settled = new ArrayList<String>();
				for (Map.Entry<String, HashMap<String, Integer>> entry : loc_neigh.entrySet())
					{
						String a = entry.getKey();
						x.put(a, Integer.MAX_VALUE);
						y.put(a, "");
					}
				y.put(s, s);
				x.put(s, 0);
				int n = loc_neigh.size();
				
				for (int i = 0; i < n - 1; i++)
					{
						String b = findmin(x, settled);
						settled.add(b);
						for (Map.Entry<String, Integer> entry : loc_neigh.get(b).entrySet())
							{
								String p = entry.getKey();
								Integer q = entry.getValue();
								if (!settled.contains(p))
									{
										if (x.get(b) + q < x.get(p))
											{
												x.put(p, x.get(b) + q);
												y.put(p, b);
											}
									}
							}
					}
					
				return x;
			}
		
		
		// Will the taxi "s" be available in time t?	
		public boolean isTaxiAvailable(String s,int t){
			if(!taxis.containsKey(s)){
				System.out.println("Sorry we don't have taxi by this name may be you should check your query");
				return false;
			}
			if(taxiAvailability.get(s)<=t)return true;
			return false;
		}
		
		
		
		public void printAvailableTaxis(int t){
			System.out.println("Available taxis:");
			boolean flag=false;
			int c=0;
			for(Map.Entry<String,String> entry:taxis.entrySet()){
				if(isTaxiAvailable(entry.getKey(),t)){
					flag=true;
					System.out.println(++c+") "+entry.getKey()+"is currently at " + entry.getValue());
				}
			}
			if(flag==false)System.out.println("SORRY!!. No taxi available at this time. ");
		}
		
		
		
		public void printshortestpath(String source,String destination){
			try{if(!loc_neigh.containsKey(source))
						{throw new Exception("Sorry we don't have data for "+source);}
			if(!loc_neigh.containsKey(destination))
				{throw new Exception("Sorry we don't have data for "+destination);}
			}
			catch(Exception e) {System.out.println(e);return;}
			
			
			HashMap<String,String> h=shortestpath(source);
			LinkedList<String> l=new LinkedList<String>();
			String temp=destination;
			while(!temp.equals(source)){
				l.add(temp);
				temp=h.get(temp);
			}
			l.add(source);
			for(int i=l.size()-1;i>=0;i--)System.out.print(l.get(i)+"  ");
		}
		
		
		public void customerQuery(String source,String destination,int t){
			try{if(!loc_neigh.containsKey(source))
				{throw new Exception("Sorry we don't have data for "+source);}
	if(!loc_neigh.containsKey(destination))
		{throw new Exception("Sorry we don't have data for "+destination);}
	}
			catch(Exception e) {System.out.println(e);return;}

			int mint=Integer.MAX_VALUE;
			String mins="";
			int c=0;
			System.out.println("Available Taxis For Customer call are");
			for(Map.Entry<String, String> entry1 : taxis.entrySet()){
				String x=entry1.getKey();
				String y=entry1.getValue();
				if(isTaxiAvailable(x,t)){
				System.out.print(++c+") Path of Taxi " + x + ": ");
				printshortestpath(y,source);
				int tempt=shortestPathLength(y).get(source);
				System.out.print(" :Time Taken: "+tempt);
				if(isTaxiAvailable(x,t) && tempt<mint){
					mint=tempt;
					mins=x;
				}
				System.out.println();
				}
			}
			int sdt=shortestPathLength(source).get(destination);
			if(mins.equals(""))System.out.println("Sorry!!! No taxi is available at this time("+t+" units)");
			else{
				System.out.println("Choose taxi \""+mins+"\" to service customer request");
				taxiAvailability.put(mins,t+mint+sdt);
				taxis.put(mins, destination);
			}
			System.out.print("Path of Customer: ");
			printshortestpath(source,destination);
			System.out.print(" time taken is "+ sdt + "units");
			System.out.println();
		}
		
		
		
		
		
		
		
		
			
	}
