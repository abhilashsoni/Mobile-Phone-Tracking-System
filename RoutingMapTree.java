import java.util.NoSuchElementException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class RoutingMapTree
{
	Exchange root;
	MobilePhoneSet set = new MobilePhoneSet();

	public RoutingMapTree() 
	{
		//creates a routing map tree with an Exchange node, the root node which has an identifier 0
		root = new Exchange(0);
	}

	public Exchange preOrder(Exchange a, int b)
	{// this function returns the position of an exchange by searching it by it's id
		if(a!=null)
		{
			if(a.exchangeno == b) return a;
			else 
			{
				Exchange c = preOrder(a.child,b);
				Exchange d = preOrder(a.next,b);
				if(c!=null) return c;
				if(d!=null) return d;
			}
		}
		return null;
	}

	public void preOrderSet(Exchange a, Exchange b)
	{//this function prints all the mobilephones under an exchange
		if(a.child == null) 
		{
			MobilePhone m = set.mobiles.front;
			while(m!=null)
			{
				if(m.basest.exchangeno == a.exchangeno)
				{
					System.out.print(" "+m.mobileno+",");
				}
				m = m.next;
			};
			if(a.next!=null && a.exchangeno!=b.exchangeno)
			{
				a=a.next;
				preOrderSet(a,b);
			}
		}

		else
		{
			if(a.exchangeno!=b.exchangeno)
			{
				if(a.next!=null)
				{
					preOrderSet(a.next,b);
				}
			}
			a=a.child;
			preOrderSet(a,b);
		}

		/*if(a.child!=null && a.exchangeno==b.exchangeno)
		{
			a = a.child;
			preOrderSet(a,b);
		}

		if(a.child!=null && a.exchangeno!=b.exchangeno)
		{
			a=a.child;
			preOrderSet(a,b);

			a=a.next;
			preOrderSet(a,b);
		}*/
	}

	public void addExchange(Exchange a, Exchange b)
	{	
		//if(root!=null)
		
			if(preOrder(root,a.exchangeno)!=null)
			{
			Exchange c = preOrder(root,a.exchangeno);
				Exchange g = new Exchange(b);
				g.parent = c;
				if(c.child==null) c.add(g); 
				else
				{	if(c.child.next==null)
					{
						c.child.next = g;
						c.noc++;
					}
					else
					{
						c = c.child.next;
						while(c.next!=null)
						{
							c = c.next;
						};
						c.next = g;
						c.parent.noc++;
					}
				}
			}
			else 
			{//throw an exception
				try
				{
				throw new NoSuchElementException(); 
				}
				catch(NoSuchElementException e)
				{
					System.out.println("Error in adding exchange: There is no exchange with this exchange no");
				}  
			}
	}

	public RoutingMapTree(Exchange k)
	{
		root.exchangeno = k.exchangeno;
	}

	public void switchOn(MobilePhone a, Exchange b)
	{
		if(root!=null)
		{
			if(preOrder(root,b.exchangeno)!=null)
			{Exchange c = preOrder(root,b.exchangeno);
			if(c!=null)
			{
				if(set.mobiles.IsMember(a))
				{
					try
					{
						throw new NoSuchElementException(); 
					}
					catch(NoSuchElementException e)
					{
						System.out.println("Error in switching on mobile: mobile phone is already switched on");
					}
				}

				else 
					{
						set.mobiles.Insert(a,c);
					}	
			}
			}
			else 
			{//throw an exception
				try
				{
				throw new NoSuchElementException(); 
				}
				catch(NoSuchElementException e)
				{
					System.out.println("Error in switching on mobile: There is no exchange with this exchange no");
				}  
			}
		}
		else 
		{//throw an exception
			try
			{
			throw new NoSuchElementException(); 
			}
			catch(NoSuchElementException e)
			{
				System.out.println("Error in switching on mobile: There is no exchange with this exchange no");
			}  
		}
	}

	public void switchOff(MobilePhone a)
	{
		if(set.mobiles.IsMember(a))
			set.mobiles.Delete(a);
		else 
		{//throw an exception
			try
			{
			throw new NoSuchElementException(); 
			}
			catch(NoSuchElementException e)
			{
				System.out.println("Error in switching off mobile: There is no mobilephone with this no");
			}  
		}
	}

	public Exchange findPhone(MobilePhone a)
	{
		Boolean found = false;
		MobilePhone b = set.mobiles.front;
		
			while(b!=null)
			{ 
				if(b.mobileno == a.mobileno)
					{
						found = true;
						return b.basest;
					}
				else b = b.next;
			};
			if(found == false)
			{
				try
					{
					throw new NoSuchElementException(); 
					}
					catch(NoSuchElementException e)
					{
						System.out.println("Error in finding phone: not such mobilephone exists");
					}
					return null;
			}
			return null;
	}

	public void movePhone(MobilePhone a, Exchange c)
	{
		if(preOrder(root, c.exchangeno)!=null )
		{ Exchange d = preOrder(root, c.exchangeno);
			if(d.child != null)
			{
				try
						{
							throw new NoSuchElementException(); 
						}
						catch(NoSuchElementException e)
						{
							System.out.println("Error in moving phone: given exchange is not level 0");
						}
			}
			else
			{
				Boolean found = false;
				MobilePhone b = set.mobiles.front;
		
				while(b!=null)
				{ 
					if(b.mobileno == a.mobileno)
						{
							b.basest = d;
							return;
						}
					else b = b.next;
				};
				if(found == false)
				{
					try
						{
							throw new NoSuchElementException(); 
						}
						catch(NoSuchElementException e)
						{
							System.out.println("Error in moving phone: mobilephone is swithched off");
						}
				}
			}	
		}
	}

	public int lowestRouter(Exchange a, Exchange b)
	{
		if(a.exchangeno == b.exchangeno) return a.exchangeno;
		else
		{
			Vector<Integer> v1 = new Vector<Integer>();
			Vector<Integer> v2 = new Vector<Integer>();
			Exchange c = preOrder(root,a.exchangeno);
			Exchange d =preOrder(root,b.exchangeno);
			while(c.parent!=null)
			{
				v1.add(c.exchangeno);
				c=c.parent;
			};
			while(d.parent!=null)
			{
				v2.add(d.exchangeno);
				d=d.parent;
			};
			for(int i=0; i<v1.size(); i++)
			{
				for(int j=0; j<v2.size(); j++)
				{
					if(v1.get(i) == v2.get(j))
					{
						return v1.get(i);
					}
				}
			}
			return 0;
		}
	}

	public Exchange searchPhone(MobilePhone a)
	{
		return (set.mobiles.Member(a));
	}

	public void routeCall(MobilePhone a, MobilePhone b, Vector v3)
	{
		Exchange c = searchPhone(a);
		Exchange d = searchPhone(b);
		if(c.exchangeno == d.exchangeno) 
		{
			v3.add(c.exchangeno);
		}
		else
		{
			Vector<Integer> v1 = new Vector<Integer>();
			Vector<Integer> v2 = new Vector<Integer>();
			while(c!=null)
			{
				v1.add(c.exchangeno);
				c=c.parent;
			};
			while(d!=null)
			{
				v2.add(d.exchangeno);
				d=d.parent;
			};
			for(int i=0; i<v1.size(); i++)
			{
				v3.add(v1.get(i));
				for(int j=0; j<v2.size(); j++)
				{
					if(v1.get(i) == v2.get(j))
					{
						for(int k = j-1; k>=0; k--)
						{
							v3.add(v2.get(k));
						}
					}
				}
			}
		}
	}

	public void performAction(String actionMessage)
    {
    	String[] arr = actionMessage.split("\\s+");
		
		if(arr[0].equals("findCallPath"))
		{
			System.out.print(actionMessage+" : ");
			try
    		{
    			if(Integer.parseInt(arr[1])>0 && Integer.parseInt(arr[2])>0)
    			{
    				MobilePhone a = new MobilePhone(Integer.parseInt(arr[1]));
    				MobilePhone b = new MobilePhone(Integer.parseInt(arr[2]));
    				if(searchPhone(a)!=null && searchPhone(b)!=null)
    				{	
    					Vector<Integer> v3 = new Vector<Integer>();
    					routeCall(a,b,v3);
    					for(int i =0 ; i<v3.size(); i++)
    					{
    						System.out.print(v3.get(i)+", ");
    					}
    				}
    				else
    				{
    					try
						{
						throw new NoSuchElementException(); 
						}
						catch(NoSuchElementException e)
						{
							System.out.print("Error : mobile phone doesn't exist or is switched off");
						}
    				}
    			}
    			else
    			{
    				try
					{
					throw new NoSuchElementException(); 
					}
					catch(NoSuchElementException e)
					{
						System.out.print("Error:Invalid type of arguement (negative)");
					}
    			}
    		}
    		catch(NumberFormatException e)
			{
				System.out.println("Error in finding phone: Invalid arguement");
			}
			System.out.println(" ");
		}

    	else if(arr[0].equals("lowestRouter"))
    	{
    		System.out.print(actionMessage+" : ");
    		try
    		{
    			if(Integer.parseInt(arr[1])>0 && Integer.parseInt(arr[2])>0)
    			{
    				if(preOrder(root,Integer.parseInt(arr[1]))!=null && preOrder(root,Integer.parseInt(arr[1]))!=null)
    				{
    					Exchange a = new Exchange(preOrder(root,Integer.parseInt(arr[1])));
    					Exchange b = new Exchange(preOrder(root,Integer.parseInt(arr[2])));
    					if(a.child == null && b.child == null)
    					{
    						System.out.println(lowestRouter(a,b));
    					}
    					else
    					{
    						try
							{
							throw new NoSuchElementException(); 
							}
							catch(NoSuchElementException e)
							{
								System.out.print("Error in lowest router: exchanges are'nt level-0");
							}
    					}
    				}
    				else
    				{
    					try
						{
						throw new NoSuchElementException(); 
						}
						catch(NoSuchElementException e)
						{
							System.out.print("Error in lowest router: such exchanges don't exist");
						}
    				}
    			}
    			else
    			{
    				try
					{
					throw new NoSuchElementException(); 
					}
					catch(NoSuchElementException e)
					{
						System.out.print("Error:Invalid type of arguement (negative)");
					}
    			}
    		}
    		catch(NumberFormatException e)
			{
				System.out.println("Error in finding phone: Invalid arguement");
			}
    	}

    	else if(arr[0].equals("movePhone"))
    	{
    		//System.out.println(actionMessage);
    		try
    		{
    			if(Integer.parseInt(arr[1])>0 && Integer.parseInt(arr[2])>0)
    			{
    				MobilePhone a = new MobilePhone(Integer.parseInt(arr[1]));
    				Exchange b = new Exchange(Integer.parseInt(arr[2]));
					movePhone(a,b);
    			}
    			else
    			{
    				try
					{
					throw new NoSuchElementException(); 
					}
					catch(NoSuchElementException e)
					{
						System.out.print("Error:Invalid type of arguement (negative)");
					}
    			}
    		}
    		catch(NumberFormatException e)
			{
				System.out.println("Error in moving phone: Invalid arguement");
			}
    	}

    	else if(arr[0].equals("findPhone"))
    	{
    		try
    		{
    			if(Integer.parseInt(arr[1])>0)
    			{
    			MobilePhone a = new MobilePhone(Integer.parseInt(arr[1]));
    				if(findPhone(a)!=null)
    				{
    				Exchange b = new Exchange(findPhone(a));
    				System.out.println(actionMessage+" : "+b.exchangeno);
    				}
    			}
    			else
    			{
    				try
					{
					throw new NoSuchElementException(); 
					}
					catch(NoSuchElementException e)
					{
						System.out.print("Error:Invalid type of arguement (negative)");
					}
    			}
    		}
    		catch(NumberFormatException e)
			{
				System.out.println("Error in finding phone: Invalid arguement");
			}
    	}
		
		else if(arr[0].equals("addExchange"))
		{
			//System.out.print(actionMessage+" ");	
			try
			{  // System.out.println(Integer.parseInt(arr[1])+"   "+Integer.parseInt(arr[2]));
				Exchange a = new Exchange(Integer.parseInt(arr[1]));
				Exchange b = new Exchange(Integer.parseInt(arr[2]));
				addExchange(a,b);
				//System.out.println(actionMessage);
			}
			catch(NumberFormatException e)
			{
				System.out.println("Error in adding exchange: Invalid arguement");
			}
			//System.out.println(" ");
		}

		else if(arr[0].equals("switchOnMobile"))
		{	
			//System.out.print(actionMessage+" ");	
			MobilePhone a = new MobilePhone(Integer.parseInt(arr[1]));
			Exchange b = new Exchange(Integer.parseInt(arr[2]));
			switchOn(a,b);
			//System.out.println(actionMessage);
			//System.out.println(" ");
		}

		else if(arr[0].equals("switchOffMobile"))
		{
			System.out.print(actionMessage+" ");	
			MobilePhone a = new MobilePhone(Integer.parseInt(arr[1]));
			switchOff(a);
			//System.out.println(" ");
		}

		else if(arr[0].equals("queryNthChild"))
		{
			System.out.print(actionMessage+" :");	
			Exchange a = new Exchange(Integer.parseInt(arr[1]));
			int b = Integer.parseInt(arr[2]);
			if(a.exchangeno<0 || b<0)
			{
				try
				{
				throw new NoSuchElementException(); 
				}
				catch(NoSuchElementException e)
				{
					System.out.print("Error:Invalid type of arguement (negative)");
				}
			}
			else if(root!=null)
			{
			Exchange c = (preOrder(root,a.exchangeno));
				if(c.child(b)!=null)
				{
					Exchange d = (c.child(b));
					System.out.print(d.exchangeno);
					//System.out.println(d.parent);
				}
			}
			else 
			{//throw an exception
				try
				{
				throw new NoSuchElementException(); 
				}
				catch(NoSuchElementException e)
				{
					System.out.println("Error: There is no exchange with this exchange no");
				}  
			}
			System.out.println(" ");
		}

		else if(arr[0].equals("queryMobilePhoneSet"))
		{	
			System.out.print(actionMessage+" :");	
			Exchange a = new Exchange(Integer.parseInt(arr[1]));
			if(preOrder(root,a.exchangeno)!=null)
			{
				Exchange c = preOrder(root,a.exchangeno);
				preOrderSet(c,c);
			}

			else
			{
				try
				{
				throw new NoSuchElementException(); 
				}
				catch(NoSuchElementException e)
				{
					System.out.println("Error: There is no exchange with this exchange no");
				} 
			}
			System.out.println(" ");
		}

		else
			{
				try
				{
				throw new NoSuchElementException(); 
				}
				catch(NoSuchElementException e)
				{
					System.out.println("Error: This query was not specified");
				} 
			}
		//if (root.child.next.next!=null)
		//System.out.println(root.child.next.next.exchangeno);
	}
}
