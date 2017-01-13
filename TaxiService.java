

public class TaxiService {
	graph_taxi_info gti;
	TaxiService(){
		gti=new graph_taxi_info();
	}
	
	public void performAction(String actionMessage){
		String a, b, c, m;
		String[] s;
		s = actionMessage.split("\\s");
		m = s[0];
		a = s[1];
		
		b = null;
		
		c = null;
		
		if (s.length == 3)
			{
				b = s[2];
			}
		if (s.length == 4)
			{
				b = s[2];
				c = s[3];
			}
	System.out.println("\""+actionMessage+"\"");
			
			if(m.equals("edge")){
				gti.addEdge(a,b,Integer.parseInt(c));
			}
			else if(m.equals("taxi")){
				gti.addTaxi(a,b);
			}
		
			else if(m.equals("customer")){
				gti.customerQuery(a,b,Integer.parseInt(c));
			}
			
			else if(m.equals("printTaxiPosition")){
				gti.printAvailableTaxis(Integer.parseInt(a));
			}
				
			else{
				try{
					throw new Exception("Invalid statement!!!");
				}
				catch(Exception e){
					System.out.println(e);
					return;
				}
			}
			
			System.out.println();
	}
	
}
